import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManagerGUI extends JFrame {

    private List<Player> playersList;
    private JTable playerTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner numberSpinner;
    private JComboBox<Position> positionCombo;
    private JComboBox<Nationality> nationalityCombo;
    private JCheckBox captainCheck;

    public PlayerManagerGUI() {
        setTitle("Team Management - Players");
        setSize(850, 450);

        // Disabilitiamo la chiusura automatica standard per gestirla noi col salvataggio
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        playersList = new ArrayList<>();

        // --- LEFT PANEL (Player Table) ---
        String[] columnNames = {"Name", "Surname", "Number", "Position", "Nat.", "Captain"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        playerTable = new JTable(tableModel);
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        playerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && playerTable.getSelectedRow() != -1) {
                int selectedRow = playerTable.getSelectedRow();
                populateForm(playersList.get(selectedRow));
            }
        });

        JScrollPane listScroller = new JScrollPane(playerTable);
        listScroller.setBorder(BorderFactory.createTitledBorder("Player Database"));
        listScroller.setPreferredSize(new Dimension(450, 0));
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
        addPlayerToTable(new Player("Roberto", "Baggio", 10, Position.RWB, Nationality.ITA, true));
    }

    // Metodo di supporto per aggiungere il giocatore sia alla lista che alla tabella
    private void addPlayerToTable(Player p) {
        playersList.add(p);
        tableModel.addRow(new Object[]{
                p.getName(),
                p.getSurname(),
                p.getNumberOfTshirt(),
                p.getPosition(),
                p.getNationality(),
                p.isCaptain() ? "Yes" : "No"
        });
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
            addPlayerToTable(p);
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePlayer(ActionEvent e) {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a player from the table to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Aggiorna l'oggetto Player
            Player p = playersList.get(selectedRow);
            p.setName(nameField.getText());
            p.setSurname(surnameField.getText());
            p.setNumberOfTshirt((Integer) numberSpinner.getValue());
            p.setPosition((Position) positionCombo.getSelectedItem());
            p.setNationality((Nationality) nationalityCombo.getSelectedItem());
            p.setCaptain(captainCheck.isSelected());

            // Aggiorna la riga visiva nella tabella
            tableModel.setValueAt(p.getName(), selectedRow, 0);
            tableModel.setValueAt(p.getSurname(), selectedRow, 1);
            tableModel.setValueAt(p.getNumberOfTshirt(), selectedRow, 2);
            tableModel.setValueAt(p.getPosition(), selectedRow, 3);
            tableModel.setValueAt(p.getNationality(), selectedRow, 4);
            tableModel.setValueAt(p.isCaptain() ? "Yes" : "No", selectedRow, 5);

            JOptionPane.showMessageDialog(this, "Player updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePlayer(ActionEvent e) {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow != -1) {
            playersList.remove(selectedRow);      // Rimuove dall'array
            tableModel.removeRow(selectedRow);    // Rimuove dalla grafica
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
        playerTable.clearSelection();
        nameField.setText("");
        surnameField.setText("");
        numberSpinner.setValue(10);
        positionCombo.setSelectedIndex(0);
        nationalityCombo.setSelectedIndex(0);
        captainCheck.setSelected(false);
    }
}