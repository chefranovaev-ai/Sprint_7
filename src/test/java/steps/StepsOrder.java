package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.Order;

import static data.OrderData.ORDERS_PATH;
import static data.OrderData.ORDER_DELETE;
import static io.restassured.RestAssured.given;

public class StepsOrder {

    @Step("Создание заказа")
    public  static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .log().all()
                .when()
                .get(ORDERS_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Отмена заказа ро номеру трека: {track}")
    public static Response cancelOrder(int track) {
        return given()
                .log().all()
                .queryParam("track", track)
                .when()
                .put(ORDER_DELETE);
    }

}
