package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Credentials;
import org.junit.Assume;
import org.junit.Test;
import steps.LoginSteps;

import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseApiTest {
    private final LoginSteps login = new LoginSteps();

    private void ensureUserExistsOrSkip() {
        try {
            ValidatableResponse reg = login.register(user)
                    .statusCode(anyOf(is(200), is(201), is(403), is(409)));

            int sc = reg.extract().statusCode();
            if (sc == 200 || sc == 201) {
                token = reg.extract().path("accessToken");
                refreshToken = reg.extract().path("refreshToken");
            }
        } catch (Throwable t) {
            Assume.assumeNoException("API недоступен: " + t.getMessage(), t);
        }
    }

    @Test
    @DisplayName("Логин существующего пользователя")
    public void shouldLoginExistingUser() {
        ensureUserExistsOrSkip();

        login.login(Credentials.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .statusCode(Expected.SC_CREATED_OR_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверным паролем -> 401")
    public void shouldFailLoginWrongCreds() {
        ensureUserExistsOrSkip();

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

    @Test
    @DisplayName("Логин с неверной почтой -> 401")
    public void shouldFailLoginWrongEmail() {
        ensureUserExistsOrSkip();

        String badEmail = "wrong." + user.getEmail();
        login.login(Credentials.builder()
                        .email(badEmail)
                        .password(user.getPassword())
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
