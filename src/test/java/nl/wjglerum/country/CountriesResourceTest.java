package nl.wjglerum.country;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@ConnectWireMock
@TestHTTPEndpoint(CountriesResource.class)
public class CountriesResourceTest {

    WireMock wiremock;

    @BeforeEach
    public void setupWireMock() {
        var response = """
                [
                  {
                    "name": "Netherlands",
                    "alpha2Code": "NL",
                    "capital": "Amsterdam",
                    "currencies": [
                      {
                        "code": "EUR",
                        "name": "Euro",
                        "symbol": "€"
                      }
                    ]
                  }
                ]
                """;
        wiremock.register(get(urlEqualTo("/v2/name/netherlands")).willReturn(
                aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(response))
        );
    }

    @Test
    public void testCountryNameEndpoint() {
        given()
                .when()
                .get("/name/netherlands")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].alpha2Code", is("NL"),
                        "[0].capital", is("Amsterdam"),
                        "[0].currencies.size()", is(1),
                        "[0].currencies[0].name", is("Euro")
                );
    }
}
