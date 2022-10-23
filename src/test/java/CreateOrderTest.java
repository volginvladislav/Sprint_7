import client.OrderClient;
import dto.OrderData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
        private static String firstName;
        private static String lastName;
        private static String address;
        private static String metroStation;
        private static String phone;
        private static int rentTime;
        private static String deliveryDate;
        private static String comment;
        private static String color;

        private OrderData orderData;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           Integer rentTime, String deliveryDate, String comment, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
        @Before
        public void setUp() {
            orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }

        @Parameterized.Parameters
        public static Object[][] getOrderData(){
            return new Object[][]{
                    {"Иван", "Иванов", "Москва, ул.Ленина 59, кв.44", "Охотный Ряд",
                            "+7 911 333 33 33", 2, "2022-10-25", "Хочу самый быстрый самокат", ""},
                    {"Григорий", "Григорьев", "Москва, ул.Ленина 58, кв.41", "Охотный Ряд",
                            "+7 911 228 28 28", 3, "2022-11-26", "Хочу самый быстрый самокат", "BLACK"},
                    {"Петр", "Петров", "Москва, ул.Ленина 53, кв.40", "Охотный Ряд",
                            "+7 911 999 09 09", 4, "2022-11-27", "Хочу самый быстрый самокат", "GREY"},
            };
        }

        @Test
        @DisplayName("Тест на создание заказа по параметрам")
        public void checkCreateOrderParameters(){
            OrderClient orderClient = new OrderClient();
            orderClient.createOrderWithParameters(getOrderData())
                    .assertThat()
                    .statusCode(SC_CREATED)
                    .body("track", notNullValue());
        }
}
