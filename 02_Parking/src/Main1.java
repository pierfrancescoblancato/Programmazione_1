public class Main1 {
    public static void main(String[] args) {

        StopOverManagement som = new StopOverManagement();

        Parking p = new Parking(10);

        Car car1 = new Car("AB123CD");
        Van van1 = new  Van("CD123AB");
        Car car2 = new Car("ZZ999XX");

        System.out.println("=== CAR ENTRY ===");
        p.enterVehicle(car1);
        p.enterVehicle(van1);
        p.enterVehicle(car2);

        System.out.println("\n=== CAR EXIT ===");
        p.exitVehicle(car1);
        p.exitVehicle(van1);

        System.out.println("\nParking Status -> Occupied: " + p.currentCapacity() + " | Free: " + p.getFreeCurretPlace());

        System.out.println("\n=== TICKET HISTORY ===");
        som.loadFromFile();


        System.out.println("\n=== TOTAL REVENUE ===");
        System.out.println("Total revenue so far: " + p.getAmount());
    }
}