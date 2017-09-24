package network.wego.web.rest;

import network.wego.WegoApp;

import network.wego.domain.RideRequest;
import network.wego.domain.Ride;
import network.wego.domain.Passenger;
import network.wego.repository.RideRequestRepository;
import network.wego.service.RideRequestService;
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
 * Test class for the RideRequestResource REST controller.
 *
 * @see RideRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WegoApp.class)
public class RideRequestResourceIntTest {

    private static final RideStatus DEFAULT_STATUS = RideStatus.CANCLED;
    private static final RideStatus UPDATED_STATUS = RideStatus.DELAYED;

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Autowired
    private RideRequestService rideRequestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRideRequestMockMvc;

    private RideRequest rideRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RideRequestResource rideRequestResource = new RideRequestResource(rideRequestService);
        this.restRideRequestMockMvc = MockMvcBuilders.standaloneSetup(rideRequestResource)
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
    public static RideRequest createEntity(EntityManager em) {
        RideRequest rideRequest = new RideRequest()
            .status(DEFAULT_STATUS);
        // Add required entity
        Ride ride = RideResourceIntTest.createEntity(em);
        em.persist(ride);
        em.flush();
        rideRequest.setRide(ride);
        // Add required entity
        Passenger passenger = PassengerResourceIntTest.createEntity(em);
        em.persist(passenger);
        em.flush();
        rideRequest.setPassenger(passenger);
        return rideRequest;
    }

    @Before
    public void initTest() {
        rideRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createRideRequest() throws Exception {
        int databaseSizeBeforeCreate = rideRequestRepository.findAll().size();

        // Create the RideRequest
        restRideRequestMockMvc.perform(post("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequest)))
            .andExpect(status().isCreated());

        // Validate the RideRequest in the database
        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
        assertThat(rideRequestList).hasSize(databaseSizeBeforeCreate + 1);
        RideRequest testRideRequest = rideRequestList.get(rideRequestList.size() - 1);
        assertThat(testRideRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRideRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rideRequestRepository.findAll().size();

        // Create the RideRequest with an existing ID
        rideRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRideRequestMockMvc.perform(post("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequest)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
        assertThat(rideRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRideRequests() throws Exception {
        // Initialize the database
        rideRequestRepository.saveAndFlush(rideRequest);

        // Get all the rideRequestList
        restRideRequestMockMvc.perform(get("/api/ride-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rideRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getRideRequest() throws Exception {
        // Initialize the database
        rideRequestRepository.saveAndFlush(rideRequest);

        // Get the rideRequest
        restRideRequestMockMvc.perform(get("/api/ride-requests/{id}", rideRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rideRequest.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRideRequest() throws Exception {
        // Get the rideRequest
        restRideRequestMockMvc.perform(get("/api/ride-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRideRequest() throws Exception {
        // Initialize the database
        rideRequestService.save(rideRequest);

        int databaseSizeBeforeUpdate = rideRequestRepository.findAll().size();

        // Update the rideRequest
        RideRequest updatedRideRequest = rideRequestRepository.findOne(rideRequest.getId());
        updatedRideRequest
            .status(UPDATED_STATUS);

        restRideRequestMockMvc.perform(put("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRideRequest)))
            .andExpect(status().isOk());

        // Validate the RideRequest in the database
        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
        assertThat(rideRequestList).hasSize(databaseSizeBeforeUpdate);
        RideRequest testRideRequest = rideRequestList.get(rideRequestList.size() - 1);
        assertThat(testRideRequest.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRideRequest() throws Exception {
        int databaseSizeBeforeUpdate = rideRequestRepository.findAll().size();

        // Create the RideRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRideRequestMockMvc.perform(put("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequest)))
            .andExpect(status().isCreated());

        // Validate the RideRequest in the database
        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
        assertThat(rideRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRideRequest() throws Exception {
        // Initialize the database
        rideRequestService.save(rideRequest);

        int databaseSizeBeforeDelete = rideRequestRepository.findAll().size();

        // Get the rideRequest
        restRideRequestMockMvc.perform(delete("/api/ride-requests/{id}", rideRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
        assertThat(rideRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideRequest.class);
        RideRequest rideRequest1 = new RideRequest();
        rideRequest1.setId(1L);
        RideRequest rideRequest2 = new RideRequest();
        rideRequest2.setId(rideRequest1.getId());
        assertThat(rideRequest1).isEqualTo(rideRequest2);
        rideRequest2.setId(2L);
        assertThat(rideRequest1).isNotEqualTo(rideRequest2);
        rideRequest1.setId(null);
        assertThat(rideRequest1).isNotEqualTo(rideRequest2);
    }
}
