import javax.swing.SwingUtilities;

public class Main1 {
    public static void main(String[] args) {
        Player p1 = new Player("Cristiano", "Ronaldo","17/02/2007",Gender.M, 7, Position.RWB, Nationality.ITA, false);
        Person per1 = new Person("Pierfrancesco","Blancato","17/02/2007",Gender.M,Nationality.ITA);
        SwingUtilities.invokeLater(() -> {
            new PlayerDatabaseGUI().setVisible(true);
        });

        System.out.println(p1.generateTaxCode());
        System.out.println(p1.toString());
        System.out.println(per1.generateTaxCode());
        System.out.println(per1.toString());
    }
}