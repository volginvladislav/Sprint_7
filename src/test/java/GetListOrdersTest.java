import client.OrderClient;
import dto.OrderResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class GetListOrdersTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Тест на получение списка заказа")
    public void checkGetListOrder(){
        List<OrderResponse> listOfOrders = orderClient.getOrderList()
                .assertThat()
                .statusCode(SC_OK)
                .extract().path("orders");
        assertTrue(listOfOrders.size() > 0);
    }
}
