import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightMenagment {
    List<Flights> allNationalFlights = new ArrayList<Flights>();

    public void addNationalFLight(Flights fly){
        this.allNationalFlights.add(fly);
    }
    public void removeNationalFLight(Flights fly){
        this.allNationalFlights.remove(fly);
    }

    // I parametri possono essere null se l'utente non li inserisce
    public List<Flights> search(String dep, String arr, LocalDateTime date) {
        List<Flights> result = new ArrayList<>();

        for (Flights f : allNationalFlights) {
            boolean validate = true;

            if (dep != null && !f.getDepartureCity().equalsIgnoreCase(dep)) {
                validate = false;
            }

            if (arr != null && !f.getArrivalCity().equalsIgnoreCase(arr)) {
                validate = false;
            }

            if (validate) {
                result.add(f);
            }
        }
        return result;
    }

    public String printAllArchiveFly() {
        String s = "";
        for (Flights fly : this.allNationalFlights) {
            s += fly.toString() + "\n";
        }
        return s;
    }
}
