import javax.swing.SwingUtilities;

public class Main1 {
    public static void main(String[] args) {
        Player p1 = new Player("Roberto", "Baggio", 10, Position.RWB, Nationality.ITA, true);

        SwingUtilities.invokeLater(() -> {
            PlayerManagerGUI app = new PlayerManagerGUI();
            app.setVisible(true);
        });
        System.out.println(p1.toString());
    }
}