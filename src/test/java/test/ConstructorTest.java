package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import page.object.MainPage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConstructorTest extends BaseUiTest {

    private MainPage main;

    @Before
    public void init() {
        main = new MainPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Кликаем по вкладке «Булки» и убеждаемся, что она стала активной")
    public void openBunsSection() {
        main.openSauces().openBuns();
        assertThat(main.activeTabText(), is("Булки"));
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Кликаем по вкладке «Соусы» и убеждаемся, что она стала активной")
    public void openSaucesSection() {
        main.openSauces();
        assertThat(main.activeTabText(), is("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Кликаем по вкладке «Начинки» и убеждаемся, что она стала активной")
    public void openFillingsSection() {
        main.openFillings();
        assertThat(main.activeTabText(), is("Начинки"));
    }
}
