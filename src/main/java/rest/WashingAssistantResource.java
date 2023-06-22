package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.WashingAssistantDto;
import entities.WashingAssistant;
import facades.WashingAssistantFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("washingassistants")
public class WashingAssistantResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final WashingAssistantFacade washingAssistantFacade = WashingAssistantFacade.getWashingAssistantFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWashingAssistants() {
        List<WashingAssistantDto> washingAssistantDtos = washingAssistantFacade.getAllWashingAssistants();
        String json = GSON.toJson(washingAssistantDtos);
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWashingAssistantById(@PathParam("id") long id) {
        WashingAssistantDto washingAssistantDto = washingAssistantFacade.getWashingAssistantById(id);
        if (washingAssistantDto != null) {
            String json = GSON.toJson(washingAssistantDto);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWashingAssistant(String json) {
        WashingAssistant washingAssistant = GSON.fromJson(json, WashingAssistant.class);
        WashingAssistantDto washingAssistantDto = new WashingAssistantDto(washingAssistant);
        WashingAssistantDto createdWashingAssistantDto = washingAssistantFacade.createWashingAssistant(washingAssistantDto);
        String responseJson = GSON.toJson(createdWashingAssistantDto);
        return Response.status(Response.Status.CREATED).entity(responseJson).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateWashingAssistant(@PathParam("id") long id, String jsonWashingAssistant) {
        WashingAssistantDto washingAssistantDto = GSON.fromJson(jsonWashingAssistant, WashingAssistantDto.class);
        WashingAssistantDto updatedWashingAssistantDto = washingAssistantFacade.editWashingAssistant(washingAssistantDto);
        return Response.ok(GSON.toJson(updatedWashingAssistantDto)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWashingAssistant(@PathParam("id") long id) {
        WashingAssistantDto deletedWashingAssistantDto = washingAssistantFacade.deleteWashingAssistantById(id);
        if (deletedWashingAssistantDto != null) {
            String responseJson = GSON.toJson(deletedWashingAssistantDto);
            return Response.ok(responseJson).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
