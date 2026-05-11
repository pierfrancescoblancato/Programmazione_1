import java.time.LocalDateTime;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String filename = "/home/pierfrancescoblancato/IdeaProjects/Programmazione_1/03_Airport/src/data/flights";
        FlightMenagement archive = new FlightMenagement();

        //archive.loadFromFile(filename);

        Airport cta = new Airport("Fontanarossa", "Catania", "CTA");
        Airport fco = new Airport("Fiumicino", "Roma", "FCO");
        Airport mxp = new Airport("Malpensa", "Milano", "MXP");

        Plane p1 = new Airliner(101, "A320", "Airbus", 180);
        Plane p2 = new Cargo(505, "747-8F", "Boeing", 1.4f);

        Flight f1 = new Flight(
                cta.getCity(),
                fco.getCity(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                p1
        );

        Flight f2 = new Flight(
                fco.getCity(),
                mxp.getCity(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                p2
        );

        archive.addNationalFLight(f1);
        archive.addNationalFLight(f2);
        archive.saveToFile(filename);

        System.out.println("=== ALL FLIGHTS IN ARCHIVE ===");
        System.out.println(archive.printAllArchiveFly());

        System.out.println("=== ESTIMATED WEIGHTS ===");
        System.out.println("Estimated Weight Flight 1: " + f1.getPlane().getWeight() + " kg");
        System.out.println("Estimated Weight Flight 2: " + f2.getPlane().getWeight() + " kg");


        System.out.println("=== SEARCH RESULTS: Departing from Catania ===");

        archive.search("Catania","Roma");
        archive.search("Catania");

    }
}
