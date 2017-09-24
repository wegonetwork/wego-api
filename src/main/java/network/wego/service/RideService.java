package network.wego.service;

import network.wego.domain.Ride;
import network.wego.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ride.
 */
@Service
@Transactional
public class RideService {

    private final Logger log = LoggerFactory.getLogger(RideService.class);

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    /**
     * Save a ride.
     *
     * @param ride the entity to save
     * @return the persisted entity
     */
    public Ride save(Ride ride) {
        log.debug("Request to save Ride : {}", ride);
        return rideRepository.save(ride);
    }

    /**
     *  Get all the rides.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Ride> findAll(Pageable pageable) {
        log.debug("Request to get all Rides");
        return rideRepository.findAll(pageable);
    }

    /**
     *  Get one ride by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Ride findOne(Long id) {
        log.debug("Request to get Ride : {}", id);
        return rideRepository.findOne(id);
    }

    /**
     *  Delete the  ride by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ride : {}", id);
        rideRepository.delete(id);
    }
}
