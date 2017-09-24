package network.wego.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EthAccount.
 */
@Entity
@Table(name = "eth_account")
public class EthAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_account")
    private String account;

    @Column(name = "jhi_date")
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public EthAccount account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public EthAccount date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EthAccount ethAccount = (EthAccount) o;
        if (ethAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ethAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EthAccount{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
