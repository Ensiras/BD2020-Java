package helloRest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("helloworld")
public class HelloWorldResource {

    // Simple get method return just a string (Response obj implicitly created)
    @GET
    public String helloWorld() {
        return "Hello World!";
    }

    // Get method with query parameter(s), can then be used for e.g. the response
    // Use in browser: ?firstname=value&lastname=value
    @GET
    @Path("ok")
    public Response ok(@QueryParam("firstname") String firstname,
                       @QueryParam("lastname") String lastname) {
        return Response.ok().entity(firstname).build();
    }

    // Get method with id parameter, allowing for a variable path.
    @GET
    @Path("notok/{id}")
    public Response notOk(@PathParam("id") String id) {
        return Response.ok().entity("Not ok " + id).build();
    }

}
