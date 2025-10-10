package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @SuppressWarnings("unchecked")
    public List<String> getAnyTwoIngredientIds() {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/ingredients")
                .then()
                .statusCode(200)
                .extract()
                .path("data.findAll { it._id }.collect{ it._id }.subList(0,2)");
    }

    public ValidatableResponse createOrder(OrderRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse createOrder(OrderRequest request, String accessTokenWithBearer) {
        return given()
                .header("Authorization", accessTokenWithBearer)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse createOrder(List<String> ingredientIds) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of("ingredients", ingredientIds))
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse createOrder(List<String> ingredientIds, String accessTokenWithBearer) {
        return given()
                .header("Authorization", accessTokenWithBearer)
                .contentType(ContentType.JSON)
                .body(Map.of("ingredients", ingredientIds))
                .when()
                .post("/api/orders")
                .then();
    }
}
