package client;

import dto.CourierRequest;
import dto.EndPoints;
import dto.LoginRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient{

    @Step("Создание логина для курьера")
    public ValidatableResponse create(CourierRequest courierRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(courierRequest)
                .post(EndPoints.COURIER)
                .then();
    }
    @Step("Вход под созданным логином")
    public ValidatableResponse login(LoginRequest loginRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(loginRequest)
                .post(EndPoints.LOGIN)
                .then();
    }
    @Step("Удаление логина курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getDefaultRequestSpec())
                .delete(EndPoints.DELETE+id)
                .then();
    }
}
