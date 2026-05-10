package steps;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.CourierCredentials;

import static data.CourierData.COURIER_LOGIN_PATH;
import static data.CourierData.COURIER_PATH;
import static io.restassured.RestAssured.given;

public class StepsCourier {

    @Step("Создание курьера {courier.login}")
    public static Response createCourier(Courier courier) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Авторизация курьера под логином {courier.login}")
    public static Response loginCourier(CourierCredentials credentials) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Удаление курьера с id {id}")
    public static Response deleteCourier(int id) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + "/" + id);
    }

}
