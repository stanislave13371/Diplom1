package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.Credentials;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public ValidatableResponse register(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    public ValidatableResponse login(Credentials creds) {
        return given()
                .contentType(ContentType.JSON)
                .body(creds)
                .when()
                .post("/api/auth/login")
                .then();
    }

    public ValidatableResponse logout(String refreshToken) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of("token", refreshToken))
                .when()
                .post("/api/auth/logout")
                .then();
    }

    public ValidatableResponse delete(String accessTokenWithBearer) {
        return given()
                .header("Authorization", accessTokenWithBearer)
                .when()
                .delete("/api/auth/user")
                .then();
    }
}