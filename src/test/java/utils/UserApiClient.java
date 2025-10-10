package utils;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class UserApiClient {

    private final String baseUri;

    public UserApiClient() {
        this("https://stellarburgers.nomoreparties.site");
    }

    public UserApiClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public static class CreateUserRequest {
        public String email;
        public String password;
        public String name;
        public CreateUserRequest(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }
    }

    public static class LoginRequest {
        public String email;
        public String password;
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class TokenResponse {
        public boolean success;
        public String accessToken;
        public String refreshToken;
    }

    public String createUser(String email, String password, String name) {
        TokenResponse resp = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(new CreateUserRequest(email, password, name))
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .extract().as(TokenResponse.class);

        return resp != null ? resp.accessToken : null;
    }

    public String login(String email, String password) {
        TokenResponse resp = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(new LoginRequest(email, password))
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract().as(TokenResponse.class);

        return resp != null ? resp.accessToken : null;
    }

    public void deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) return;

        given()
                .baseUri(baseUri)
                .header("Authorization", accessToken)
                .delete("/api/auth/user")
                .then()
                .statusCode(anyOf(is(202), is(200)));
    }
}
