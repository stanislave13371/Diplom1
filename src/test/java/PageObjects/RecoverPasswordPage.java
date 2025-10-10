package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoverPasswordPage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public RecoverPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By recoverPageLoginButton = By.xpath("//a[@href='/login']");

    @Step("Перейти к форме входа со страницы восстановления пароля")
    public RecoverPasswordPage clickToRecoverPageLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(recoverPageLoginButton)).click();
        return this;
    }
}
