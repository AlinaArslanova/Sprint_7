package scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class Order extends Rest {

    private static final String ORDER_PATH = "api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(OrderMethods orderMethods) {
        return given()
                .spec(getBaseSpec())
                .body(orderMethods)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse allOrders() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }
}