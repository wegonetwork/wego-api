package network.wego.repository;

import network.wego.domain.EthAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EthAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EthAccountRepository extends JpaRepository<EthAccount,Long> {
    
}
