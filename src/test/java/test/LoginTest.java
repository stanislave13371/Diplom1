package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import utils.RandomUtils;
import utils.UserApiClient;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest extends BaseUiTest {

    private final UserApiClient api = new UserApiClient();

    private String email;
    private String password;
    private String name;
    private String accessToken;

    @Before
    public void createUserByApi() {
        email = RandomUtils.randomEmail();
        password = RandomUtils.randomPassword();
        name = RandomUtils.randomName();

        accessToken = api.createUser(email, password, name);
    }

    @After
    public void deleteUserByApi() {
        api.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Логин с главной страницы")
    @Description("Создаём пользователя по API, логинимся через UI, проверяем, что кнопка «Оформить заказ» видна")
    public void loginFromMainPage() {
        driver.get("https://stellarburgers.nomoreparties.site/");

        driver.findElement(By.xpath("//button[text()='Войти в аккаунт']")).click();

        driver.findElement(By.name("name")).sendKeys(email);
        driver.findElement(By.name("Пароль")).sendKeys(password);

        driver.findElement(By.xpath("//button[text()='Войти']")).click();

        boolean orderButtonVisible =
                !driver.findElements(By.xpath("//button[text()='Оформить заказ']")).isEmpty();

        assertThat(orderButtonVisible, is(true));
    }
}
