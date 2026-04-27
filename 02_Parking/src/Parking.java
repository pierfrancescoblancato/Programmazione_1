import java.util.ArrayList;
import java.util.Date;

public class Parking {

    Car[] place;
    ArrayList<Stopover> history = new ArrayList<>();
    int priceForMillis = 2;

    public Parking() {
    }

    public Parking(int capacity) {
        this.place = new Car[capacity];
    }

    public Car[] getPlace() {
        return place;
    }

    public void setPlace(Car[] place) {
        this.place = place;
    }

    public ArrayList<Stopover> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Stopover> history) {
        this.history = history;
    }

    public int getPriceForMillis() {
        return priceForMillis;
    }

    public void setPriceForMillis(int priceForMillis) {
        this.priceForMillis = priceForMillis;
    }

    public void enterCar(Car car) {
        for(int i = 0; i < place.length; i++){
            if(place[i] != null && place[i].equals(car)){
                System.out.println("Car " + car.getPlate() + " already entered.");                return;
            }
        }
        for(int i = 0; i < place.length; i++ ){
            if(place[i] == null){
                place[i] = car;

                Stopover ticket = new Stopover(car, i, priceForMillis);
                history.add(ticket);

                System.out.println("Car parked: " + place[i] + " at spot " + i + " (Ticket opened)");
                return;
            }
        }
        System.out.println("The parking is full");
    }

    public void exitCar(Car car) {
        for(int i = 0; i < place.length; i++){
            if(place[i] != null && place[i].equals(car)){
                System.out.println("Car left unparked: " + place[i]);
                place[i] = null;

                // Cerca il ticket di questa macchina e registra l'orario di uscita
                for(Stopover ticket : history) {
                    if(ticket.getCar().equals(car) && ticket.getTimeOut() == 0) {
                        ticket.setTimeOut(System.currentTimeMillis());
                        System.out.println("Ticket closed: " + ticket.toString());
                        break;
                    }
                }
                return;
            }
        }
        System.out.println("Error: Car not found in parking.");
    }

    public int currentCapacity(){
        int count = 0;
        for (Car c : place) {
            if (c != null) {
                count++;
            }
        }
        return count;
    }

    public int getFreeCurretPlace(){
        int count = 0;
        for(int i = 0; i < place.length; i++){
            if(place[i] == null){
                count++;
            }
        }
        return count;
    }

    public double getAmount(){
        int amount = 0;
        for(Stopover ticket : history){
            amount += ticket.getTotalPrice();
        }
        return amount;
    }
}