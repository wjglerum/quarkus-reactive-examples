package nl.wjglerum.country;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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
