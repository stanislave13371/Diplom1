package utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private final String baseUri;

    public UserApiClient() {
        this("https://stellarburgers.education-services.ru");
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenResponse {
        public boolean success;
        public String accessToken;
    }

    @Step("Регистрация пользователя по API: {email}")
    public String createUser(String email, String password, String name) {
        try {
            Response response = given()
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .body(new CreateUserRequest(email, password, name))
                    .post("/api/auth/register");

            TokenResponse resp = response.as(TokenResponse.class);
            if (resp != null && resp.success && resp.accessToken != null && !resp.accessToken.isEmpty()) {
                return resp.accessToken;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Step("Логин по API: {email}")
    public String login(String email, String password) {
        try {
            Response response = given()
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .body(new LoginRequest(email, password))
                    .post("/api/auth/login");

            TokenResponse resp = response.as(TokenResponse.class);
            if (resp != null && resp.success && resp.accessToken != null && !resp.accessToken.isEmpty()) {
                return resp.accessToken;
            }
        } catch (Exception ignored) { }
        return null;
    }

    @Step("Удаление пользователя по API")
    public void deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) return;

        String bearer = normalizeBearer(accessToken);
        try {
            given()
                    .baseUri(baseUri)
                    .header("Authorization", bearer)
                    .delete("/api/auth/user");
        } catch (Exception ignored) { }
    }

    private static String normalizeBearer(String token) {
        return token.startsWith("Bearer ") ? token : "Bearer " + token;
    }
}
