import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.Courier;
import org.example.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.StepsCourier.*;

public class LoginCourierTest extends BaseApiTest {

    private Courier courier;

    @Before
            public void setUp() {
        courier = CourierData.getRandomCourier();
        createCourier(courier);
    }
    @After
    public void tearDown() {

        if (courierId != null) {
            deleteCourier(courierId);
            courierId = null;
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Курьер может авторизоваться и для этого нужно передать все обязательные поля, запрос возвращает id")
    public void authorizationCourier() {

        courierId = loginCourier(CourierCredentials.from(courier))
                .then()
                .log().all()
                .statusCode(HTTP_OK)
               .body("id", notNullValue())
                .extract().path("id");

    }

    @Test
    @DisplayName("Авторизация с неправильным логином")
    @Description("Система вернет ошибку, если неправильно указать логин")
    public void errorLoginCourierWrongLogin() {

        courierId = loginCourier(CourierCredentials.from(courier)).then().extract().path("id");

        CourierCredentials creds = new CourierCredentials("wrong_" + courier.getLogin(), courier.getPassword());
        loginCourier(creds)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Авторизация с неправильным паролем")
    @Description("Система вернет ошибку, если неправильно указать пароль")
    public void errorLoginCourierWrongPassword() {
        CourierCredentials wrongPass = new CourierCredentials(courier.getLogin(), "wrong_" + courier.getPassword());

        loginCourier(wrongPass)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @Test
    @DisplayName("Ошибка авторизации без логина")
    @Description("Если не передать логин, запрос возвращает 400 Bad Request")
    public void loginWithoutLoginField() {

        CourierCredentials creds = new CourierCredentials(null, courier.getPassword());

        loginCourier(creds)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка авторизации без пароля")
    @Description("Если не передать пароль, запрос возвращает 400 Bad Request")
    public void loginWithoutPasswordField() {

        CourierCredentials creds = new CourierCredentials(courier.getLogin(), null);

        loginCourier(creds)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

}
