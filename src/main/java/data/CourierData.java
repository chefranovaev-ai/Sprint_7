package data;

import com.github.javafaker.Faker;
import org.example.Courier;

public class CourierData {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String COURIER_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    private static Faker user = new Faker();

    public static Courier getRandomCourier() {
        return new Courier(
                user.name().username() + System.currentTimeMillis(),
                user.regexify("[0-9]{4}"),
                user.name().firstName()
        );

    }

}
