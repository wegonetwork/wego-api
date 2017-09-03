package network.wego.service;

import network.wego.domain.RideRequest;
import network.wego.repository.RideRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RideRequest.
 */
@Service
@Transactional
public class RideRequestService {

    private final Logger log = LoggerFactory.getLogger(RideRequestService.class);

    private final RideRequestRepository rideRequestRepository;

    public RideRequestService(RideRequestRepository rideRequestRepository) {
        this.rideRequestRepository = rideRequestRepository;
    }

    /**
     * Save a rideRequest.
     *
     * @param rideRequest the entity to save
     * @return the persisted entity
     */
    public RideRequest save(RideRequest rideRequest) {
        log.debug("Request to save RideRequest : {}", rideRequest);
        return rideRequestRepository.save(rideRequest);
    }

    /**
     *  Get all the rideRequests.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RideRequest> findAll(Pageable pageable) {
        log.debug("Request to get all RideRequests");
        return rideRequestRepository.findAll(pageable);
    }

    /**
     *  Get one rideRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RideRequest findOne(Long id) {
        log.debug("Request to get RideRequest : {}", id);
        return rideRequestRepository.findOne(id);
    }

    /**
     *  Delete the  rideRequest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RideRequest : {}", id);
        rideRequestRepository.delete(id);
    }
}
