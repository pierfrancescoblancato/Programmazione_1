import java.util.ArrayList;
import java.util.List;

public class Airport {
    List<Flight> flies = new ArrayList<Flight>();
    private String name;
    private String city;
    private String iata;

    public Airport( String name, String city, String iata) {
        this.name = name;
        this.city = city;
        this.iata = iata;
    }

    public List<Flight> getFlies() {
        return flies;
    }

    public void setFlies(List<Flight> flies) {
        this.flies = flies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String printFly() {
        String s = "";
        for (Flight fly : this.flies) {
            s += fly.toString() + "\n";
        }
        return s;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "fly=" + flies +
                '}';
    }
}
