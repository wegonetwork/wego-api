package network.wego.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Terms.
 */
@Entity
@Table(name = "terms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Terms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terms")
    private String terms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerms() {
        return terms;
    }

    public Terms terms(String terms) {
        this.terms = terms;
        return this;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Terms terms = (Terms) o;
        if (terms.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), terms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Terms{" +
            "id=" + getId() +
            ", terms='" + getTerms() + "'" +
            "}";
    }
}
