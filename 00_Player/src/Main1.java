import javax.swing.SwingUtilities;

public class Main1 {
    public static void main(String[] args) {
        Player p1 = new Player("Roberto", "Baggio","10/12/2007",Gender.F, 10, Position.RWB, Nationality.ITA, true);

        SwingUtilities.invokeLater(() -> {
            new PlayerDatabaseGUI().setVisible(true);
        });

        System.out.println(p1.generateTaxCode());
        System.out.println(p1.toString());

    }
}