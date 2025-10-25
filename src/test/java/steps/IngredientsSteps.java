package steps;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsSteps extends BaseApi {

    @Step("Получить все ингредиенты")
    public JsonPath getAll() {
        return given().spec(spec())
                .when().get(Endpoints.INGREDIENTS)
                .then().statusCode(200)
                .extract().jsonPath();
    }

    @Step("Взять любые два валидных id ингредиентов")
    public List<String> getAnyTwoIngredientIds() {
        List<String> ids = getAll().getList("data._id");
        if (ids == null || ids.size() < 2) {
            throw new IllegalStateException("Недостаточно ингредиентов для теста");
        }
        return ids.subList(0, 2);
    }
}
