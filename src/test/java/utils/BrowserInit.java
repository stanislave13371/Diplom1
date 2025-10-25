package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BrowserInit {

    public static WebDriver startBrowser(String browserName) {
        String browser = browserName == null ? "chrome" : browserName.toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--lang=ru");
        options.addArguments("--window-size=1920,1080");
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }

        WebDriver driver;

        if ("chrome".equals(browser)) {
            String cdVersion = System.getProperty("chromedriverVersion", "");
            if (!cdVersion.isEmpty()) {
                WebDriverManager.chromedriver().driverVersion(cdVersion).setup();
            } else {
                WebDriverManager.chromedriver().setup();
            }
            driver = new ChromeDriver(options);

        } else if ("yandex".equals(browser)) {
            String yandexBinary = System.getProperty(
                    "yandex.binary",
                    "C:\\Users\\ls232\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe"
            );
            if (!new java.io.File(yandexBinary).exists()) {
                throw new IllegalArgumentException("Не найден Яндекс.Браузер по пути: " + yandexBinary);
            }
            options.setBinary(yandexBinary);

            String cdVersion = System.getProperty("chromedriverVersion", "138.0.7204.0");
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver()
                    .clearDriverCache().clearResolutionCache()
                    .driverVersion(cdVersion)
                    .setup();

            driver = new org.openqa.selenium.chrome.ChromeDriver(options);

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }

        driver.manage().window().maximize();
        return driver;
    }
}