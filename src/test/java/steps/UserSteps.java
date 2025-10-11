package steps;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps extends BaseApi {

    @Step("Удалить пользователя (по accessToken)")
    public ValidatableResponse delete(String accessToken) {
        return given().spec(spec())
                .header("Authorization", accessToken)
                .when().delete(Endpoints.USER)
                .then();
    }
}
