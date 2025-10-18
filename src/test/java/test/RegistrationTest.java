package test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.object.BasePage;
import page.object.LoginPage;
import page.object.RegisterPage;
import io.qameta.allure.Description;
import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

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

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));
        assertThat("Должен быть редирект на страницу входа",
                driver.getCurrentUrl(), containsString("/login"));
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

        assertThat("При невалидном пароле не должно редиректить со страницы регистрации",
                driver.getCurrentUrl(), containsString("/register"));

        assertThat(reg.getPasswordErrorMessageText(), containsString("Некорректный пароль"));
    }
}