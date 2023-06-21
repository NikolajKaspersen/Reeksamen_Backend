package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@NamedQuery(name = "Booking.deleteAllRows", query = "DELETE from Booking ")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String date_and_time;
    private String duration;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "booking_washing_assistant",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "washing_assistant_id", referencedColumnName = "id"))
    private List<WashingAssistant> washingAssistants = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Car car;


    public Booking() {

    }

    public Booking(String date_and_time, String duration) {
        this.date_and_time = date_and_time;
        this.duration = duration;
        this.car = car;
        this.washingAssistants = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<WashingAssistant> getWashingAssistants() {
        return washingAssistants;
    }

    public void setWashingAssistants(List<WashingAssistant> washingAssistants) {
        this.washingAssistants = washingAssistants;
    }

    public void addWashingAssistant(WashingAssistant washingAssistant) {
        this.washingAssistants.add(washingAssistant);
        washingAssistant.addBooking(this);
    }

    public void removeWashingAssistant(WashingAssistant washingAssistant) {
        if (washingAssistant != null) {
            this.washingAssistants.remove(washingAssistant);
        }
    }

    public Car getCar() {
        return car;
    }

    // set Car
    public void setCar(Car car) {
        this.car = car;
    }

    // add Car to Booking
    public void addCar(Car car) {
        if (car != null) {
            this.car = car;
        }
    }

    // remove Car from Booking
    public void removeCar(Car car) {
        if (car != null) {
            this.car = null;
        }
    }


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date_and_time='" + date_and_time + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}