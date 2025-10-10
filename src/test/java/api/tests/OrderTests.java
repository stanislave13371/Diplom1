package api.tests;

import api.Expected;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class OrderTests extends BaseApiTest {
    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();

    private List<String> anyTwoIngredients;

    @Before
    public void initData() {
        anyTwoIngredients = orderSteps.getAnyTwoIngredientIds();
    }

    @Test
    @DisplayName("Создание заказа без авторизации — разрешено")
    public void shouldCreateOrderWithoutAuth() {
        orderSteps.createOrder(new OrderRequest(anyTwoIngredients))
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void shouldCreateOrderAuthorized() {
        ValidatableResponse reg = userSteps.register(user).statusCode(Expected.SC_CREATED_OR_OK);
        token        = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");

        orderSteps.createOrder(new OrderRequest(anyTwoIngredients), token)
                .statusCode(200)
                .body("success", is(true))
                .body("order.price", greaterThan(0));
    }

    @Test
    @DisplayName("Пустой список ингредиентов — 400")
    public void shouldNotCreateOrderWithoutIngredients() {
        ValidatableResponse reg = userSteps.register(user).statusCode(Expected.SC_CREATED_OR_OK);
        token        = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");

        orderSteps.createOrder(new OrderRequest(List.of()), token)
                .statusCode(Expected.SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", containsString("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Невалидный ингредиент — 500 и HTML")
    public void shouldFailWithInvalidIngredientHash() {
        ValidatableResponse reg = userSteps.register(user).statusCode(Expected.SC_CREATED_OR_OK);
        token        = reg.extract().path("accessToken");
        refreshToken = reg.extract().path("refreshToken");

        orderSteps.createOrder(new OrderRequest(List.of("invalid_hash_123")), token)
                .statusCode(Expected.SC_INTERNAL_ERROR)
                .contentType(startsWith("text/html"))
                .body(containsString("Internal Server Error"));
    }
}
