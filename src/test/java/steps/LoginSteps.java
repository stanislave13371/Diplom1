package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Credentials;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginSteps {

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
}
