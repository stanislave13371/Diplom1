package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Credentials;
import org.junit.Before;
import org.junit.Test;
import steps.LoginSteps;

import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseApiTest {
    private final LoginSteps login = new LoginSteps();

    @Before
    public void createUser() {
        ValidatableResponse reg = login.register(user)
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true));
        token = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");
    }

    @Test
    @DisplayName("Логин существующего пользователя")
    public void shouldLoginExistingUser() {
        login.login(Credentials.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверными данными -> 401 и точное сообщение")
    public void shouldFailLoginWrongCreds() {
        login.login(Credentials.builder()
                        .email(user.getEmail())
                        .password("wrongPass123")
                        .build())
                .statusCode(Expected.SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("email or password are incorrect"),
                        equalTo("email or password incorrect"),
                        containsString("incorrect")
                ));
    }
}
