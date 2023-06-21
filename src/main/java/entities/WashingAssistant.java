package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "washing_assistant")
@NamedQuery(name = "WashingAssistant.deleteAllRows", query = "DELETE from WashingAssistant")
public class WashingAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String Primary_language;
    private String years_of_experience;
    private int price_per_hour;

    @ManyToMany(mappedBy = "washingAssistants", cascade = CascadeType.PERSIST)
    private List<Booking> bookings = new ArrayList<>();


    public WashingAssistant() {

    }


    public WashingAssistant(String name, String Primary_language, String years_of_experience, int price_per_hour) {
        this.name = name;
        this.Primary_language = Primary_language;
        this.years_of_experience = years_of_experience;
        this.price_per_hour = price_per_hour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimary_language() {
        return Primary_language;
    }

    public void setPrimary_language(String primary_language) {
        Primary_language = primary_language;
    }

    public String getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(String years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    public int getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(int price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    // Get the list of bookings
    public List<Booking> getBookings() {
        return bookings;
    }

    // Set the list of bookings
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // Add a booking to the list of bookings
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.getWashingAssistants().add(this);
    }

    // Remove a booking from the list of bookings
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.getWashingAssistants().remove(this);
    }

    @Override
    public String toString() {
        return "WashingAssistant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Primary_language='" + Primary_language + '\'' +
                ", years_of_experience='" + years_of_experience + '\'' +
                ", price_per_hour=" + price_per_hour +
                '}';
    }
}