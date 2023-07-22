package scooter;

public class OrderGenerator {

    public static OrderMethods getDefault(String[] color) {
        return new OrderMethods("Alina",
                "Arslanova",
                "Ufa",
                5,
                "+7 996 580 81 40",
                2,
                "2023-07-25",
                "good",
                color);
    }
}