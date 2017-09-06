package network.wego.repository;

import network.wego.domain.Message;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select message from Message message where message.userSender.login = ?#{principal.username}")
    List<Message> findByUserSenderIsCurrentUser();

    @Query("select message from Message message where message.userReciver.login = ?#{principal.username}")
    List<Message> findByUserReciverIsCurrentUser();
    
}
