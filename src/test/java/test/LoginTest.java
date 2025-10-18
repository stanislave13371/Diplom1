package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.containsString;

import page.object.BasePage;
import page.object.LoginPage;
import page.object.RegisterPage;
import page.object.RecoverPasswordPage;
import utils.RandomUtils;
import utils.UserApiClient;

public class LoginTest extends BaseUiTest {

    private final UserApiClient api = new UserApiClient();

    private String email;
    private String password;
    private String name;
    private String accessToken;

    @Before
    public void createUserViaApi() {
        name = RandomUtils.randomName();
        email = RandomUtils.randomEmail();
        password = RandomUtils.randomPassword();
        accessToken = api.createUser(name, email, password);
    }

    @After
    public void deleteUserViaApi() {
        if (accessToken != null) {
            api.deleteUser(accessToken);
        }
    }

    private void completeLoginOnLoginPage() {
        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Кликаем «Войти в аккаунт» на главной, вводим валидные данные и убеждаемся, что ушли со страницы /login")
    public void loginFromMainEnterButton() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

        assertThat("После логина не должны оставаться на странице /login",
                driver.getCurrentUrl(), not(containsString("/login")));
    }

    @Test
    @DisplayName("Логин через «Личный кабинет»")
    @Description("Открываем «Личный кабинет» со главной, авторизуемся валидными данными; проверяем, что ушли с /login")
    public void loginFromPersonalAccount() {
        BasePage base = new BasePage(driver);
        base.clickOnPersonalAccount();

        completeLoginOnLoginPage();

        assertThat("После успешного входа не должны оставаться на странице логина",
                driver.getCurrentUrl(), not(containsString("/login")));
    }

    @Test
    @DisplayName("Логин через ссылку на странице регистрации")
    @Description("Переходим на логин через ссылку «Войти» на странице регистрации; авторизуемся; проверяем уход с /login")
    public void loginFromRegisterPageLink() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.clickToBeRegisterLink();

        RegisterPage reg = new RegisterPage(driver);
        reg.clickRegisterPageLoginButton();

        completeLoginOnLoginPage();

        assertThat("После успешного входа не должны оставаться на странице логина",
                driver.getCurrentUrl(), not(containsString("/login")));
    }

    @Test
    @DisplayName("Логин через ссылку на странице восстановления пароля")
    @Description("Переходим на логин с экрана восстановления пароля; авторизуемся; проверяем уход с /login")
    public void loginFromRecoverPasswordLink() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.clickToRecoverPasswordButton();

        RecoverPasswordPage recover = new RecoverPasswordPage(driver);
        recover.clickToRecoverPageLoginButton();

        completeLoginOnLoginPage();

        assertThat("После успешного входа не должны оставаться на странице логина",
                driver.getCurrentUrl(), not(containsString("/login")));
    }
}
