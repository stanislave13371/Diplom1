package page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By personalAccount = By.xpath("//a[@href='/account']");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");

    @Step("Клик по кнопке «Войти в аккаунт» на главной")
    public BasePage clickOnLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    @Step("Переход в «Личный кабинет» через ссылку в шапке")
    public BasePage clickOnPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccount)).click();
        return this;
    }
}