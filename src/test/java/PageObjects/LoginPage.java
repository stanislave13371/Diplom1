package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.UserBuilder;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final UserBuilder builder = UserBuilder.registerUserData();

    private final By loginTitle = By.xpath("//h2[text()='Вход']");
    private final By loginInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[@href='/register']");
    private final By stellarLogo = By.xpath("//div[contains(@class,'AppHeader_header__logo')]/a");
    private final By recoverPasswordButton = By.xpath("//a[@href='/forgot-password']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заголовок «Вход» видим")
    public LoginPage visibleElementLoginTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
        return this;
    }

    @Step("Ввести email: {email}")
    public LoginPage loginInputField(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));
        el.clear();
        el.sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage passwordInputField(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        el.clear();
        el.sendKeys(password);
        return this;
    }

    public LoginPage loginInputField()     { return loginInputField(builder.getEmail()); }
    public LoginPage passwordInputField()  { return passwordInputField(builder.getPassword()); }

    @Step("Нажать «Войти»")
    public LoginPage clickToLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    @Step("Ссылка «Зарегистрироваться»")
    public LoginPage clickToBeRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        return this;
    }

    @Step("Клик по логотипу")
    public LoginPage clickToStellarBurgerLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(stellarLogo)).click();
        return this;
    }

    @Step("Перейти на восстановление пароля")
    public LoginPage clickToRecoverPasswordButton() {
        wait.until(ExpectedConditions.elementToBeClickable(recoverPasswordButton)).click();
        return this;
    }
}