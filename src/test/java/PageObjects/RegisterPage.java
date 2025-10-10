package PageObjects;

import io.qameta.allure.Step;
import utils.UserBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private final UserBuilder builder = UserBuilder.registerUserData();

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By passwordErrorMessage = By.xpath("//p[contains(@class,'input__error') and text()='Некорректный пароль']");
    private final By registerPageLoginButton = By.xpath("//a[@href='/login']");

    @Step("Ввести Имя: {name}")
    public RegisterPage nameInputData(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).clear();
        driver.findElement(nameInput).sendKeys(name);
        return this;
    }

    @Step("Ввести Email: {email}")
    public RegisterPage emailInputData(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
        return this;
    }

    @Step("Ввести Пароль: ******")
    public RegisterPage passwordInputData(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    @Step("Ввести Имя (builder)")
    public RegisterPage nameInputData() {
        return nameInputData(builder.getName());
    }

    @Step("Ввести Email (builder)")
    public RegisterPage emailInputData() {
        return emailInputData(builder.getEmail());
    }

    @Step("Ввести Пароль (builder)")
    public RegisterPage passwordInputData() {
        return passwordInputData(builder.getPassword());
    }

    @Step("Ввести Некорректный пароль (builder)")
    public RegisterPage incorrectPasswordInputData() {
        return passwordInputData(builder.getIncorrectPassword());
    }

    @Step("Нажать кнопку «Зарегистрироваться»")
    public RegisterPage clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        return this;
    }

    @Step("Ожидать ошибку «Некорректный пароль»")
    public RegisterPage passwordErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorMessage));
        return this;
    }

    @Step("Перейти к форме входа со страницы регистрации")
    public RegisterPage clickRegisterPageLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerPageLoginButton)).click();
        return this;
    }
}
