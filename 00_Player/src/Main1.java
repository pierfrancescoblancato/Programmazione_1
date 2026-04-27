import javax.swing.SwingUtilities;

public class Main1 {
    public static void main(String[] args) {
        Player player1 = new Player("Cristiano", "Ronaldo","17/02/2007",Gender.M, 7, Position.RWB, Nationality.ITA, false);
        Person person1 = new Person("Pierfrancesco","Blancato","17/02/2007",Gender.M,Nationality.ITA);

//        SwingUtilities.invokeLater(() -> {
//            new PlayerDatabaseGUI().setVisible(true);
//        });

        System.out.println(player1.generateTaxCode());
        System.out.println(player1.toString());
        System.out.println(person1.generateTaxCode());
        System.out.println(person1.toString());
        System.out.println(player1.equals(player1));
    }
}