package nl.wjglerum.country;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/country")
@Produces(MediaType.APPLICATION_JSON)
public class CountriesResource {

    @RestClient
    CountriesClient countriesClient;

    @GET
    @Path("/name/{name}")
    public Uni<Set<Country>> name(String name) {
        return countriesClient.getByName(name)
                .onFailure()
                .retry()
                .atMost(10)
                .log();
    }
}
