package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourierCredentials {
    private String login;
    private String password;

    public static CourierCredentials from (Courier courier) {
        return  new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
}
