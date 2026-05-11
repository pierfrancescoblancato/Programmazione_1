import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FlightMenagement archive = new FlightMenagement();

        Airport cta = new Airport("Fontanarossa", "Catania", "CTA");
        Airport fco = new Airport("Fiumicino", "Roma", "FCO");
        Airport mxp = new Airport("Malpensa", "Milano", "MXP");

        Plane p1 = new Airliner(101, "A320", "Airbus", 180);
        Plane p2 = new Cargo(505, "747-8F", "Boeing", 1.4f);

        Flight f1 = new Flight(
                cta.getName(),
                fco.getName(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                p1
        );

        Flight f2 = new Flight(
                fco.getName(),
                mxp.getName(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                p2
        );

        archive.addNationalFLight(f1);
        archive.addNationalFLight(f2);

        System.out.println("=== ALL FLIGHTS IN ARCHIVE ===");
        System.out.println(archive.printAllArchiveFly());

        System.out.println("=== ESTIMATED WEIGHTS ===");
        System.out.println("Estimated Weight Flight 1: " + f1.getPlane().getWeight() + " kg");
        System.out.println("Estimated Weight Flight 2: " + f2.getPlane().getWeight() + " kg");


        System.out.println("=== SEARCH RESULTS: Departing from Catania ===");
        List<Flight> filtered = archive.search("Catania","Roma");

        for (Flight f : filtered) {
            System.out.println(f);
        }

    }
}
