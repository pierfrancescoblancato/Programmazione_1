import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        //spostare la logica di inserimento dei voli da airport a un sistema nazionale dei voli dandoti la possibilita di cercare tramite nome
        FlightMenagment archive = new FlightMenagment();

        Airport cta = new Airport("Fontanarossa","Catania","Cta");
        Airport mpx = new Airport("Silvio Berlusconi","Varese","MPX");
        Airport fco = new Airport("Leonardo da Vinci","Roma","FCO");

        Plane p1 = new Airliner(111,"B777","Boieng",400);
        Plane p2 = new Airliner(222,"A320","Airbus",240);
        Plane p3 = new Cargo(111,"B777","Boieng",400);

        Flights f1 =new Flights(fco.getName(), mpx.getName(),LocalDateTime.of(2026, 05, 20, 10, 10),LocalDateTime.of(2026, 05, 20, 12, 10),p1);
        Flights f2 =new Flights(cta.getName(), mpx.getName(),LocalDateTime.of(2026, 05, 20, 10, 10),LocalDateTime.of(2026, 05, 20, 12, 10),p2);
        Flights f3 =new Flights(cta.getName(), fco.getName(),LocalDateTime.of(2026, 05, 20, 10, 10),LocalDateTime.of(2026, 05, 20, 12, 10),p3);

        archive.addNationalFLight(f1);
        archive.addNationalFLight(f2);
        archive.addNationalFLight(f3);

        System.out.println(archive.printAllArchiveFly());    }
}