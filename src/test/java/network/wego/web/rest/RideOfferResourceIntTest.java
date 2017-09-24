package network.wego.web.rest;

import network.wego.JhipsterApp;

import network.wego.domain.RideOffer;
import network.wego.domain.Driver;
import network.wego.domain.Ride;
import network.wego.repository.RideOfferRepository;
import network.wego.service.RideOfferService;
import network.wego.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import network.wego.domain.enumeration.RideStatus;
/**
 * Test class for the RideOfferResource REST controller.
 *
 * @see RideOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class RideOfferResourceIntTest {

    private static final RideStatus DEFAULT_STATUS = RideStatus.CANCLED;
    private static final RideStatus UPDATED_STATUS = RideStatus.DELAYED;

    @Autowired
    private RideOfferRepository rideOfferRepository;

    @Autowired
    private RideOfferService rideOfferService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRideOfferMockMvc;

    private RideOffer rideOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RideOfferResource rideOfferResource = new RideOfferResource(rideOfferService);
        this.restRideOfferMockMvc = MockMvcBuilders.standaloneSetup(rideOfferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RideOffer createEntity(EntityManager em) {
        RideOffer rideOffer = new RideOffer()
            .status(DEFAULT_STATUS);
        // Add required entity
        Driver driver = DriverResourceIntTest.createEntity(em);
        em.persist(driver);
        em.flush();
        rideOffer.setDriver(driver);
        // Add required entity
        Ride ride = RideResourceIntTest.createEntity(em);
        em.persist(ride);
        em.flush();
        rideOffer.setRide(ride);
        return rideOffer;
    }

    @Before
    public void initTest() {
        rideOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createRideOffer() throws Exception {
        int databaseSizeBeforeCreate = rideOfferRepository.findAll().size();

        // Create the RideOffer
        restRideOfferMockMvc.perform(post("/api/ride-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideOffer)))
            .andExpect(status().isCreated());

        // Validate the RideOffer in the database
        List<RideOffer> rideOfferList = rideOfferRepository.findAll();
        assertThat(rideOfferList).hasSize(databaseSizeBeforeCreate + 1);
        RideOffer testRideOffer = rideOfferList.get(rideOfferList.size() - 1);
        assertThat(testRideOffer.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRideOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rideOfferRepository.findAll().size();

        // Create the RideOffer with an existing ID
        rideOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRideOfferMockMvc.perform(post("/api/ride-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideOffer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RideOffer> rideOfferList = rideOfferRepository.findAll();
        assertThat(rideOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRideOffers() throws Exception {
        // Initialize the database
        rideOfferRepository.saveAndFlush(rideOffer);

        // Get all the rideOfferList
        restRideOfferMockMvc.perform(get("/api/ride-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rideOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getRideOffer() throws Exception {
        // Initialize the database
        rideOfferRepository.saveAndFlush(rideOffer);

        // Get the rideOffer
        restRideOfferMockMvc.perform(get("/api/ride-offers/{id}", rideOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rideOffer.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRideOffer() throws Exception {
        // Get the rideOffer
        restRideOfferMockMvc.perform(get("/api/ride-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRideOffer() throws Exception {
        // Initialize the database
        rideOfferService.save(rideOffer);

        int databaseSizeBeforeUpdate = rideOfferRepository.findAll().size();

        // Update the rideOffer
        RideOffer updatedRideOffer = rideOfferRepository.findOne(rideOffer.getId());
        updatedRideOffer
            .status(UPDATED_STATUS);

        restRideOfferMockMvc.perform(put("/api/ride-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRideOffer)))
            .andExpect(status().isOk());

        // Validate the RideOffer in the database
        List<RideOffer> rideOfferList = rideOfferRepository.findAll();
        assertThat(rideOfferList).hasSize(databaseSizeBeforeUpdate);
        RideOffer testRideOffer = rideOfferList.get(rideOfferList.size() - 1);
        assertThat(testRideOffer.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRideOffer() throws Exception {
        int databaseSizeBeforeUpdate = rideOfferRepository.findAll().size();

        // Create the RideOffer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRideOfferMockMvc.perform(put("/api/ride-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideOffer)))
            .andExpect(status().isCreated());

        // Validate the RideOffer in the database
        List<RideOffer> rideOfferList = rideOfferRepository.findAll();
        assertThat(rideOfferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRideOffer() throws Exception {
        // Initialize the database
        rideOfferService.save(rideOffer);

        int databaseSizeBeforeDelete = rideOfferRepository.findAll().size();

        // Get the rideOffer
        restRideOfferMockMvc.perform(delete("/api/ride-offers/{id}", rideOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RideOffer> rideOfferList = rideOfferRepository.findAll();
        assertThat(rideOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideOffer.class);
        RideOffer rideOffer1 = new RideOffer();
        rideOffer1.setId(1L);
        RideOffer rideOffer2 = new RideOffer();
        rideOffer2.setId(rideOffer1.getId());
        assertThat(rideOffer1).isEqualTo(rideOffer2);
        rideOffer2.setId(2L);
        assertThat(rideOffer1).isNotEqualTo(rideOffer2);
        rideOffer1.setId(null);
        assertThat(rideOffer1).isNotEqualTo(rideOffer2);
    }
}
