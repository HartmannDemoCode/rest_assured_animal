package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AnimalDTO;
import errorhandling.AnimalNotFoundException;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("animal")
public class AnimalRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final FacadeExample FACADE =  FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {

        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        List<AnimalDTO> rns = FACADE.getAll();
        return Response.ok().entity(GSON.toJson(rns)).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id")long id) {
        try {
            AnimalDTO rd = FACADE.getById(id);
            return Response.ok().entity(GSON.toJson(rd)).build();
        } catch (AnimalNotFoundException ex){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String a) {
        AnimalDTO animalDto = GSON.fromJson(a, AnimalDTO.class);
        AnimalDTO result = FACADE.create(animalDto);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") long id, String a){
        AnimalDTO animalDto = GSON.fromJson(a, AnimalDTO.class);
        animalDto.setId(id);
        AnimalDTO result = FACADE.update(animalDto);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") long id) throws AnimalNotFoundException  {
        AnimalDTO result;
//        try {
            
            result = FACADE.delete(id);    
//        } catch (Exception e) {
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
        
        return Response.ok().entity(GSON.toJson(result)).build();
    }
}
