package network.wego.repository;

import network.wego.domain.RideRequest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RideRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
    
}
