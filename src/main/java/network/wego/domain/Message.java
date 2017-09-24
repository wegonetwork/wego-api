package network.wego.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "subject")
    private String subject;

    @Column(name = "msg_date_time")
    private Instant msgDateTime;

    @ManyToOne(optional = false)
    @NotNull
    private User userSender;

    @ManyToOne(optional = false)
    @NotNull
    private User userReciver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Message message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public Message subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getMsgDateTime() {
        return msgDateTime;
    }

    public Message msgDateTime(Instant msgDateTime) {
        this.msgDateTime = msgDateTime;
        return this;
    }

    public void setMsgDateTime(Instant msgDateTime) {
        this.msgDateTime = msgDateTime;
    }

    public User getUserSender() {
        return userSender;
    }

    public Message userSender(User user) {
        this.userSender = user;
        return this;
    }

    public void setUserSender(User user) {
        this.userSender = user;
    }

    public User getUserReciver() {
        return userReciver;
    }

    public Message userReciver(User user) {
        this.userReciver = user;
        return this;
    }

    public void setUserReciver(User user) {
        this.userReciver = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", subject='" + getSubject() + "'" +
            ", msgDateTime='" + getMsgDateTime() + "'" +
            "}";
    }
}
