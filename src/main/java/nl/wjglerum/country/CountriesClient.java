package nl.wjglerum.country;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

@Path("/v2")
@RegisterRestClient(configKey = "country-api")
public interface CountriesClient {

    @GET
    @Path("/name/{countryName}")
    Uni<Set<Country>> getByName(String countryName);
}
