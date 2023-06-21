package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookingDto;
import entities.Booking;
import facades.BookingFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bookings")
public class BookingResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final BookingFacade bookingFacade = BookingFacade.getBookingFacade(EMF);

    @GET
//    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        List<BookingDto> bookingDtos = bookingFacade.getAllBookings();
        String json = GSON.toJson(bookingDtos);
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") long id) {
        BookingDto bookingDto = bookingFacade.getBookingById(id);
        if (bookingDto != null) {
            String json = GSON.toJson(bookingDto);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(String json) {
        BookingDto bookingDto = GSON.fromJson(json, BookingDto.class);
        BookingDto createdBookingDto = bookingFacade.createBooking(bookingDto);
        String responseJson = GSON.toJson(createdBookingDto);
        return Response.status(Response.Status.CREATED).entity(responseJson).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(String jsonBooking) {
        BookingDto bookingDto = GSON.fromJson(jsonBooking, BookingDto.class);
        BookingDto updatedBookingDto = bookingFacade.editBooking(bookingDto);
        return Response.ok(GSON.toJson(updatedBookingDto)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") long id) {
        BookingDto deletedBookingDto = bookingFacade.deleteBookingById(id);
        if (deletedBookingDto != null) {
            String responseJson = GSON.toJson(deletedBookingDto);
            return Response.ok(responseJson).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
