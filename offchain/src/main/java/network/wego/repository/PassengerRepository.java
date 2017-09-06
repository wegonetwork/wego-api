package network.wego.repository;

import network.wego.domain.Passenger;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Passenger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    @Query("select passenger from Passenger passenger where passenger.user.login = ?#{principal.username}")
    List<Passenger> findByUserIsCurrentUser();
    
}
