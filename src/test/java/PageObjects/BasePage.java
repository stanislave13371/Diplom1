package PageObjects;

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
    private final By createOrderButton = By.xpath("//button[text()='Оформить заказ']");


    private final By rollsButton = By.xpath("(//div[contains(@class,'tab_tab')])[1]");
    private final By souseButton = By.xpath("(//div[contains(@class,'tab_tab')])[2]");
    private final By toppingsButton = By.xpath("(//div[contains(@class,'tab_tab')])[3]");


    private final By activeTab = By.xpath("//div[contains(@class,'tab_tab') and contains(@class,'current')]");

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

    @Step("Переход в «Личный кабинет» (alias)")
    public BasePage clickOnProfileButton() {
        return clickOnPersonalAccount();
    }

    @Step("Ожидание видимости кнопки «Оформить заказ»")
    public BasePage visibleLabelCreateOrderButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
        return this;
    }

    @Step("Открыть раздел «Булки»")
    public BasePage clickToRollsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(rollsButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
        return this;
    }

    @Step("Открыть раздел «Соусы»")
    public BasePage clickToSouseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(souseButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
        return this;
    }

    @Step("Открыть раздел «Начинки»")
    public BasePage clickToToppingButton() {
        wait.until(ExpectedConditions.elementToBeClickable(toppingsButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
        return this;
    }
}