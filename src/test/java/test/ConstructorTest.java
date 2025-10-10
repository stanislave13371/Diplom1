package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConstructorTest extends BaseUiTest {

    private String activeTabText(WebDriver driver) {
        return driver.findElement(By.cssSelector("[class*='tab_tab_type_current']")).getText().trim();
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Кликаем по вкладке «Булки» и убеждаемся, что она стала активной")
    public void openBunsSection() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        driver.findElement(By.xpath("//span[text()='Булки']")).click();
        assertThat(activeTabText(driver), is("Булки"));
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Кликаем по вкладке «Соусы» и убеждаемся, что она стала активной")
    public void openSaucesSection() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        driver.findElement(By.xpath("//span[text()='Соусы']")).click();
        assertThat(activeTabText(driver), is("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Кликаем по вкладке «Начинки» и убеждаемся, что она стала активной")
    public void openFillingsSection() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        driver.findElement(By.xpath("//span[text()='Начинки']")).click();
        assertThat(activeTabText(driver), is("Начинки"));
    }
}