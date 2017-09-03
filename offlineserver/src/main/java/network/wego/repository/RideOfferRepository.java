package network.wego.repository;

import network.wego.domain.RideOffer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RideOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RideOfferRepository extends JpaRepository<RideOffer,Long> {
    
}
