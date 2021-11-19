package nl.wjglerum.country;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CountriesResourceTest {

    @Test
    public void testCountryNameEndpoint() {
        given()
                .when().get("/country/name/netherlands")
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
