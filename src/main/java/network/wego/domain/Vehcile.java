package network.wego.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Vehcile.
 */
@Entity
@Table(name = "vehcile")
public class Vehcile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "company")
    private String company;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "color")
    private String color;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @Column(name = "car_image_url")
    private String carImageURL;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vehcile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public Vehcile model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public Vehcile company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getNumber() {
        return number;
    }

    public Vehcile number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public Vehcile color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public Vehcile seatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
        return this;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getCarImageURL() {
        return carImageURL;
    }

    public Vehcile carImageURL(String carImageURL) {
        this.carImageURL = carImageURL;
        return this;
    }

    public void setCarImageURL(String carImageURL) {
        this.carImageURL = carImageURL;
    }

    public User getUser() {
        return user;
    }

    public Vehcile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehcile vehcile = (Vehcile) o;
        if (vehcile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehcile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehcile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", company='" + getCompany() + "'" +
            ", number='" + getNumber() + "'" +
            ", color='" + getColor() + "'" +
            ", seatsNumber='" + getSeatsNumber() + "'" +
            ", carImageURL='" + getCarImageURL() + "'" +
            "}";
    }
}
