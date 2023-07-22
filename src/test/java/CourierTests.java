import scooter.Courier;
import scooter.CourierCredentials;
import scooter.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourierTests {

    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier();
    }

    @After
    public void cleanUp() {
        courier.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Проверка заполнения обязательных полей для создания курьера")
    @Description("Запрос возвращает статус код - 400, недостаточно данных для создания")
    public void checkNotAllRequiredFields() {
        ValidatableResponse responseCreate = courier.createCourier(CourierGenerator.getDefaultWithoutPassword());
        int statusCode = responseCreate.extract().statusCode();
        String message = responseCreate.extract().path("message");
        assertEquals("Некорректный статус код", 400, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Запрос возвращает статус код - 201")
    public void createNewCourierAndCheckResponse() {
        courier.createCourier(CourierGenerator.getDefault());
        ValidatableResponse responseLogin = courier.loginCourier(CourierCredentials.from(CourierGenerator.getDefault()));
        courierId = responseLogin.extract().path("id");
        assertNotNull("Id is null", courierId);
    }

    @Test
    @DisplayName("Невозможность создания двух одинаковых курьеров")
    @Description("Запрос возвращает статус код - 409 и с сообщением что логин уже используется")
    public void checkNotCreateTwoIdenticalCouriers() {
        courier.createCourier(CourierGenerator.getDefault());
        courier.loginCourier(CourierCredentials.from(CourierGenerator.getDefault()));
        ValidatableResponse responseNotCreate = courier.createCourier(CourierGenerator.getDefault());
        int doubleStatusCode = responseNotCreate.extract().statusCode();
        assertEquals("Некорректный статус код", 409, doubleStatusCode);
        String message = responseNotCreate.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }
}
