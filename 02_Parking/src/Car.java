class Car{
    private static String plate;

    public Car() {
    }
    public Car(String plate) {
        this.plate = plate;
    }

    public static String getPlate() {
        return plate;
    }

    public static void setPlate(String plate) {
        Car.plate = plate;
    }



}

