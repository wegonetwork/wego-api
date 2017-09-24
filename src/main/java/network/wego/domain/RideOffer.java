package network.wego.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import network.wego.domain.enumeration.RideStatus;

/**
 * A RideOffer.
 */
@Entity
@Table(name = "ride_offer")
public class RideOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RideStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Driver driver;

    @ManyToOne(optional = false)
    @NotNull
    private Ride ride;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RideStatus getStatus() {
        return status;
    }

    public RideOffer status(RideStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public RideOffer driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Ride getRide() {
        return ride;
    }

    public RideOffer ride(Ride ride) {
        this.ride = ride;
        return this;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RideOffer rideOffer = (RideOffer) o;
        if (rideOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rideOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RideOffer{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
