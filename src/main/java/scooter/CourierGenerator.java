package scooter;

public class CourierGenerator {
    public static CourierMethods getDefault() {
        return new CourierMethods("sasha007", "ooo007", "sasha");
    }

    public static CourierMethods getDefaultWithoutPassword() {
        return new CourierMethods("sasha007", "", "den07");
    }

    public static CourierMethods getRegisteredCourier() {
        return new CourierMethods("002qwerty", "456098", "bob");
    }

    public static CourierMethods getWrong() {
        return new CourierMethods("1122polo", "00000", "haha");
    }
}
