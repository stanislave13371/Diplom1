package test;

import PageObjects.BasePage;
import PageObjects.LoginPage;
import PageObjects.RegisterPage;
import io.qameta.allure.Description;
import org.junit.Test;

public class RegistrationTest extends BaseUiTest {

    @Test
    @Description("Успешная регистрация")
    public void successRegistration() {
        BasePage base = new BasePage(driver);
        base.clickOnLoginButton();

        LoginPage login = new LoginPage(driver);
        login.visibleElementLoginTitle();
        login.clickToBeRegisterLink();

        RegisterPage reg = new RegisterPage(driver);
        reg.nameInputData()
                .emailInputData()
                .passwordInputData()
                .clickRegisterButton();
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
