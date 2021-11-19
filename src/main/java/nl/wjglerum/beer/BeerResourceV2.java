package nl.wjglerum.beer;

import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/beers/v2")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BeerResourceV2 {

    @Inject
    BeerService beerService;

    @GET
    public Uni<Beer> delay() {
        return Uni.createFrom().completionStage(beerService.getFromDraft());
    }
}
