package network.wego.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EthAccount.
 */
@Entity
@Table(name = "eth_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EthAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ethaccount")
    private String ethaccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEthaccount() {
        return ethaccount;
    }

    public EthAccount ethaccount(String ethaccount) {
        this.ethaccount = ethaccount;
        return this;
    }

    public void setEthaccount(String ethaccount) {
        this.ethaccount = ethaccount;
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
            ", ethaccount='" + getEthaccount() + "'" +
            "}";
    }
}
