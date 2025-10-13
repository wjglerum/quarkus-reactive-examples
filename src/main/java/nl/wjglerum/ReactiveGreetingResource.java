package nl.wjglerum;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/hello")
public class ReactiveGreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/reactive")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> reactive() {
        return Uni.createFrom().item("Hello reactive!");
    }

    @GET
    @Blocking
    @Path("/blocking")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> blocking() {
        return Uni.createFrom().item("Hello blocking!");
    }

    @GET
    @Path("/exception")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> exception() {
        throw new IllegalArgumentException("Foo!");
    }

    @ServerExceptionMapper(IllegalArgumentException.class)
    public Response illegal() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Whoops").build();
    }
}
