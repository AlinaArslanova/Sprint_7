import scooter.Order;
import scooter.OrderMethods;
import scooter.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderTests {

    private OrderMethods orderMethods;
    private Order order;
    private final String[] color;
    private int orderTrack;

    public OrderTests(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа {index}")
    public static Object[][] getColors() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setUp() {
        orderMethods = OrderGenerator.getDefault(color);
        order = new Order();
    }

    @Test
    @DisplayName("Успешное создание заказа")
    @Description("Запрос возвращает статус код - 201")
    public void createOrder() {
        ValidatableResponse response = order.create(orderMethods);
        int statusCode = response.extract().statusCode();
        orderTrack = response.extract().path("track");
        assertEquals("Некорректный статус код", 201, statusCode);
        assertNotNull("Значение track пустое", orderTrack);
    }
}