import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.Courier;
import org.example.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.StepsCourier.*;

public class СreatingCourierTest extends BaseApiTest {
    @After
    public void tearDown() {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера со всеми полями")
    @Description("тест проверяет, что курьер создается при передачи в ручку всех полей и успешный запрос возвращает правильный код и ok: true")
    public void testNewCourierAllFields() {

        Courier courier = CourierData.getRandomCourier();

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

        courierId = loginCourier(CourierCredentials.from(courier))
                .then()
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Создание нового курьера по обязательным полям")
    @Description("тест проверяет, что курьер создается при передачи в ручку только обязательных полей и успешный запрос возвращает правильный код и ok: true")
    public void testNewCourierRequiredFields() {

        Courier courier = CourierData.getRandomCourier();

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

           courierId = loginCourier(CourierCredentials.from(courier))
                   .then()
                   .log().all()
                   .extract().path("id");


    }

    @Test
    @DisplayName("Добавление дублирующего курьера")
    @Description("проверка на то, что нельзя создать двух одинаковых курьеров")
    public void testDoubleCourier() {

        Courier courier = CourierData.getRandomCourier();

        createCourier(courier)
                .then()
                .log().all();
        courierId = loginCourier(CourierCredentials.from(courier))
                .then()
                .extract()
                .path("id");
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT);

    }

    @Test
    @DisplayName("Запрос без обязательного поля LOGIN")
    @Description("В запросе не передаем обязательное поле login")
    public void testCreatingCourierWithoutLogin() {

        Courier courier = CourierData.getRandomCourier();
        courier.setLogin(null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Запрос без обязательного поля PASSWORD")
    @Description("В запросе не передаем обязательное поле password")
    public void testCreatingCourierWithoutPassword() {

        Courier courier = CourierData.getRandomCourier();
        courier.setPassword(null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
