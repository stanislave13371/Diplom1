package test;

import PageObjects.BasePage;
import io.qameta.allure.Description;
import org.junit.Test;

public class ConstructorTest extends BaseUiTest {

    @Test
    @Description("Переход к разделу «Булки»")
    public void openRollsSection() {
        BasePage base = new BasePage(driver);
        base.clickToSouseButton();
        base.clickToRollsButton();
    }

    @Test
    @Description("Переход к разделу «Соусы»")
    public void openSouseSection() {
        BasePage base = new BasePage(driver);
        base.clickToSouseButton();
    }

    @Test
    @Description("Переход к разделу «Начинки»")
    public void openToppingSection() {
        BasePage base = new BasePage(driver);
        base.clickToToppingButton();
    }
}
