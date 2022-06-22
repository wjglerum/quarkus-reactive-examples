package nl.wjglerum.beer;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/beers/v3")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BeerResourceV3 {

    @Inject
    BeerRepository beerRepository;

    @Inject
    @Channel("beers-out")
    Emitter<Beer> beerEmitter;

    @GET
    public Multi<Beer> beers() {
        return beerRepository.streamAll();
    }

    @GET
    @Path("/{name}")
    public Uni<Beer> getByName(@PathParam("name") String name) {
        return beerRepository.findByName(name);
    }

    @POST
    public Uni<Response> create(Beer beer) {
        return Panache.<Beer>withTransaction(beer::persist)
                .invoke(inserted -> beerEmitter.send(inserted))
                .map(inserted -> URI.create("/beers/v3/" + inserted.name))
                .map(uri -> Response.seeOther(uri).build());
    }

    @Incoming("beers-in")
    public void log(Beer beer) {
        Log.infof("Awesome! A new beer was registered: %s", beer.name);
    }

    @ServerExceptionMapper
    public Response persistenceException(PersistenceException exception) {
        Log.error(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
