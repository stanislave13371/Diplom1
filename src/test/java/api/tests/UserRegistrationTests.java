package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.Test;
import steps.UserSteps;

import static org.hamcrest.Matchers.*;

public class UserRegistrationTests extends BaseApiTest {
    private final UserSteps userSteps = new UserSteps();

    @Test
    @DisplayName("Создание уникального пользователя")
    public void shouldCreateUniqueUser() {
        token = userSteps.register(user)
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void shouldNotCreateExistingUser() {
        token = userSteps.register(user)
                .statusCode(Expected.SC_CREATED_OR_OK)
                .extract().path("accessToken");

        userSteps.register(user)
                .statusCode(anyOf(is(403), is(409)))
                .body("success", is(false))
                .body("message", containsString("already"));
    }

    @Test
    @DisplayName("Регистрация без обязательного поля")
    public void shouldNotCreateWithoutRequiredField() {
        User noName = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        userSteps.register(noName)
                .statusCode(Expected.SC_CONFLICT_OR_FORBIDDEN)
                .body("success", is(false))
                .body("message", containsString("required"));
    }
}