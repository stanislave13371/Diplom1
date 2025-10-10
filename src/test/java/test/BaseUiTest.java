package test;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.BrowserInit;

import java.nio.charset.StandardCharsets;

public class BaseUiTest {

    protected WebDriver driver;

    @Before
    @Step("Инициализация браузера и открытие приложения")
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");
        String baseUrl = System.getProperty("baseUrl", "https://stellarburgers.nomoreparties.site/");

        driver = BrowserInit.startBrowser(browser);

        Allure.parameter("browser", browser);
        try {
            Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
            Allure.parameter("browserName", caps.getBrowserName());
            String ver;
            try {
                ver = caps.getBrowserVersion();
            } catch (Throwable t) {
                Object v = caps.getCapability("browserVersion");
                ver = v != null ? String.valueOf(v) : null;
            }
            if (ver != null) {
                Allure.parameter("browserVersion", ver);
            }
        } catch (Throwable ignored) { }

        Allure.parameter("baseUrl", baseUrl);

        driver.get(baseUrl);
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Rule
    public TestWatcher allureWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                if (driver != null) {
                    byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.getLifecycle().addAttachment("Screenshot on failure", "image/png", "png", png);
                    try {
                        String logs = driver.manage().logs().get("browser").getAll().toString();
                        Allure.getLifecycle().addAttachment("Browser console", "text/plain", "log",
                                logs.getBytes(StandardCharsets.UTF_8));
                    } catch (Exception ignored) { }
                }
            } catch (Exception ignored) { }
        }
    };
}
