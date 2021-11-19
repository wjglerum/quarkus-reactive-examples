package nl.wjglerum;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
