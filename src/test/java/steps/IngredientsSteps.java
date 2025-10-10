package steps;

import api.Endpoints;
import api.Specs;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsSteps extends BaseApi {
    @Step("Получить список ингредиентов")
    public ValidatableResponse getAll() {
        return given().spec(Specs.req())
                .when().get(Endpoints.INGREDIENTS)
                .then();
    }
}