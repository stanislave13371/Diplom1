package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import model.Credentials;
import org.junit.Test;
import steps.UserSteps;

import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseApiTest {
    private final UserSteps userSteps = new UserSteps();

    @Test
    @DisplayName("Логин существующего пользователя")
    public void shouldLoginExistingUser() {
        token = userSteps.register(user).extract().path("accessToken");

        userSteps.login(Credentials.builder()
                        .email(user.getEmail())
                        .password(user.getPassword()).build())
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверными данными")
    public void shouldFailLoginWrongCreds() {
        token = userSteps.register(user).extract().path("accessToken");

        userSteps.login(Credentials.builder()
                        .email(user.getEmail())
                        .password("wrongPass123").build())
                .statusCode(Expected.SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", containsString("incorrect"));
    }
}