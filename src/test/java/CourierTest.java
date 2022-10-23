import client.CourierClient;
import dto.CourierRequest;
import dto.LoginRequest;
import generator.CourierRequestGenerator;
import generator.LoginRequestGenerator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.CourierRequestGenerator.getRandomCourierRequest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class CourierTest {
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
            courierClient .delete(id).assertThat().statusCode(SC_OK).body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Регистрация логина курьера в системе с валидными значениями")
    public void checkCreateLoginCourierValidCredentials() {
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
    @DisplayName("Регистрация логина с уже используемыми данными")
    public void checkCannotCreateExistingCourier() {
        //создается логин
        CourierRequest courierRequest = getRandomCourierRequest();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        //выполнить вход для получения id
        LoginRequest loginRequest = LoginRequestGenerator.from(courierRequest);
        id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
        //проверка создать логин с теми же данными
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Тест на регистрацию логина без поля login")
    public void checkCreateLoginCourierWithoutLoginField(){
        CourierRequest courierRequest = CourierRequestGenerator.getCreateLoginCourierWithoutLoginField();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест на регистрацию логина без поля password")
    public void checkCreateLoginCourierWithoutPasswordField(){
        CourierRequest courierRequest = CourierRequestGenerator.getCreateLoginCourierWithoutPasswordField();
        courierClient.create(courierRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
