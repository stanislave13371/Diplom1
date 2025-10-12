package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.junit.Test;
import steps.LoginSteps;

import static org.hamcrest.Matchers.*;

public class UserRegistrationTests extends BaseApiTest {
    private final LoginSteps login = new LoginSteps();

    @Test
    @DisplayName("Создание уникального пользователя")
    public void shouldCreateUniqueUser() {
        ValidatableResponse reg = login.register(user)
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());

        token = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void shouldNotCreateExistingUser() {
        token = login.register(user)
                .statusCode(Expected.SC_CREATED_OR_OK)
                .extract().path("accessToken");

        login.register(user)
                .statusCode(Expected.SC_CONFLICT_OR_FORBIDDEN)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("User already exists"),
                        containsString("already")
                ));
    }

    @Test
    @DisplayName("Регистрация без почты (email) -> 403")
    public void shouldNotCreateWithoutEmail() {
        User noEmail = User.builder()
                .name(user.getName())
                .password(user.getPassword())
                .build();

        login.register(noEmail)
                .statusCode(Expected.SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("Email, password and name are required fields"),
                        containsString("required")
                ));
    }

    @Test
    @DisplayName("Регистрация без пароля -> 403")
    public void shouldNotCreateWithoutPassword() {
        User noPassword = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        login.register(noPassword)
                .statusCode(Expected.SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("Email, password and name are required fields"),
                        containsString("required")
                ));
    }

    @Test
    @DisplayName("Регистрация без имени -> 403")
    public void shouldNotCreateWithoutName() {
        User noName = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        login.register(noName)
                .statusCode(Expected.SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("Email, password and name are required fields"),
                        containsString("required")
                ));
    }
}
