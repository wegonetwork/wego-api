package network.wego.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import network.wego.domain.enumeration.RideStatus;

/**
 * A RideRequest.
 */
@Entity
@Table(name = "ride_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RideRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RideStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Ride ride;

    @ManyToOne(optional = false)
    @NotNull
    private Passenger passenger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RideStatus getStatus() {
        return status;
    }

    public RideRequest status(RideStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Ride getRide() {
        return ride;
    }

    public RideRequest ride(Ride ride) {
        this.ride = ride;
        return this;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public RideRequest passenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RideRequest rideRequest = (RideRequest) o;
        if (rideRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rideRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RideRequest{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
