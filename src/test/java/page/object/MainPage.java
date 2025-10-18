package page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By bunsTab = By.xpath("//span[text()='Булки']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']");
    private final By activeTab = By.cssSelector("[class*='tab_tab_type_current']");

    private final By loginAccountBtn = By.xpath("//button[.='Войти в аккаунт']");
    private final By personalAccount = By.xpath("//p[.='Личный Кабинет']");

    private final By orderButton = By.xpath("//button[.='Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Открыть вкладку «Булки»")
    public MainPage openBuns() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
        wait.until(ExpectedConditions.textToBe(activeTab, "Булки"));
        return this;
    }

    @Step("Открыть вкладку «Соусы»")
    public MainPage openSauces() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
        wait.until(ExpectedConditions.textToBe(activeTab, "Соусы"));
        return this;
    }

    @Step("Открыть вкладку «Начинки»")
    public MainPage openFillings() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
        wait.until(ExpectedConditions.textToBe(activeTab, "Начинки"));
        return this;
    }

    public String activeTabText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab)).getText().trim();
    }

    @Step("Нажать «Войти в аккаунт» на главной")
    public void clickLoginInAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(loginAccountBtn)).click();
    }

    @Step("Перейти через «Личный Кабинет» в шапке")
    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccount)).click();
    }

    @Step("Проверить, что пользователь авторизован (видна кнопка «Оформить заказ»)")
    public boolean isAuthorized() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton)).isDisplayed();
    }
}
