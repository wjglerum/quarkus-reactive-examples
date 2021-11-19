package nl.wjglerum.beer;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
    @Channel("beers")
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
        return beerRepository.persist(beer)
                .invoke(inserted -> beerEmitter.send(inserted))
                .map(inserted -> URI.create("/beers/v3/" + inserted.id))
                .map(uri -> Response.seeOther(uri).build());
    }

    @Incoming("beers")
    public void log(Beer beer) {
        Log.infof("Awesome! A new beer was registered: %s", beer.name);
    }
}
