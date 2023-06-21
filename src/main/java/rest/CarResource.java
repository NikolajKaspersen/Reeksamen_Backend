package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDto;
import entities.Car;
import facades.CarFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cars")
public class CarResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final CarFacade carFacade = CarFacade.getCarFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCars() {
        List<CarDto> carDtos = carFacade.getAllCars();
        String json = GSON.toJson(carDtos);
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarById(@PathParam("id") long id) {
        CarDto carDto = carFacade.getCarById(id);
        if (carDto != null) {
            String json = GSON.toJson(carDto);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCar(String json) {
        CarDto carDto = GSON.fromJson(json, CarDto.class);
        CarDto createdCarDto = carFacade.createCar(carDto);
        String responseJson = GSON.toJson(createdCarDto);
        return Response.status(Response.Status.CREATED).entity(responseJson).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(@PathParam("id") long id, String jsonCar) {
        CarDto carDto = GSON.fromJson(jsonCar, CarDto.class);
        CarDto updatedCarDto = carFacade.editCar(carDto);
        return Response.ok(GSON.toJson(updatedCarDto)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") long id) {
        CarDto deletedCarDto = carFacade.deleteById(id);
        if (deletedCarDto != null) {
            String responseJson = GSON.toJson(deletedCarDto);
            return Response.ok(responseJson).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
