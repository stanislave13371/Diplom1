package test;

import PageObjects.BasePage;
import PageObjects.LoginPage;
import PageObjects.RecoverPasswordPage;
import PageObjects.RegisterPage;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import utils.UserBuilder;

import static utils.UserBuilder.registerUserData;

public class LoginTest extends BaseUiTest {

    private UserBuilder user;

    @Before
    public void registerUserBeforeEachTest() {
        user = registerUserData();

        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle()
                .clickToBeRegisterLink();

        RegisterPage reg = new RegisterPage(driver);
        reg.nameInputData(user.getName())
                .emailInputData(user.getEmail())
                .passwordInputData(user.getPassword())
                .clickRegisterButton();

        new LoginPage(driver).visibleElementLoginTitle();
    }

    @Test
    @Description("Вход по кнопке «Войти в аккаунт» на главной после предварительной регистрации")
    public void loginFromMainPageButton() {
        new LoginPage(driver).clickToStellarBurgerLogo();

        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(user.getEmail())
                .passwordInputField(user.getPassword())
                .clickToLoginButton();

        base.visibleLabelCreateOrderButton();
    }

    @Test
    @Description("Вход через кнопку «Личный кабинет» после предварительной регистрации")
    public void loginFromPersonalAccountButton() {
        new LoginPage(driver).clickToStellarBurgerLogo();

        BasePage base = new BasePage(driver);
        base.clickOnPersonalAccount();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(user.getEmail())
                .passwordInputField(user.getPassword())
                .clickToLoginButton();

        base.visibleLabelCreateOrderButton();
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void loginFromRegisterPage() {
        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle()
                .clickToBeRegisterLink();

        RegisterPage reg = new RegisterPage(driver);
        reg.clickRegisterPageLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(user.getEmail())
                .passwordInputField(user.getPassword())
                .clickToLoginButton();

        new BasePage(driver).visibleLabelCreateOrderButton();
    }

    @Test
    @Description("Вход через кнопку в форме восстановления пароля")
    public void loginFromRecoverPasswordPage() {
        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle()
                .clickToRecoverPasswordButton();

        RecoverPasswordPage recover = new RecoverPasswordPage(driver);
        recover.clickToRecoverPageLoginButton();

        new LoginPage(driver)
                .visibleElementLoginTitle()
                .loginInputField(user.getEmail())
                .passwordInputField(user.getPassword())
                .clickToLoginButton();

        new BasePage(driver).visibleLabelCreateOrderButton();
    }
}