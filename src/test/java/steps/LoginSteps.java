package steps;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Credentials;
import model.User;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginSteps extends BaseApi {

    @Step("Регистрация пользователя")
    public ValidatableResponse register(User user) {
        return given().spec(spec())
                .body(user)
                .when().post(Endpoints.REGISTER)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(Credentials creds) {
        return given().spec(spec())
                .body(creds)
                .when().post(Endpoints.LOGIN)
                .then();
    }

    @Step("Логаут по refreshToken")
    public ValidatableResponse logout(String refreshToken) {
        return given().spec(spec())
                .body(Map.of("token", refreshToken))
                .when().post(Endpoints.LOGOUT)
                .then();
    }
}
