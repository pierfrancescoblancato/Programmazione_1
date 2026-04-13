import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDatabaseGUI extends JFrame {

    // Lista per memorizzare i giocatori (il vero "database" in memoria)
    private List<Player> giocatori = new ArrayList<>();
    private final String FILE_NAME = "database_giocatori.dat";

    // Componenti del Form
    private JTextField txtName, txtSurname, txtDateBirth;
    private JComboBox<Gender> cbGender;
    private JSpinner spinShirtNumber;
    private JComboBox<Position> cbPosition;
    private JComboBox<Nationality> cbNationality;
    private JCheckBox chkCaptain;

    // Componenti della Tabella
    private JTable table;
    private DefaultTableModel tableModel;

    public PlayerDatabaseGUI() {
        setTitle("Gestione Giocatori - Database");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        creaMenu(); // Crea il menu per Salvare/Caricare

        // --- 1. PANNELLO SINISTRO (FORM) ---
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dati Giocatore"));
        formPanel.setPreferredSize(new Dimension(350, 0));

        formPanel.add(new JLabel("Nome:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Cognome:"));
        txtSurname = new JTextField();
        formPanel.add(txtSurname);

        formPanel.add(new JLabel("Data nascita (dd/MM/yyyy):"));
        txtDateBirth = new JTextField("10/12/2007");
        formPanel.add(txtDateBirth);

        formPanel.add(new JLabel("Genere:"));
        cbGender = new JComboBox<>(Gender.values());
        formPanel.add(cbGender);

        formPanel.add(new JLabel("Numero Maglia (1-100):"));
        spinShirtNumber = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        formPanel.add(spinShirtNumber);

        formPanel.add(new JLabel("Posizione:"));
        cbPosition = new JComboBox<>(Position.values());
        formPanel.add(cbPosition);

        formPanel.add(new JLabel("Nazionalità:"));
        cbNationality = new JComboBox<>(Nationality.values());
        cbNationality.setSelectedItem(Nationality.ITA);
        formPanel.add(cbNationality);

        formPanel.add(new JLabel("Capitano:"));
        chkCaptain = new JCheckBox();
        formPanel.add(chkCaptain);

        JButton btnClear = new JButton("Pulisci Campi");
        btnClear.addActionListener(e -> pulisciForm());
        formPanel.add(new JLabel(""));
        formPanel.add(btnClear);

        add(formPanel, BorderLayout.WEST);

        // --- 2. PANNELLO CENTRALE (TABELLA) ---
        String[] colonne = {"Nome", "Cognome", "Nascita", "Sesso", "Maglia", "Ruolo", "Naz.", "Cap.", "C.F."};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                caricaDatiSelezionati();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tabella Giocatori"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. PANNELLO INFERIORE (BOTTONI CRUD) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAdd = new JButton("Aggiungi Giocatore");
        JButton btnUpdate = new JButton("Aggiorna Selezionato");
        JButton btnDelete = new JButton("Rimuovi Selezionato");

        btnAdd.setBackground(new Color(144, 238, 144));
        btnDelete.setBackground(new Color(255, 99, 71));

        actionPanel.add(btnAdd);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);

        add(actionPanel, BorderLayout.SOUTH);

        // --- AZIONI DEI BOTTONI CRUD ---
        btnAdd.addActionListener(e -> {
            try {
                Player p = creaGiocatoreDalForm();
                giocatori.add(p); // Aggiunge alla lista
                aggiornaTabella(); // Ridisegna la tabella
                pulisciForm();
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                mostraErrore("Seleziona un giocatore dalla tabella.");
                return;
            }
            try {
                Player p = creaGiocatoreDalForm();
                giocatori.set(row, p); // Sostituisce nella lista
                aggiornaTabella();
                pulisciForm();
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                mostraErrore("Seleziona un giocatore da rimuovere.");
            } else {
                giocatori.remove(row); // Rimuove dalla lista
                aggiornaTabella();
                pulisciForm();
            }
        });

        // Tenta di caricare i dati all'avvio
        caricaDaFile(false);
    }

    // --- METODI DI SUPPORTO ---

    private void creaMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");

        JMenuItem itemSalva = new JMenuItem("Salva Dati (Export)");
        JMenuItem itemCarica = new JMenuItem("Carica Dati (Import)");

        itemSalva.addActionListener(e -> salvaSuFile());
        itemCarica.addActionListener(e -> caricaDaFile(true));

        menuFile.add(itemSalva);
        menuFile.add(itemCarica);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private Player creaGiocatoreDalForm() {
        Player p = new Player();
        p.setName(txtName.getText());
        p.setSurname(txtSurname.getText());
        p.setDateBirth(txtDateBirth.getText());
        p.setGender((Gender) cbGender.getSelectedItem());
        p.setNumberOfTshirt((Integer) spinShirtNumber.getValue());
        p.setPosition((Position) cbPosition.getSelectedItem());
        p.setNationality((Nationality) cbNationality.getSelectedItem());
        p.setCaptain(chkCaptain.isSelected());
        return p;
    }

    private void caricaDatiSelezionati() {
        int row = table.getSelectedRow();
        Player p = giocatori.get(row); // Prende i dati direttamente dalla lista

        txtName.setText(p.getName());
        txtSurname.setText(p.getSurname());
        txtDateBirth.setText(p.getDateBirth());
        cbGender.setSelectedItem(p.getGender());
        spinShirtNumber.setValue(p.getNumberOfTshirt());
        cbPosition.setSelectedItem(p.getPosition());
        cbNationality.setSelectedItem(p.getNationality());
        chkCaptain.setSelected(p.isCaptain());
    }

    private void aggiornaTabella() {
        tableModel.setRowCount(0); // Svuota la tabella
        for (Player p : giocatori) {
            Object[] rowData = {
                    p.getName(), p.getSurname(), p.getDateBirth(), p.getGender().name(),
                    p.getNumberOfTshirt(), p.getPosition().name(), p.getNationality().name(),
                    p.isCaptain() ? "Sì" : "No", p.generateTaxCode()
            };
            tableModel.addRow(rowData);
        }
    }

    private void pulisciForm() {
        txtName.setText("");
        txtSurname.setText("");
        txtDateBirth.setText("01/01/2000");
        cbGender.setSelectedIndex(0);
        spinShirtNumber.setValue(10);
        cbPosition.setSelectedIndex(0);
        cbNationality.setSelectedItem(Nationality.ITA);
        chkCaptain.setSelected(false);
        table.clearSelection();
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    // --- SALVATAGGIO E CARICAMENTO (SERIALIZZAZIONE) ---
    private void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(giocatori);
            JOptionPane.showMessageDialog(this, "Dati salvati con successo in " + FILE_NAME, "Salvataggio", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            mostraErrore("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void caricaDaFile(boolean mostraMessaggio) {
        File f = new File(FILE_NAME);
        if (!f.exists()) return; // Se il file non esiste, esce senza errori

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            giocatori = (List<Player>) ois.readObject();
            aggiornaTabella();
            if (mostraMessaggio) {
                JOptionPane.showMessageDialog(this, "Dati caricati con successo!", "Caricamento", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            mostraErrore("Errore durante il caricamento: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PlayerDatabaseGUI().setVisible(true);
        });
    }
}