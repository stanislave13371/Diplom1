package test;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import page.object.BasePage;
import page.object.LoginPage;
import page.object.RegisterPage;
import utils.RandomUtils;
import utils.UserApiClient;

public class RegistrationTest extends BaseUiTest {

    private final UserApiClient api = new UserApiClient();

    private String email;
    private String password;
    private String name;

    @After
    public void deleteUserIfCreated() {
        if (email != null && password != null) {
            try {
                String token = api.login(email, password);
                if (token != null && !token.isBlank()) {
                    api.deleteUser(token);
                }
            } catch (Exception ignored) {
            } finally {
                email = null;
                password = null;
                name = null;
            }
        }
    }

    @Test
    @Description("Успешная регистрация")
    public void successRegistration() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle();
        login.clickToBeRegisterLink();

        name = RandomUtils.randomName();
        email = RandomUtils.randomEmail();
        password = RandomUtils.randomPassword();

        RegisterPage reg = new RegisterPage(driver);
        reg.nameInputData(name)
                .emailInputData(email)
                .passwordInputData(password)
                .clickRegisterButton();

        new LoginPage(driver).visibleElementLoginTitle();
    }

    @Test
    @Description("Ошибка для некорректного пароля (<6 символов)")
    public void incorrectPasswordRegistration() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle();
        login.clickToBeRegisterLink();

        RegisterPage reg = new RegisterPage(driver);
        reg.nameInputData()
                .emailInputData()
                .incorrectPasswordInputData()
                .clickRegisterButton()
                .passwordErrorMessage();
    }
}
