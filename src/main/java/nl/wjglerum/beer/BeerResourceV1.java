package nl.wjglerum.beer;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/beers/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BeerResourceV1 {

    @GET
    public Uni<List<Beer>> all() {
        return Beer.listAll(Sort.by("name"));
    }

    @GET
    @Path("/{id}")
    public Uni<Beer> getById(@PathParam("id") Long id) {
        return Beer.findById(id);
    }

    @POST
    public Uni<Response> create(Beer beer) {
        return Panache.<Beer>withTransaction(beer::persist)
                .onItem()
                .transform(inserted -> URI.create("/beers/v1/" + inserted.id))
                .onItem()
                .transform(uri -> Response.seeOther(uri).build());
    }

    @ServerExceptionMapper
    public Response persistenceException(PersistenceException exception) {
        Log.error(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
