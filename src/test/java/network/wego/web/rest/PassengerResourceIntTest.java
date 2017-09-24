package network.wego.web.rest;

import network.wego.WegoApp;

import network.wego.domain.Passenger;
import network.wego.domain.User;
import network.wego.repository.PassengerRepository;
import network.wego.service.PassengerService;
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

/**
 * Test class for the PassengerResource REST controller.
 *
 * @see PassengerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WegoApp.class)
public class PassengerResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPassengerMockMvc;

    private Passenger passenger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PassengerResource passengerResource = new PassengerResource(passengerService);
        this.restPassengerMockMvc = MockMvcBuilders.standaloneSetup(passengerResource)
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
    public static Passenger createEntity(EntityManager em) {
        Passenger passenger = new Passenger()
            .fullName(DEFAULT_FULL_NAME);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        passenger.setUser(user);
        return passenger;
    }

    @Before
    public void initTest() {
        passenger = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassenger() throws Exception {
        int databaseSizeBeforeCreate = passengerRepository.findAll().size();

        // Create the Passenger
        restPassengerMockMvc.perform(post("/api/passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passenger)))
            .andExpect(status().isCreated());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeCreate + 1);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
    }

    @Test
    @Transactional
    public void createPassengerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passengerRepository.findAll().size();

        // Create the Passenger with an existing ID
        passenger.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassengerMockMvc.perform(post("/api/passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passenger)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPassengers() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get all the passengerList
        restPassengerMockMvc.perform(get("/api/passengers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passenger.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get the passenger
        restPassengerMockMvc.perform(get("/api/passengers/{id}", passenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(passenger.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPassenger() throws Exception {
        // Get the passenger
        restPassengerMockMvc.perform(get("/api/passengers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassenger() throws Exception {
        // Initialize the database
        passengerService.save(passenger);

        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Update the passenger
        Passenger updatedPassenger = passengerRepository.findOne(passenger.getId());
        updatedPassenger
            .fullName(UPDATED_FULL_NAME);

        restPassengerMockMvc.perform(put("/api/passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPassenger)))
            .andExpect(status().isOk());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getFullName()).isEqualTo(UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Create the Passenger

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPassengerMockMvc.perform(put("/api/passengers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passenger)))
            .andExpect(status().isCreated());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePassenger() throws Exception {
        // Initialize the database
        passengerService.save(passenger);

        int databaseSizeBeforeDelete = passengerRepository.findAll().size();

        // Get the passenger
        restPassengerMockMvc.perform(delete("/api/passengers/{id}", passenger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passenger.class);
        Passenger passenger1 = new Passenger();
        passenger1.setId(1L);
        Passenger passenger2 = new Passenger();
        passenger2.setId(passenger1.getId());
        assertThat(passenger1).isEqualTo(passenger2);
        passenger2.setId(2L);
        assertThat(passenger1).isNotEqualTo(passenger2);
        passenger1.setId(null);
        assertThat(passenger1).isNotEqualTo(passenger2);
    }
}
