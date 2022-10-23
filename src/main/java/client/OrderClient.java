package client;

import dto.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
        @Step("Создание заказа по параметрам")
        public ValidatableResponse createOrderWithParameters(Object[][] orderData) {
            return given()
                    .spec(getDefaultRequestSpec())
                    .body(orderData)
                    .post(EndPoints.ORDERS)
                    .then();
        }

        @Step("Получение списка заказов")
        public ValidatableResponse getOrderList(){
            return given()
                    .spec(getDefaultRequestSpec())
                    .get(EndPoints.ORDERS)
                    .then();
        }
}

