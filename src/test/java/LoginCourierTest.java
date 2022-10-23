import client.CourierClient;
import dto.CourierRequest;
import dto.LoginRequest;
import generator.LoginRequestGenerator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.CourierRequestGenerator.getRandomCourierRequest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierClient courierClient;
    //переменная для получения ID, используется для удаления данных
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    @DisplayName("Удаление логина курьера после каждого теста при получении ID")
    //удаляем данные после каждого теста
    public void tearDown(){
        if(id != null){
            courierClient.delete(id).assertThat().statusCode(SC_OK).body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Авторизация курьера в системе с валидными значениями")
    public void checkLoginCourierValidCredentials() {
        //создается логин
        CourierRequest courierRequest = getRandomCourierRequest();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        //проверка создан ли логин, можно ли залогиниться
        LoginRequest loginRequest = LoginRequestGenerator.from(courierRequest);
        id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Проверка входа в систему без поля login")
    public void checkLoginCourierWithoutLoginField() {
        //создается логин
        CourierRequest courierRequest = getRandomCourierRequest();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        //проверка создан ли логин, получаем id для удаления
        LoginRequest loginRequest = LoginRequestGenerator.from(courierRequest);
        id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
        //проверка можно ли залогиниться используя только login
        LoginRequest loginRequest_2 = LoginRequestGenerator.getCourierRequestLoginWithoutLoginFrom(courierRequest);
                courierClient.login(loginRequest_2)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Проверка входа в систему без поля password")
    public void checkLoginCourierWithoutPasswordField() {
        CourierRequest courierRequest = getRandomCourierRequest();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        //проверка создан ли логин, получаем id для удаления
        LoginRequest loginRequest = LoginRequestGenerator.from(courierRequest);
        id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
        //проверка можно ли залогиниться используя только login
        LoginRequest loginRequest_2 = LoginRequestGenerator.getCourierRequestLoginWithoutPasswordFrom(courierRequest);
        courierClient.login(loginRequest_2)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Проверка входа в систему под несуществующим логином")
    public void checkLoginCourierWithInvalidCredentials() {
        //проверка на логин с несуществующими данными
        LoginRequest loginRequest = LoginRequestGenerator.getCourierRequestLoginWithInvalidCredentials();
        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
