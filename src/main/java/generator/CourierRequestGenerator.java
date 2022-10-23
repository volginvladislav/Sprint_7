package generator;

import dto.CourierRequest;
import dto.LoginRequest;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class CourierRequestGenerator {
    @Step("Ввод данных для создания курьера")
    public static CourierRequest getRandomCourierRequest() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("1234");
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        return courierRequest;
    }

    @Step("Данные для проверки возможности регистрации без поля login")
    public static CourierRequest getCreateLoginCourierWithoutLoginField() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setPassword("4747");
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        return courierRequest;
    }

    @Step("данные для проверки возможности регистрации без поля password")
    public static CourierRequest getCreateLoginCourierWithoutPasswordField() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        return courierRequest;
    }

}

