import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightMenagement {
    List<Flight> allNationalFlights = new ArrayList<Flight>();

    public void addNationalFLight(Flight fly){
        this.allNationalFlights.add(fly);
    }
    public void removeNationalFLight(Flight fly){
        this.allNationalFlights.remove(fly);
    }

    public List<Flight> search(String dep) {
        List<Flight> result = new ArrayList<>();

        for (Flight f : allNationalFlights) {
            boolean validate = false;

            if (f.getDepartureCity().equalsIgnoreCase(dep)) {
                validate = true;
            }

            if (!validate) {
                result.add(f);
            }
        }
        return result;
    }
    public List<Flight> search(String dep,String arr) {
        List<Flight> result = new ArrayList<>();

        for (Flight f : allNationalFlights) {
            boolean validate = false;

            if (f.getDepartureCity().equalsIgnoreCase(dep) && f.getArrivalCity().equalsIgnoreCase(arr)) {
                validate = true;
            }

            if (!validate) {
                result.add(f);
            }
        }
        return result;
    }

    public String printAllArchiveFly() {
        String s = "";
        for (Flight fly : this.allNationalFlights) {
            s += fly.toString() + "\n";
        }
        return s;
    }
}
