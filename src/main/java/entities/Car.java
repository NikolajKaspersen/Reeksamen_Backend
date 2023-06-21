package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private int registration_number;
    private String brand;
    private String make;
    private String year;


    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public Car() {

    }

    public Car(int registration_number, String brand, String make, String year) {
        this.registration_number = registration_number;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.bookings = new ArrayList<>();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setCar(this);
    }


    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", registration_number=" + registration_number +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}