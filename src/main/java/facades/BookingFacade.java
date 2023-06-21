package facades;

import dtos.BookingDto;
import dtos.CarDto;
import entities.Booking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class BookingFacade {

    private static BookingFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BookingFacade() {

    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */

    public static BookingFacade getBookingFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookingFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<BookingDto> getAllBookings() {
        EntityManager em = getEntityManager();
        List<Booking> bookings;
        try {
            TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b", Booking.class);
            bookings = query.getResultList();
        } finally {
            em.close();
        }
        return BookingDto.getDtos(bookings);
    }

    public BookingDto getBookingById(long id) {
        EntityManager em = getEntityManager();
        try {
            Booking booking = em.find(Booking.class, id);
            return new BookingDto(booking);
        } finally {
            em.close();
        }
    }

    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = new Booking(bookingDto.getDate_and_time(), bookingDto.getDuration());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookingDto(booking);
    }

    public BookingDto deleteBookingById(long id) {
        EntityManager em = getEntityManager();
        Booking booking = em.find(Booking.class, id);
        try {
            em.getTransaction().begin();
            em.remove(booking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookingDto(booking);
    }

    public BookingDto editBooking(BookingDto bookingDto) {
        EntityManager em = getEntityManager();
        try {
            Booking booking = em.find(Booking.class, bookingDto.getId());
            em.getTransaction().begin();
            booking.setDate_and_time(bookingDto.getDate_and_time());
            booking.setDuration(bookingDto.getDuration());
            em.getTransaction().commit();
            return new BookingDto(booking);
        } finally {
            em.close();
        }
    }
}
