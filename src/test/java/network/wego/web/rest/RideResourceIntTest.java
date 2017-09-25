package network.wego.web.rest;

import network.wego.JhipsterApp;

import network.wego.domain.Ride;
import network.wego.domain.Vehcile;
import network.wego.domain.City;
import network.wego.repository.RideRepository;
import network.wego.service.RideService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import network.wego.domain.enumeration.Gender;
import network.wego.domain.enumeration.RideStatus;
/**
 * Test class for the RideResource REST controller.
 *
 * @see RideResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class RideResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_POINT = "AAAAAAAAAA";
    private static final String UPDATED_MAP_POINT = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUANCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUANCY = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEAT_AGE = 1;
    private static final Integer UPDATED_SEAT_AGE = 2;

    private static final Long DEFAULT_MOBILE_NUMBER = 1L;
    private static final Long UPDATED_MOBILE_NUMBER = 2L;

    private static final Gender DEFAULT_SEAT_GENDER = Gender.MALE;
    private static final Gender UPDATED_SEAT_GENDER = Gender.FEMALE;

    private static final Instant DEFAULT_RIDE_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RIDE_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SMOKING = false;
    private static final Boolean UPDATED_SMOKING = true;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final RideStatus DEFAULT_STATUS = RideStatus.CANCLED;
    private static final RideStatus UPDATED_STATUS = RideStatus.DELAYED;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideService rideService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRideMockMvc;

    private Ride ride;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RideResource rideResource = new RideResource(rideService);
        this.restRideMockMvc = MockMvcBuilders.standaloneSetup(rideResource)
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
    public static Ride createEntity(EntityManager em) {
        Ride ride = new Ride()
            .address(DEFAULT_ADDRESS)
            .mapPoint(DEFAULT_MAP_POINT)
            .frequancy(DEFAULT_FREQUANCY)
            .destination(DEFAULT_DESTINATION)
            .seatAge(DEFAULT_SEAT_AGE)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .seatGender(DEFAULT_SEAT_GENDER)
            .rideDateTime(DEFAULT_RIDE_DATE_TIME)
            .smoking(DEFAULT_SMOKING)
            .price(DEFAULT_PRICE)
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS);
        // Add required entity
        Vehcile vehcile = VehcileResourceIntTest.createEntity(em);
        em.persist(vehcile);
        em.flush();
        ride.setVehcile(vehcile);
        // Add required entity
        City city = CityResourceIntTest.createEntity(em);
        em.persist(city);
        em.flush();
        ride.setCity(city);
        return ride;
    }

    @Before
    public void initTest() {
        ride = createEntity(em);
    }

    @Test
    @Transactional
    public void createRide() throws Exception {
        int databaseSizeBeforeCreate = rideRepository.findAll().size();

        // Create the Ride
        restRideMockMvc.perform(post("/api/rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ride)))
            .andExpect(status().isCreated());

        // Validate the Ride in the database
        List<Ride> rideList = rideRepository.findAll();
        assertThat(rideList).hasSize(databaseSizeBeforeCreate + 1);
        Ride testRide = rideList.get(rideList.size() - 1);
        assertThat(testRide.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testRide.getMapPoint()).isEqualTo(DEFAULT_MAP_POINT);
        assertThat(testRide.getFrequancy()).isEqualTo(DEFAULT_FREQUANCY);
        assertThat(testRide.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testRide.getSeatAge()).isEqualTo(DEFAULT_SEAT_AGE);
        assertThat(testRide.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testRide.getSeatGender()).isEqualTo(DEFAULT_SEAT_GENDER);
        assertThat(testRide.getRideDateTime()).isEqualTo(DEFAULT_RIDE_DATE_TIME);
        assertThat(testRide.isSmoking()).isEqualTo(DEFAULT_SMOKING);
        assertThat(testRide.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRide.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRide.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rideRepository.findAll().size();

        // Create the Ride with an existing ID
        ride.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRideMockMvc.perform(post("/api/rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ride)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ride> rideList = rideRepository.findAll();
        assertThat(rideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRides() throws Exception {
        // Initialize the database
        rideRepository.saveAndFlush(ride);

        // Get all the rideList
        restRideMockMvc.perform(get("/api/rides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ride.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mapPoint").value(hasItem(DEFAULT_MAP_POINT.toString())))
            .andExpect(jsonPath("$.[*].frequancy").value(hasItem(DEFAULT_FREQUANCY.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].seatAge").value(hasItem(DEFAULT_SEAT_AGE)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].seatGender").value(hasItem(DEFAULT_SEAT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].rideDateTime").value(hasItem(DEFAULT_RIDE_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getRide() throws Exception {
        // Initialize the database
        rideRepository.saveAndFlush(ride);

        // Get the ride
        restRideMockMvc.perform(get("/api/rides/{id}", ride.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ride.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.mapPoint").value(DEFAULT_MAP_POINT.toString()))
            .andExpect(jsonPath("$.frequancy").value(DEFAULT_FREQUANCY.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.seatAge").value(DEFAULT_SEAT_AGE))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.intValue()))
            .andExpect(jsonPath("$.seatGender").value(DEFAULT_SEAT_GENDER.toString()))
            .andExpect(jsonPath("$.rideDateTime").value(DEFAULT_RIDE_DATE_TIME.toString()))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRide() throws Exception {
        // Get the ride
        restRideMockMvc.perform(get("/api/rides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRide() throws Exception {
        // Initialize the database
        rideService.save(ride);

        int databaseSizeBeforeUpdate = rideRepository.findAll().size();

        // Update the ride
        Ride updatedRide = rideRepository.findOne(ride.getId());
        updatedRide
            .address(UPDATED_ADDRESS)
            .mapPoint(UPDATED_MAP_POINT)
            .frequancy(UPDATED_FREQUANCY)
            .destination(UPDATED_DESTINATION)
            .seatAge(UPDATED_SEAT_AGE)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .seatGender(UPDATED_SEAT_GENDER)
            .rideDateTime(UPDATED_RIDE_DATE_TIME)
            .smoking(UPDATED_SMOKING)
            .price(UPDATED_PRICE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);

        restRideMockMvc.perform(put("/api/rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRide)))
            .andExpect(status().isOk());

        // Validate the Ride in the database
        List<Ride> rideList = rideRepository.findAll();
        assertThat(rideList).hasSize(databaseSizeBeforeUpdate);
        Ride testRide = rideList.get(rideList.size() - 1);
        assertThat(testRide.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testRide.getMapPoint()).isEqualTo(UPDATED_MAP_POINT);
        assertThat(testRide.getFrequancy()).isEqualTo(UPDATED_FREQUANCY);
        assertThat(testRide.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRide.getSeatAge()).isEqualTo(UPDATED_SEAT_AGE);
        assertThat(testRide.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testRide.getSeatGender()).isEqualTo(UPDATED_SEAT_GENDER);
        assertThat(testRide.getRideDateTime()).isEqualTo(UPDATED_RIDE_DATE_TIME);
        assertThat(testRide.isSmoking()).isEqualTo(UPDATED_SMOKING);
        assertThat(testRide.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRide.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRide.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRide() throws Exception {
        int databaseSizeBeforeUpdate = rideRepository.findAll().size();

        // Create the Ride

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRideMockMvc.perform(put("/api/rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ride)))
            .andExpect(status().isCreated());

        // Validate the Ride in the database
        List<Ride> rideList = rideRepository.findAll();
        assertThat(rideList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRide() throws Exception {
        // Initialize the database
        rideService.save(ride);

        int databaseSizeBeforeDelete = rideRepository.findAll().size();

        // Get the ride
        restRideMockMvc.perform(delete("/api/rides/{id}", ride.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ride> rideList = rideRepository.findAll();
        assertThat(rideList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ride.class);
        Ride ride1 = new Ride();
        ride1.setId(1L);
        Ride ride2 = new Ride();
        ride2.setId(ride1.getId());
        assertThat(ride1).isEqualTo(ride2);
        ride2.setId(2L);
        assertThat(ride1).isNotEqualTo(ride2);
        ride1.setId(null);
        assertThat(ride1).isNotEqualTo(ride2);
    }
}
