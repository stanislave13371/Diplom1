package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;
import org.junit.Before;
import org.junit.Test;
import steps.IngredientsSteps;
import steps.LoginSteps;
import steps.OrderSteps;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class OrderTests extends BaseApiTest {
    private final IngredientsSteps ingredients = new IngredientsSteps();
    private final OrderSteps orders = new OrderSteps();
    private final LoginSteps login = new LoginSteps();

    private List<String> anyTwoIngredients;

    @Before
    public void prepare() {
        anyTwoIngredients = ingredients.getAnyTwoIngredientIds();

        ValidatableResponse reg = login.register(user).statusCode(Expected.SC_CREATED_OR_OK);
        token = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");
    }

    @Test
    @DisplayName("Создание заказа без авторизации разрешено")
    public void shouldCreateOrderWithoutAuth() {
        orders.createOrder(new OrderRequest(anyTwoIngredients))
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void shouldCreateOrderAuthorized() {
        orders.createOrder(new OrderRequest(anyTwoIngredients), token)
                .statusCode(200)
                .body("success", is(true))
                .body("order.price", greaterThan(0));
    }

    @Test
    @DisplayName("Пустой список ингредиентов → 400")
    public void shouldNotCreateOrderWithoutIngredients() {
        orders.createOrder(new OrderRequest(List.of()), token)
                .statusCode(Expected.SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", anyOf(
                        equalTo("Ingredient ids must be provided"),
                        containsString("Ingredient")
                ));
    }

    @Test
    @DisplayName("Невалидный ингредиент → 500 и text/html")
    public void shouldFailWithInvalidIngredientHash() {
        orders.createOrder(new OrderRequest(List.of("invalid_hash_123")), token)
                .statusCode(Expected.SC_INTERNAL_ERROR)
                .contentType(startsWith("text/html"))
                .body(containsString("Internal Server Error"));
    }
}
