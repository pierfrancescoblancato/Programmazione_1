import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Airport airport = new Airport();
        Plane p1 = new Airliner(111,"777","boieng",400);
        Flights fly = new Flights(LocalDateTime.now(),LocalDateTime.of(2026, 5, 8, 14, 0),p1);

    }
}