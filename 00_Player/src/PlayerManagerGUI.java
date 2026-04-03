import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlayerManagerGUI extends JFrame {

    private DefaultListModel<Player> listModel;
    private JList<Player> playerList;

    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner numberSpinner;
    private JComboBox<Position> positionCombo;
    private JComboBox<Nationality> nationalityCombo;
    private JCheckBox captainCheck;

    public PlayerManagerGUI() {
        setTitle("Team Management - Players");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- LEFT PANEL (Player List) ---
        listModel = new DefaultListModel<>();
        playerList = new JList<>(listModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        playerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && playerList.getSelectedIndex() != -1) {
                populateForm(playerList.getSelectedValue());
            }
        });

        JScrollPane listScroller = new JScrollPane(playerList);
        listScroller.setBorder(BorderFactory.createTitledBorder("Player List"));
        listScroller.setPreferredSize(new Dimension(300, 0));
        add(listScroller, BorderLayout.WEST);

        // --- RIGHT PANEL (Form and Buttons) ---
        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Player Details"));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Surname:"));
        surnameField = new JTextField();
        formPanel.add(surnameField);

        formPanel.add(new JLabel("Shirt Number (1-100):"));
        numberSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        formPanel.add(numberSpinner);

        formPanel.add(new JLabel("Position:"));
        positionCombo = new JComboBox<>(Position.values());
        formPanel.add(positionCombo);

        formPanel.add(new JLabel("Nationality:"));
        nationalityCombo = new JComboBox<>(Nationality.values());
        formPanel.add(nationalityCombo);

        formPanel.add(new JLabel("Captain:"));
        captainCheck = new JCheckBox();
        formPanel.add(captainCheck);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(this::addPlayer);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(this::updatePlayer);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(this::deletePlayer);

        JButton btnClear = new JButton("Clear Form");
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);

        // Add default player for testing
        listModel.addElement(new Player("Roberto", "Baggio", 10, Position.F, Nationality.ITA, true));
    }

    private void addPlayer(ActionEvent e) {
        try {
            Player p = new Player(
                    nameField.getText(),
                    surnameField.getText(),
                    (Integer) numberSpinner.getValue(),
                    (Position) positionCombo.getSelectedItem(),
                    (Nationality) nationalityCombo.getSelectedItem(),
                    captainCheck.isSelected()
            );
            listModel.addElement(p);
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePlayer(ActionEvent e) {
        int selectedIndex = playerList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select a player from the list to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Player p = listModel.get(selectedIndex);
            p.setName(nameField.getText());
            p.setSurname(surnameField.getText());
            p.setNumberOfTshirt((Integer) numberSpinner.getValue());
            p.setPosition((Position) positionCombo.getSelectedItem());
            p.setNationality((Nationality) nationalityCombo.getSelectedItem());
            p.setCaptain(captainCheck.isSelected());

            playerList.repaint();
            JOptionPane.showMessageDialog(this, "Player updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePlayer(ActionEvent e) {
        int selectedIndex = playerList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Select a player to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void populateForm(Player p) {
        nameField.setText(p.getName());
        surnameField.setText(p.getSurname());
        numberSpinner.setValue(p.getNumberOfTshirt());
        positionCombo.setSelectedItem(p.getPosition());
        nationalityCombo.setSelectedItem(p.getNationality());
        captainCheck.setSelected(p.isCaptain());
    }

    private void clearForm() {
        playerList.clearSelection();
        nameField.setText("");
        surnameField.setText("");
        numberSpinner.setValue(10);
        positionCombo.setSelectedIndex(0);
        nationalityCombo.setSelectedIndex(0);
        captainCheck.setSelected(false);
    }
}