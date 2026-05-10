import io.qameta.allure.junit4.DisplayName;
import org.example.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import static java.net.HttpURLConnection.*;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {

    private final List<String> colors;
    private int orderTrack;

    public CreateOrderTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Тест с цветом: {0}")
    public static Object[][] data() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }
    @Test
    @DisplayName("Создание заказа с различными цветами")
    public void createOrderWithDifferentColors() {
        Order order = new Order(
                "Винни",
                "Пух",
                "Лесная, 4",
                "4",
                "+7123456789",
                "08.05.2026",
                3,
                colors,
                "до обеда"
        );
        orderTrack = steps.StepsOrder.createOrder(order)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .extract().path("track"); // Сохраняем трек
    }

    @After
    public void tearDownOrder() {
        if (orderTrack != 0) {
            steps.StepsOrder.cancelOrder(orderTrack)
                    .then()
                    .log().all();
        }

    }
}
