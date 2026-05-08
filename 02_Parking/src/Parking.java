import java.util.ArrayList;

public class Parking {

    private int places;
    private int priceForMillisCar = 1;
    private int priceForMillisVan = 2;

    private ArrayList<Vehicle> currentVehicles = new ArrayList<>();
    private ArrayList<Stopover> history = new ArrayList<>();

    public Parking() {
        this.places = 10;
    }

    public Parking(int places) {
        this.places = places;
    }

    public int getPlace() { return places; }
    public void setPlace(int places) { this.places = places; }

    public int getPriceForMillisCar() { return priceForMillisCar; }
    public void setPriceForMillisCar(int priceForMillisCar) { this.priceForMillisCar = priceForMillisCar; }

    public int getPriceForMillisVan() { return priceForMillisVan; }
    public void setPriceForMillisVan(int priceForMillisVan) { this.priceForMillisVan = priceForMillisVan; }

    public ArrayList<Stopover> getHistory() { return history; }
    public void setHistory(ArrayList<Stopover> history) { this.history = history; }

    public int currentCapacity() {
        return currentVehicles.size();
    }
    public int getFreeCurretPlace() {
        return places - currentVehicles.size();
    }

    public void enterVehicle(Vehicle vehicle) {
        for (Vehicle v : currentVehicles) {
            if (v.equals(vehicle)) {
                System.out.println("Vehicle " + vehicle.getPlate() + " already entered.");
                return;
            }
        }

        if (currentVehicles.size() < places) {
            int actualPrice = (vehicle instanceof Van) ? priceForMillisVan : priceForMillisCar;

            currentVehicles.add(vehicle);

            Stopover ticket = new Stopover(vehicle, currentVehicles.indexOf(vehicle), actualPrice);
            history.add(ticket);

            System.out.println("Vehicle parked: " + vehicle.getPlate() + " (Ticket opened)");
        } else {
            System.out.println("The parking is full");
        }
    }

    public void exitVehicle(Vehicle vehicle) {
        Vehicle toRemove = null;

        for (Vehicle v : currentVehicles) {
            if (v.equals(vehicle)) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            currentVehicles.remove(toRemove);
            System.out.println("Vehicle left: " + vehicle.getPlate());

            for (Stopover ticket : history) {
                if (ticket.getVehicle().equals(vehicle) && ticket.getTimeOut() == 0) {
                    ticket.setTimeOut(System.currentTimeMillis());
                    System.out.println("Ticket closed: " + ticket.toString());
                    break;
                }
            }
        } else {
            System.out.println("Error: Vehicle not found in parking.");
        }
    }

    public double getAmount() {
        double amount = 0;

        for (Stopover ticket : history) {
            amount += ticket.getTotalPrice();
        }
        return amount;
    }
}