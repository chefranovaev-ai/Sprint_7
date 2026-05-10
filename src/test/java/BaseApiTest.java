import io.restassured.RestAssured;
import org.junit.Before;

import static data.CourierData.BASE_URL;

public class BaseApiTest {
    protected Integer courierId;

    @Before
    public void startUp() {
        RestAssured.baseURI = BASE_URL;
    }

}
