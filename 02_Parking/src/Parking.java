import java.util.ArrayList;
import java.time.LocalDateTime;

public class Parking {

    Car[] place;
    ArrayList<Stopover> history = new ArrayList<>();

    public Parking(int capacity) {
        this.place = new Car[capacity];
    }

    public Car[] getPlace() {
        return place;
    }

    public void setPlace(Car[] place) {
        this.place = place;
    }

    public void enterCar(Car car) {
        for(int i = 0; i < place.length; i++){
            if(place[i] != null && place[i].equals(car)){
                System.out.println("Car already entered.");
                return;
            }
        }
        for(int i = 0; i < place.length; i++ ){
            if(place[i] == null){
                place[i] = car;

                // Crea il ticket e lo salva nello storico
                Stopover ticket = new Stopover(car, i);
                history.add(ticket);

                System.out.println("Car parked: " + place[i] + " (Ticket opened)");
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
                    if(ticket.getCar().equals(car) && ticket.getTimestampOut() == null) {
                        ticket.setTimestampOut(LocalDateTime.now());
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
        for(int i = 0; i < place.length; i++){
            if(place[i] != null){
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
}