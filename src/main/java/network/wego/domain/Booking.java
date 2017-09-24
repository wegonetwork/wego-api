package network.wego.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import network.wego.domain.enumeration.BookingStatus;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

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

    public BookingStatus getStatus() {
        return status;
    }

    public Booking status(BookingStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Ride getRide() {
        return ride;
    }

    public Booking ride(Ride ride) {
        this.ride = ride;
        return this;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Booking passenger(Passenger passenger) {
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
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
