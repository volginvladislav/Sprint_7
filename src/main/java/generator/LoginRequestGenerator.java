package generator;

import dto.CourierRequest;
import dto.LoginRequest;
import io.qameta.allure.Step;

public class LoginRequestGenerator {
    @Step("Ввод данных для проверки логина в системе")
    public static LoginRequest from(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(courierRequest.getLogin());
        loginRequest.setPassword(courierRequest.getPassword());
        return loginRequest;
    }
    @Step ("Данные для проверки возможности выполнить вход без поля login")
    public static LoginRequest getCourierRequestLoginWithoutLoginFrom(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        return loginRequest;
    }
    @Step("данные для проверки возможности выполнить вход без поля password")
    public static LoginRequest getCourierRequestLoginWithoutPasswordFrom(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(courierRequest.getLogin());
        loginRequest.setPassword("");
        return loginRequest;
    }
    @Step("данные для теста с невалидными данными")
    public static LoginRequest getCourierRequestLoginWithInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("dfhjsfqrgvxc");
        loginRequest.setPassword("735432");
        return loginRequest;
    }
}
