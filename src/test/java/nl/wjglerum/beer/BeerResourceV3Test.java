package nl.wjglerum.beer;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@TestHTTPEndpoint(BeerResourceV3.class)
public class BeerResourceV3Test {

    @Test
    public void testListEndpoint() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    public void testNameEndpoint() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("Grolsch")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", is("Grolsch"));
    }

    @Test
    public void testNoneEndpoint() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("Heineken")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreateEndpoint() {
        var data = Map.of("name", "Karmeliet");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .redirects()
                .follow(false)
                .body(data)
                .when()
                .post()
                .then()
                .statusCode(303)
                .header("location", containsString("/beers/v3/"));
    }
}
