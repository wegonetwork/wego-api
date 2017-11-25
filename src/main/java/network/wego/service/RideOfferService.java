package network.wego.service;

import network.wego.domain.RideOffer;
import network.wego.repository.RideOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RideOffer.
 */
@Service
@Transactional
public class RideOfferService {

    private final Logger log = LoggerFactory.getLogger(RideOfferService.class);

    private final RideOfferRepository rideOfferRepository;

    public RideOfferService(RideOfferRepository rideOfferRepository) {
        this.rideOfferRepository = rideOfferRepository;
    }

    /**
     * Save a rideOffer.
     *
     * @param rideOffer the entity to save
     * @return the persisted entity
     */
    public RideOffer save(RideOffer rideOffer) {
        log.debug("Request to save RideOffer : {}", rideOffer);
        return rideOfferRepository.save(rideOffer);
    }

    /**
     *  Get all the rideOffers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RideOffer> findAll(Pageable pageable) {
        log.debug("Request to get all RideOffers");
        return rideOfferRepository.findAll(pageable);
    }

    /**
     *  Get one rideOffer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RideOffer findOne(Long id) {
        log.debug("Request to get RideOffer : {}", id);
        return rideOfferRepository.findOne(id);
    }

    /**
     *  Delete the  rideOffer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RideOffer : {}", id);
        rideOfferRepository.delete(id);
    }
}
