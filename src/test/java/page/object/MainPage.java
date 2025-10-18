package page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    private final By bunsTab     = By.xpath("//span[normalize-space()='Булки']/ancestor::*[contains(@class,'tab')][1]");
    private final By saucesTab   = By.xpath("//span[normalize-space()='Соусы']/ancestor::*[contains(@class,'tab')][1]");
    private final By fillingsTab = By.xpath("//span[normalize-space()='Начинки']/ancestor::*[contains(@class,'tab')][1]");

    private final By bunsActive     = By.xpath("//span[normalize-space()='Булки']/ancestor::*[contains(@class,'tab_tab_type_current')]");
    private final By saucesActive   = By.xpath("//span[normalize-space()='Соусы']/ancestor::*[contains(@class,'tab_tab_type_current')]");
    private final By fillingsActive = By.xpath("//span[normalize-space()='Начинки']/ancestor::*[contains(@class,'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        this.js = (JavascriptExecutor) driver;
    }

    private void scrollIntoViewCenter(WebElement el) {
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", el);
        } catch (JavascriptException ignore) {
            js.executeScript("arguments[0].scrollIntoView(true);", el);
        }
    }

    private void jsClick(WebElement el) {
        js.executeScript("arguments[0].click();", el);
    }

    private ExpectedCondition<Boolean> hasActiveClass(WebElement el) {
        return drv -> {
            try {
                String cls = el.getAttribute("class");
                return cls != null && cls.contains("tab_tab_type_current");
            } catch (StaleElementReferenceException e) {
                return false;
            }
        };
    }

    private MainPage clickTabAndWait(By tabToClick, By expectedActive) {
        for (int i = 0; i < 2; i++) {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(tabToClick));
            scrollIntoViewCenter(el);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(el)).click();
            } catch (WebDriverException e) {
                jsClick(el);
            }

            if (wait.until(hasActiveClass(el))) {
                return this;
            }

            scrollIntoViewCenter(el);
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(expectedActive));
        return this;
    }

    @Step("Открыть вкладку «Булки»")
    public MainPage openBuns() {
        return clickTabAndWait(bunsTab, bunsActive);
    }

    @Step("Открыть вкладку «Соусы»")
    public MainPage openSauces() {
        return clickTabAndWait(saucesTab, saucesActive);
    }

    @Step("Открыть вкладку «Начинки»")
    public MainPage openFillings() {
        return clickTabAndWait(fillingsTab, fillingsActive);
    }

    public String activeTabText() {
        if (isPresent(bunsActive))     return "Булки";
        if (isPresent(saucesActive))   return "Соусы";
        if (isPresent(fillingsActive)) return "Начинки";
        By anyActive = By.xpath("//*[contains(@class,'tab_tab_type_current')]//span");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(anyActive)).getText().trim();
    }

    private boolean isPresent(By loc) {
        return !driver.findElements(loc).isEmpty();
    }
}
