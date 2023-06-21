package dtos;

import entities.Booking;
import entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO for {@link entities.Booking}
 */
public class BookingDto implements Serializable {
    private final Long id;
    private final String date_and_time;
    private final String duration;

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.date_and_time = booking.getDate_and_time();
        this.duration = booking.getDuration();
    }

    public static List<BookingDto> getDtos(List<Booking> bookings){
        return bookings.stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

//    public static List<BookingDto> getDtos(List<Booking> bookings) {
//        List<BookingDto> bookingDtos = new ArrayList<>();
//        bookings.forEach(booking -> bookingDtos.add(new BookingDto(booking)));
//        return bookingDtos;
//    }

    public Long getId() {
        return id;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto entity = (BookingDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.date_and_time, entity.date_and_time) &&
                Objects.equals(this.duration, entity.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_and_time, duration);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "date_and_time = " + date_and_time + ", " +
                "duration = " + duration + ")";
    }
}