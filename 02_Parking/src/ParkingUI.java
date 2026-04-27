import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingUI extends JFrame {

    private static final int CAPACITY = 10;
    private final Car[] place = new Car[CAPACITY];

    private JPanel gridPanel;
    private JTextField plateInput;
    private JLabel statOccupied;
    private JLabel statFree;
    private JPanel logPanel;
    private JScrollPane logScroll;

    private static final Color COLOR_BG        = new Color(250, 250, 248);
    private static final Color COLOR_SURFACE    = new Color(242, 240, 235);
    private static final Color COLOR_BORDER     = new Color(210, 208, 200);
    private static final Color COLOR_TEXT       = new Color(30, 30, 28);
    private static final Color COLOR_MUTED      = new Color(120, 118, 112);
    private static final Color COLOR_GREEN_BG   = new Color(225, 245, 238);
    private static final Color COLOR_GREEN_BD   = new Color(29, 158, 117);
    private static final Color COLOR_GREEN_TXT  = new Color(15, 110, 86);
    private static final Color COLOR_RED_BG     = new Color(250, 236, 231);
    private static final Color COLOR_RED_BD     = new Color(216, 90, 48);
    private static final Color COLOR_RED_TXT    = new Color(153, 60, 29);
    private static final Color COLOR_AMBER_BG   = new Color(250, 238, 218);
    private static final Color COLOR_AMBER_TXT  = new Color(133, 79, 11);

    public ParkingUI() {
        setTitle("Parking Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 600);
        setLocationRelativeTo(null);
        setBackground(COLOR_BG);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout(0, 0));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildControls(), BorderLayout.SOUTH);

        renderGrid();
        updateStats();
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, COLOR_BORDER),
                new EmptyBorder(0, 0, 10, 0)
        ));

        JLabel title = new JLabel("Parking Manager");
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        title.setForeground(COLOR_TEXT);

        JLabel sub = new JLabel("capacity: 10 posti");
        sub.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        sub.setForeground(COLOR_MUTED);

        JPanel left = new JPanel(new GridLayout(2, 1, 0, 2));
        left.setBackground(COLOR_BG);
        left.add(title);
        left.add(sub);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        stats.setBackground(COLOR_BG);

        statOccupied = new JLabel("0");
        statFree     = new JLabel("10");

        stats.add(buildStatCard(statOccupied, "occupati", COLOR_RED_TXT));
        stats.add(buildStatCard(statFree,     "liberi",   COLOR_GREEN_TXT));

        panel.add(left,  BorderLayout.WEST);
        panel.add(stats, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildStatCard(JLabel numLabel, String labelText, Color numColor) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 1));
        card.setBackground(COLOR_SURFACE);
        card.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        numLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        numLabel.setForeground(numColor);
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        lbl.setForeground(COLOR_MUTED);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(numLabel);
        card.add(lbl);
        return card;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setBackground(COLOR_BG);
        center.setBorder(new EmptyBorder(12, 0, 8, 0));

        gridPanel = new JPanel(new GridLayout(2, 5, 8, 8));
        gridPanel.setBackground(COLOR_BG);
        center.add(gridPanel, BorderLayout.NORTH);

        logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
        logPanel.setBackground(COLOR_SURFACE);
        logPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        logScroll = new JScrollPane(logPanel);
        logScroll.setPreferredSize(new Dimension(0, 155));
        logScroll.setBorder(new LineBorder(COLOR_BORDER, 1, true));
        logScroll.getViewport().setBackground(COLOR_SURFACE);
        logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel logTitle = new JLabel("CONSOLE LOG");
        logTitle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        logTitle.setForeground(COLOR_MUTED);
        logTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        logPanel.add(logTitle);
        logPanel.add(Box.createVerticalStrut(6));

        center.add(logScroll, BorderLayout.CENTER);
        return center;
    }

    private JPanel buildControls() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        plateInput = new JTextField();
        plateInput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        plateInput.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        plateInput.setPreferredSize(new Dimension(0, 36));

        plateInput.addActionListener(e -> handleEnter());

        JButton btnEnter = buildButton("+ Entra", COLOR_GREEN_BD, COLOR_GREEN_TXT, COLOR_GREEN_BG);
        JButton btnExit  = buildButton("- Esci",  COLOR_RED_BD,   COLOR_RED_TXT,   COLOR_RED_BG);

        btnEnter.addActionListener(e -> handleEnter());
        btnExit.addActionListener(e  -> handleExit());

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        btnPanel.setBackground(COLOR_BG);
        btnPanel.add(btnEnter);
        btnPanel.add(btnExit);

        panel.add(plateInput, BorderLayout.CENTER);
        panel.add(btnPanel,   BorderLayout.EAST);
        return panel;
    }

    private JButton buildButton(String text, Color border, Color fg, Color hoverBg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(hoverBg.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(hoverBg);
                } else {
                    g.setColor(COLOR_BG);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        btn.setForeground(fg);
        btn.setBorder(new CompoundBorder(
                new LineBorder(border, 1, true),
                new EmptyBorder(0, 14, 0, 14)
        ));
        btn.setPreferredSize(new Dimension(100, 36));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void renderGrid() {
        gridPanel.removeAll();
        for (int i = 0; i < CAPACITY; i++) {
            gridPanel.add(buildSpot(i));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel buildSpot(int i) {
        Car car = place[i];
        JPanel spot = new JPanel(new GridLayout(3, 1, 0, 2));
        spot.setPreferredSize(new Dimension(0, 70));
        spot.setBorder(new CompoundBorder(
                new LineBorder(car != null ? COLOR_GREEN_BD : COLOR_BORDER, 1, true),
                new EmptyBorder(6, 6, 6, 6)
        ));
        spot.setBackground(car != null ? COLOR_GREEN_BG : COLOR_BG);

        JLabel numLbl = new JLabel(String.format("#%02d", i + 1), SwingConstants.CENTER);
        numLbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        numLbl.setForeground(COLOR_MUTED);

        JLabel iconLbl = new JLabel(car != null ? "[ ]" : "---", SwingConstants.CENTER);
        iconLbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        iconLbl.setForeground(car != null ? COLOR_GREEN_TXT : COLOR_MUTED);

        JLabel plateLbl = new JLabel(car != null ? car.getPlate() : "libero", SwingConstants.CENTER);
        plateLbl.setFont(new Font(Font.MONOSPACED, car != null ? Font.BOLD : Font.PLAIN, 10));
        plateLbl.setForeground(car != null ? COLOR_TEXT : COLOR_MUTED);

        spot.add(numLbl);
        spot.add(iconLbl);
        spot.add(plateLbl);
        return spot;
    }

    private void handleEnter() {
        String plate = plateInput.getText().trim().toUpperCase();
        if (plate.isEmpty()) { addLog("WARN", "Inserisci una targa valida."); return; }
        enterCar(new Car(plate));
        plateInput.setText("");
        renderGrid();
        updateStats();
    }

    private void handleExit() {
        String plate = plateInput.getText().trim().toUpperCase();
        if (plate.isEmpty()) { addLog("WARN", "Inserisci una targa valida."); return; }
        exitCar(new Car(plate));
        plateInput.setText("");
        renderGrid();
        updateStats();
    }

    // ── Logica originale ──────────────────────────────────────────────────────

    private void enterCar(Car car) {
        for (int i = 0; i < place.length; i++) {
            if (place[i] != null && place[i].equals(car)) {
                addLog("WARN", "Car already entered.");
                return;
            }
        }
        for (int i = 0; i < place.length; i++) {
            if (place[i] == null) {
                place[i] = car;
                addLog("OK", "Car parked: " + place[i]);
                return;
            }
        }
        addLog("ERR", "The parking is full");
    }

    private void exitCar(Car car) {
        for (int i = 0; i < place.length; i++) {
            if (place[i] != null && place[i].equals(car)) {
                addLog("OK", "Car left unparked: " + place[i]);
                place[i] = null;
                return;
            }
        }
        addLog("ERR", "Error: Car not found in parking.");
    }

    private int currentCapacity() {
        int count = 0;
        for (Car c : place) if (c != null) count++;
        return count;
    }

    private int getFreeCurretPlace() {
        int count = 0;
        for (Car c : place) if (c == null) count++;
        return count;
    }

    // ─────────────────────────────────────────────────────────────────────────

    private void updateStats() {
        statOccupied.setText(String.valueOf(currentCapacity()));
        statFree.setText(String.valueOf(getFreeCurretPlace()));
    }

    private void addLog(String type, String msg) {
        Color bg  = switch (type) {
            case "OK"   -> COLOR_GREEN_BG;
            case "ERR"  -> COLOR_RED_BG;
            default     -> COLOR_AMBER_BG;
        };
        Color fg  = switch (type) {
            case "OK"   -> COLOR_GREEN_TXT;
            case "ERR"  -> COLOR_RED_TXT;
            default     -> COLOR_AMBER_TXT;
        };

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
        row.setBackground(COLOR_SURFACE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tag = new JLabel(type);
        tag.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        tag.setForeground(fg);
        tag.setBackground(bg);
        tag.setOpaque(true);
        tag.setBorder(new EmptyBorder(1, 5, 1, 5));

        JLabel text = new JLabel(msg);
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        text.setForeground(COLOR_TEXT);

        row.add(tag);
        row.add(text);
        logPanel.add(row);
        logPanel.add(Box.createVerticalStrut(2));
        logPanel.revalidate();

        SwingUtilities.invokeLater(() -> {
            JScrollBar sb = logScroll.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new ParkingUI().setVisible(true);
        });
    }
}