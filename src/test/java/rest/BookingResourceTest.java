package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dtos.BookingDto;
import entities.Booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import rest.ApplicationConfig;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

public class BookingResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static final Gson GSON = new Gson();

    private Booking b1;
    private Booking b2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        b1 = new Booking("30th May", "90min");
        b2 = new Booking("5th June", "30min");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Booking.deleteAllRows").executeUpdate();
            em.persist(b1);
            em.persist(b2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Booking.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given()
                .when()
                .get("/bookings")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing log response");
        given()
                .log().all()
                .when()
                .get("/bookings")
                .then()
                .log().body().statusCode(200);
    }

    @Test
    public void testCount() {
        given()
                .contentType("application/json")
                .get("/bookings").then()
                .assertThat()
                .statusCode(200).body("size()", org.hamcrest.Matchers.is(2));
    }

    @Test
    public void testGetBookingById() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/bookings/{id}")
                .then()
                .statusCode(200)
                .body("date_and_time", equalTo("5th June"))
                .body("duration", equalTo("30min"));
    }

    @Test
    public void testCreateBooking() {
        JsonObject newBooking = new JsonObject();
        newBooking.addProperty("date_and_time", "5th April 18:30");
        newBooking.addProperty("duration", "30min");

        given()
                .contentType("application/json")
                .body(newBooking.toString())
                .post("/bookings")
                .then()
                .statusCode(201)
                .body("date_and_time", equalTo("5th April 18:30"))
                .body("duration", equalTo("30min"));
    }

}
