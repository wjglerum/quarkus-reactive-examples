package nl.wjglerum.country;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Path("/v2")
@RegisterRestClient(configKey = "country-api")
public interface CountriesClient {

    @GET
    @Path("/name/{countryName}")
    Uni<Set<Country>> getByName(String countryName);
}
