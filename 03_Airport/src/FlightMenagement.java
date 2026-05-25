import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class FlightMenagement {
    List<Flight> allNationalFlights = new ArrayList<Flight>();

    public void saveToFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            System.out.println("Saving " + allNationalFlights.size() + " flights...");

            for (Flight f : allNationalFlights) {
                String line = "{\n" +
                        "\"departureCity\": \"" + f.getDepartureCity() + "\",\n" +
                        "\"arrivalCity\": \"" + f.getArrivalCity() + "\",\n" +
                        "\"departureTime\": \"" + f.getDepartureTime() + "\",\n" +
                        "\"arrivalTime\": \"" + f.getArrivalTime() + "\",\n" +
                        "\"plane\": {\n" +
                        "    \"id\": \"" + f.getPlane().getId() + "\",\n" +
                        "    \"model\": \"" + f.getPlane().getModel() + "\",\n" +
                        "    \"producer\": \"" + f.getPlane().getProducer() + "\"\n" +
                        "    \"type\": \"" + (f.getPlane() instanceof Airliner ? "AIRLINER" : "CARGO") + "\",\n" +
                        "    \"value\": " + (f.getPlane() instanceof Airliner ? ((Airliner) f.getPlane()).getMaxSeats() : ((Cargo) f.getPlane()).getCapacity()*1000+"kg") + "\n" +
                        "     },\n" +
                        "}";

                System.out.println("Writing: " + line);  // DEBUG
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            allNationalFlights.clear();

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");

                Plane plane;
                if (p[7].equals("AIRLINER")) {
                    plane = new Airliner(
                            Integer.parseInt(p[4]),  // id
                            p[5],                     // model
                            p[6],                     // producer
                            Integer.parseInt(p[8])    // maxSeats
                    );
                } else {
                    plane = new Cargo(
                            Integer.parseInt(p[4]),  // id
                            p[5],                     // model
                            p[6],                     // producer
                            Float.parseFloat(p[8])    // capacity
                    );
                }

                Flight f = new Flight(p[0], p[1], LocalDateTime.parse(p[2]), LocalDateTime.parse(p[3]), plane);
                allNationalFlights.add(f);
            }
            reader.close();
            System.out.println("Loaded successfully!");
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void addNationalFLight(Flight fly){
        this.allNationalFlights.add(fly);
    }
    public void removeNationalFLight(Flight fly){
        this.allNationalFlights.remove(fly);
    }

    public List<Flight> search(String dep) {
        List<Flight> result = new ArrayList<>();

        System.out.println("=== Flights departing from " + dep + " ===");

        for (Flight f : allNationalFlights) {
            if (f.getDepartureCity().equalsIgnoreCase(dep)) {
                result.add(f);
                System.out.println(f);
            }
        }
        return result;
    }

    public List<Flight> search(String dep, String arr) {
        List<Flight> result = new ArrayList<>();

        System.out.println("=== Flights from " + dep + " to " + arr + " ===");

        for (Flight f : allNationalFlights) {
            if (f.getDepartureCity().equalsIgnoreCase(dep) &&
                    f.getArrivalCity().equalsIgnoreCase(arr)) {
                result.add(f);
                System.out.println(f);
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
