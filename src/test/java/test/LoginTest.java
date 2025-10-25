package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.object.BasePage;
import page.object.LoginPage;
import page.object.RecoverPasswordPage;
import page.object.RegisterPage;
import utils.RandomUtils;
import utils.UserApiClient;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class LoginTest extends BaseUiTest {

    private final UserApiClient api = new UserApiClient();
    private String email;
    private String password;
    private String name;
    private String accessToken;

    private final String baseUrl = System.getProperty("baseUrl",
            "https://stellarburgers.education-services.ru/");

    @Before
    public void createUserViaApi() {
        email = RandomUtils.randomEmail();
        password = RandomUtils.randomPassword();
        name = RandomUtils.randomName();

        accessToken = api.createUser(email, password, name);

        Assume.assumeTrue("API недоступно: пользователь не создан", accessToken != null);
    }

    @After
    public void deleteUserViaApi() {
        if (accessToken != null) {
            api.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Главная → Войти в аккаунт → логин по email+password → редирект с /login")
    public void loginFromMainEnterButton() {
        new BasePage(driver).clickOnLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Хедер → «Личный кабинет» → логин → редирект с /login")
    public void loginFromPersonalAccount() {
        new BasePage(driver).clickOnPersonalAccount();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Логин → ссылка «Зарегистрироваться» → на форме регистрации нажать «Войти» → логин → редирект с /login")
    public void loginFromRegisterPageLink() {
        new BasePage(driver).clickOnLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .clickToBeRegisterLink();

        new RegisterPage(driver)
                .clickRegisterPageLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Логин → «Восстановить пароль» → «Войти» → логин → редирект с /login")
    public void loginFromRecoverPasswordLink() {
        new BasePage(driver).clickOnLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .clickToRecoverPasswordButton();

        new RecoverPasswordPage(driver)
                .clickToRecoverPageLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(email)
                .passwordInputField(password)
                .clickToLoginButton();

        assertLoggedIn();
    }


    private void assertLoggedIn() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        assertThat("После входа не должны оставаться на /login",
                driver.getCurrentUrl(), not(containsString("/login")));
        assertThat(driver.getCurrentUrl(), containsString(baseUrl.replaceAll("/+$", "")));
    }
}
