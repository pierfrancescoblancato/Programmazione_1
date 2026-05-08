import java.util.ArrayList;
import java.util.List;

public class Airport {
    List<Flights> fly = new ArrayList<Flights>();

    public List<Flights> getFly() {
        return fly;
    }

    public void setFly(List<Flights> fly) {
        this.fly = fly;
    }

    public void addFly(Flights f) {
        fly.add(f);
    }
    public void removeFly(Flights f) {
        fly.remove(f);
    }
}
