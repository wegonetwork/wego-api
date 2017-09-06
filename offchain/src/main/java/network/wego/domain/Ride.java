package network.wego.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import network.wego.domain.enumeration.Gender;

import network.wego.domain.enumeration.RideStatus;

/**
 * A Ride.
 */
@Entity
@Table(name = "ride")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ride implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "destination")
    private String destination;

    @Column(name = "map_point")
    private String mapPoint;

    @Column(name = "ride_date_time")
    private Instant rideDateTime;

    @Column(name = "frequancy")
    private String frequancy;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_gender")
    private Gender seatGender;

    @Column(name = "seat_age")
    private Integer seatAge;

    @Column(name = "smoking")
    private Boolean smoking;

    @Column(name = "price")
    private Double price;

    @Column(name = "luggage")
    private String luggage;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "jhi_comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RideStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Vehcile vehcile;

    @ManyToOne(optional = false)
    @NotNull
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Ride address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDestination() {
        return destination;
    }

    public Ride destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMapPoint() {
        return mapPoint;
    }

    public Ride mapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
        return this;
    }

    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }

    public Instant getRideDateTime() {
        return rideDateTime;
    }

    public Ride rideDateTime(Instant rideDateTime) {
        this.rideDateTime = rideDateTime;
        return this;
    }

    public void setRideDateTime(Instant rideDateTime) {
        this.rideDateTime = rideDateTime;
    }

    public String getFrequancy() {
        return frequancy;
    }

    public Ride frequancy(String frequancy) {
        this.frequancy = frequancy;
        return this;
    }

    public void setFrequancy(String frequancy) {
        this.frequancy = frequancy;
    }

    public Gender getSeatGender() {
        return seatGender;
    }

    public Ride seatGender(Gender seatGender) {
        this.seatGender = seatGender;
        return this;
    }

    public void setSeatGender(Gender seatGender) {
        this.seatGender = seatGender;
    }

    public Integer getSeatAge() {
        return seatAge;
    }

    public Ride seatAge(Integer seatAge) {
        this.seatAge = seatAge;
        return this;
    }

    public void setSeatAge(Integer seatAge) {
        this.seatAge = seatAge;
    }

    public Boolean isSmoking() {
        return smoking;
    }

    public Ride smoking(Boolean smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Double getPrice() {
        return price;
    }

    public Ride price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLuggage() {
        return luggage;
    }

    public Ride luggage(String luggage) {
        this.luggage = luggage;
        return this;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public Ride mobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getComment() {
        return comment;
    }

    public Ride comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RideStatus getStatus() {
        return status;
    }

    public Ride status(RideStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Vehcile getVehcile() {
        return vehcile;
    }

    public Ride vehcile(Vehcile vehcile) {
        this.vehcile = vehcile;
        return this;
    }

    public void setVehcile(Vehcile vehcile) {
        this.vehcile = vehcile;
    }

    public City getCity() {
        return city;
    }

    public Ride city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ride ride = (Ride) o;
        if (ride.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ride.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ride{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", destination='" + getDestination() + "'" +
            ", mapPoint='" + getMapPoint() + "'" +
            ", rideDateTime='" + getRideDateTime() + "'" +
            ", frequancy='" + getFrequancy() + "'" +
            ", seatGender='" + getSeatGender() + "'" +
            ", seatAge='" + getSeatAge() + "'" +
            ", smoking='" + isSmoking() + "'" +
            ", price='" + getPrice() + "'" +
            ", luggage='" + getLuggage() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
