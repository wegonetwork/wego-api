package network.wego.service;

import network.wego.domain.Passenger;
import network.wego.repository.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Passenger.
 */
@Service
@Transactional
public class PassengerService {

    private final Logger log = LoggerFactory.getLogger(PassengerService.class);

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    /**
     * Save a passenger.
     *
     * @param passenger the entity to save
     * @return the persisted entity
     */
    public Passenger save(Passenger passenger) {
        log.debug("Request to save Passenger : {}", passenger);
        return passengerRepository.save(passenger);
    }

    /**
     *  Get all the passengers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Passenger> findAll(Pageable pageable) {
        log.debug("Request to get all Passengers");
        return passengerRepository.findAll(pageable);
    }

    /**
     *  Get one passenger by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Passenger findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        return passengerRepository.findOne(id);
    }

    /**
     *  Delete the  passenger by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.delete(id);
    }
}
