package network.wego.repository;

import network.wego.domain.Vehcile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Vehcile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehcileRepository extends JpaRepository<Vehcile,Long> {

    @Query("select vehcile from Vehcile vehcile where vehcile.user.login = ?#{principal.username}")
    List<Vehcile> findByUserIsCurrentUser();
    
}
