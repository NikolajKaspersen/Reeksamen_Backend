package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDto;
import entities.User;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserDto> userDtos = userFacade.getAllUsers();
        String json = GSON.toJson(userDtos);
        return Response.ok(json).build();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username) {
        UserDto userDto = userFacade.getUserByUsername(username);
        if (userDto != null) {
            String json = GSON.toJson(userDto);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String json) {
        UserDto userDto = GSON.fromJson(json, UserDto.class);
        UserDto createdUserDto = userFacade.createUser(userDto);
        String responseJson = GSON.toJson(createdUserDto);
        return Response.status(Response.Status.CREATED).entity(responseJson).build();
    }

    @PUT
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("username") long id, String jsonUser) {
        UserDto userDto = GSON.fromJson(jsonUser, UserDto.class);
        UserDto updatedUserDto = userFacade.editUser(userDto);
        return Response.ok(GSON.toJson(updatedUserDto)).build();
    }

    @DELETE
    @Path("{Username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("Username") String username) {
        UserDto deletedUserDto = userFacade.deleteUserByUsername(username);
        if (deletedUserDto != null) {
            String responseJson = GSON.toJson(deletedUserDto);
            return Response.ok(responseJson).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
