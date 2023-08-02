import scooter.Courier;
import scooter.CourierCredentials;
import scooter.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTests {

    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier();
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            courier.deleteCourier(courierId);
            System.out.println("Удален - " + courierId);
        } else {
            System.out.println("Не удалось удалить курьера, так как ID не найден");
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Запрос возвращает id и статус код 200")
    public void courierCanLogin() {
        ValidatableResponse loginResponse = courier
                .loginCourier(CourierCredentials.from(CourierGenerator.getRegisteredCourier()));
        courierId = loginResponse.extract().path("id");
        assertNotNull("Id is null", courierId);
    }

    @Test
    @DisplayName("Заполнены не все обязательные поля при авторизации")
    @Description("Запрос возвращает ошибку 400")
    public void loginCourierEmptyPassword() {
        ValidatableResponse loginResponse = courier
                .loginCourier(CourierCredentials.from(CourierGenerator.getDefaultWithoutPassword()));
        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        assertEquals("Некорректный статус код", 400, loginStatusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    @Description("Запрос возвращает ошибку 404")
    public void wrongLoginCourier() {
        ValidatableResponse loginResponse = courier
                .loginCourier(CourierCredentials.from(CourierGenerator.getWrong()));
        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        assertEquals("Некорректный статус код", 404, loginStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }
}