package network.wego.repository;

import network.wego.domain.Terms;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Terms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermsRepository extends JpaRepository<Terms,Long> {
    
}
