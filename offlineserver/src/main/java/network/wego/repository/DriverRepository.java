package network.wego.repository;

import network.wego.domain.Driver;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query("select driver from Driver driver where driver.user.login = ?#{principal.username}")
    List<Driver> findByUserIsCurrentUser();
    
}
