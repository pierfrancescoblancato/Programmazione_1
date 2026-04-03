import javax.swing.SwingUtilities;

public class Main1 {
    public static void main(String[] args) {
        // Starts the GUI in the correct Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            PlayerManagerGUI app = new PlayerManagerGUI();
            app.setVisible(true);
        });
    }
}