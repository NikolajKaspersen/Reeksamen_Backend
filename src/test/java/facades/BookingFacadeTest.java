package facades;

import dtos.BookingDto;
import entities.Booking;
import facades.BookingFacade;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingFacadeTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private BookingFacade bookingFacade;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
    }

    @AfterAll
    public static void tearDownClass() {
        EMF_Creator.endREST_TestWithDB();
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        bookingFacade = BookingFacade.getBookingFacade(emf);

        // Clear existing data and add test bookings
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Booking").executeUpdate();
        em.persist(new Booking("30th May", "90min"));
        em.persist(new Booking("5th June", "30min"));
        em.getTransaction().commit();
    }

    @AfterEach
    public void tearDown() {
        em.close();
    }

    @Test
    public void testGetAllBookings() {
        // Act
        List<BookingDto> result = bookingFacade.getAllBookings();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookingById() {
        // Arrange
        Booking booking = em.find(Booking.class, 1L);

        // Act
        BookingDto result = bookingFacade.getBookingById(1L);

        // Assert
        assertEquals(booking.getDate_and_time(), result.getDate_and_time());
        assertEquals(booking.getDuration(), result.getDuration());
    }
    @Test
    public void testCreateBooking() {
        // Arrange
        String dateAndTime = "5th April 18:30";
        String duration = "30min";

        // Create a BookingDto directly
        BookingDto bookingDto = new BookingDto(new Booking(dateAndTime, duration));

        // Act
        BookingDto result = bookingFacade.createBooking(bookingDto);

        // Assert
        assertNotNull(result.getId());
        assertEquals(dateAndTime, result.getDate_and_time());
        assertEquals(duration, result.getDuration());
    }


    // Test andre metoder i BookingFacade på lignende måde
}
