import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;
import static steps.StepsOrder.getOrderList;

public class GetOrderListTest extends BaseApiTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что в тело ответа возвращается список заказов")
    public void getOrderListReturnsOrders() {
        getOrderList()
                .then()
                .statusCode(HTTP_OK)
                .body("orders", instanceOf(java.util.List.class));
    }
}
