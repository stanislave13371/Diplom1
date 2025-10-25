package steps;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderSteps extends BaseApi {

    @Step("Создать заказ (без авторизации)")
    public ValidatableResponse createOrder(OrderRequest order) {
        return given().spec(spec())
                .body(order)
                .when().post(Endpoints.ORDERS)
                .then();
    }

    @Step("Создать заказ (c авторизацией)")
    public ValidatableResponse createOrder(OrderRequest order, String accessToken) {
        return given().spec(spec())
                .header("Authorization", accessToken)
                .body(order)
                .when().post(Endpoints.ORDERS)
                .then();
    }
}
