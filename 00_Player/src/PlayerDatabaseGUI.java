import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDatabaseGUI extends JFrame {

    // ── Palette colori ────────────────────────────────────────────────────────
    private static final Color BG_DARK       = new Color(0x13, 0x17, 0x1F);
    private static final Color BG_CARD       = new Color(0x1C, 0x22, 0x2E);
    private static final Color BG_CARD2      = new Color(0x22, 0x2A, 0x38);
    private static final Color ACCENT        = new Color(0x00, 0xC2, 0xFF);
    private static final Color ACCENT_SOFT   = new Color(0x00, 0x80, 0xAA, 80);
    private static final Color TEXT_PRIMARY  = new Color(0xE8, 0xEF, 0xF8);
    private static final Color TEXT_MUTED    = new Color(0x7A, 0x8A, 0xA0);
    private static final Color SUCCESS       = new Color(0x2E, 0xCC, 0x71);
    private static final Color DANGER        = new Color(0xE7, 0x4C, 0x3C);
    private static final Color WARNING       = new Color(0xF3, 0x9C, 0x12);
    private static final Color ROW_EVEN      = new Color(0x1C, 0x22, 0x2E);
    private static final Color ROW_ODD       = new Color(0x20, 0x27, 0x35);
    private static final Color ROW_SELECT    = new Color(0x00, 0xC2, 0xFF, 50);

    // ── Dati ──────────────────────────────────────────────────────────────────
    private List<Player> giocatori = new ArrayList<>();
    private final String FILE_NAME = "database_giocatori.dat";

    // ── Form ──────────────────────────────────────────────────────────────────
    private JTextField txtName, txtSurname, txtDateBirth, txtSearch;
    private JComboBox<Gender>       cbGender;
    private JSpinner                spinShirtNumber;
    private JComboBox<Position>     cbPosition;
    private JComboBox<Nationality>  cbNationality;
    private JCheckBox               chkCaptain;

    // ── Tabella ───────────────────────────────────────────────────────────────
    private JTable           table;
    private DefaultTableModel tableModel;

    // ── Stats ─────────────────────────────────────────────────────────────────
    private JLabel lblTotale, lblCap, lblNazLabel;
    private JLabel lblStatus;

    // ─────────────────────────────────────────────────────────────────────────
    public PlayerDatabaseGUI() {
        applyGlobalStyle();

        setTitle("⚽  Player Database");
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        // Layout root
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);
        setContentPane(root);

        creaMenu();

        // ── Header ──────────────────────────────────────────────────────────
        root.add(creaHeader(), BorderLayout.NORTH);

        // ── Centro: form + tabella ───────────────────────────────────────────
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                creaFormPanel(),
                crearightPanel());
        split.setDividerLocation(340);
        split.setDividerSize(4);
        split.setBorder(null);
        split.setBackground(BG_DARK);
        split.setContinuousLayout(true);
        root.add(split, BorderLayout.CENTER);

        // ── Status bar ──────────────────────────────────────────────────────
        root.add(creaStatusBar(), BorderLayout.SOUTH);

        caricaDaFile(false);
        aggiornaStats();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  STILE GLOBALE
    // ══════════════════════════════════════════════════════════════════════════
    private void applyGlobalStyle() {
        UIManager.put("Panel.background",          BG_DARK);
        UIManager.put("ScrollPane.background",     BG_DARK);
        UIManager.put("Viewport.background",       BG_DARK);
        UIManager.put("TextField.background",      BG_CARD2);
        UIManager.put("TextField.foreground",      TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", ACCENT);
        UIManager.put("TextField.border",
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0x35, 0x45, 0x55), 1),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        UIManager.put("ComboBox.background",       BG_CARD2);
        UIManager.put("ComboBox.foreground",       TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground", ACCENT);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("Spinner.background",        BG_CARD2);
        UIManager.put("Spinner.foreground",        TEXT_PRIMARY);
        UIManager.put("CheckBox.background",       BG_CARD);
        UIManager.put("CheckBox.foreground",       TEXT_PRIMARY);
        UIManager.put("Label.foreground",          TEXT_PRIMARY);
        UIManager.put("Menu.background",           BG_CARD);
        UIManager.put("Menu.foreground",           TEXT_PRIMARY);
        UIManager.put("MenuBar.background",        BG_CARD);
        UIManager.put("MenuItem.background",       BG_CARD);
        UIManager.put("MenuItem.foreground",       TEXT_PRIMARY);
        UIManager.put("MenuItem.selectionBackground", ACCENT);
        UIManager.put("OptionPane.background",     BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Button.background",         BG_CARD2);
        UIManager.put("Button.foreground",         TEXT_PRIMARY);
        UIManager.put("SplitPane.background",      BG_DARK);
        UIManager.put("SplitPaneDivider.background", new Color(0x2A, 0x35, 0x45));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel creaHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // sfumatura orizzontale
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0x0A, 0x14, 0x2A),
                        getWidth(), 0, new Color(0x00, 0x2A, 0x38));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // linea accent in basso
                g2.setColor(ACCENT);
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        // Titolo
        JLabel title = new JLabel("⚽  PLAYER DATABASE");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);

        // Barra ricerca
        JPanel searchBox = new JPanel(new BorderLayout(6, 0));
        searchBox.setOpaque(false);
        JLabel searchIcon = new JLabel("🔍");
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(260, 32));
        txtSearch.putClientProperty("JTextField.placeholderText", "Cerca giocatore…");
        txtSearch.setBackground(new Color(0x0D, 0x19, 0x2A));
        txtSearch.setForeground(TEXT_PRIMARY);
        txtSearch.setCaretColor(ACCENT);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtraTabella(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtraTabella(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtraTabella(); }
        });
        searchBox.add(searchIcon, BorderLayout.WEST);
        searchBox.add(txtSearch,  BorderLayout.CENTER);

        // Pannello stats compatto
        JPanel statsBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        statsBox.setOpaque(false);
        lblTotale   = creaStatChip("Giocatori", "0", ACCENT);
        lblCap      = creaStatChip("Capitano", "—", WARNING);
        lblNazLabel = creaStatChip("Nazionalità", "0", SUCCESS);
        statsBox.add(lblTotale);
        statsBox.add(lblNazLabel);
        statsBox.add(lblCap);

        header.add(title,    BorderLayout.WEST);
        header.add(searchBox, BorderLayout.CENTER);
        header.add(statsBox,  BorderLayout.EAST);
        return header;
    }

    /** Crea un chip "Etichetta · valore" colorato */
    private JLabel creaStatChip(String label, String val, Color accent) {
        JLabel l = new JLabel(label + "  " + val) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 28));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 90));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setForeground(accent);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        l.setOpaque(false);
        l.setName(label); // usato per aggiornare il testo dopo
        return l;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANNELLO FORM (sinistra)
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel creaFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_CARD);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Titolo sezione
        JLabel sectionTitle = new JLabel("  Dati Giocatore");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sectionTitle.setForeground(ACCENT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 0));
        wrapper.add(sectionTitle, BorderLayout.NORTH);

        // Form con GridBagLayout per più controllo
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        GridBagConstraints lbl = new GridBagConstraints();
        lbl.anchor = GridBagConstraints.WEST; lbl.insets = new Insets(6, 0, 2, 0);
        lbl.gridx = 0; lbl.fill = GridBagConstraints.HORIZONTAL; lbl.weightx = 1;

        GridBagConstraints fld = new GridBagConstraints();
        fld.anchor = GridBagConstraints.WEST; fld.insets = new Insets(0, 0, 4, 0);
        fld.gridx = 0; fld.fill = GridBagConstraints.HORIZONTAL; fld.weightx = 1;

        int row = 0;

        txtName      = new JTextField();
        txtSurname   = new JTextField();
        txtDateBirth = new JTextField("10/12/2007");
        cbGender     = new JComboBox<>(Gender.values());
        spinShirtNumber = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        cbPosition   = new JComboBox<>(Position.values());
        cbNationality = new JComboBox<>(Nationality.values());
        cbNationality.setSelectedItem(Nationality.ITA);
        chkCaptain   = new JCheckBox("  È il capitano della squadra");
        chkCaptain.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        stilizzaSpinner(spinShirtNumber);

        Object[][] campi = {
                {"Nome", txtName},
                {"Cognome", txtSurname},
                {"Data di nascita (dd/MM/yyyy)", txtDateBirth},
                {"Genere", cbGender},
                {"Numero maglia (1–100)", spinShirtNumber},
                {"Posizione", cbPosition},
                {"Nazionalità", cbNationality},
        };

        for (Object[] c : campi) {
            lbl.gridy = row++;
            JLabel l = new JLabel((String) c[0]);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            l.setForeground(TEXT_MUTED);
            form.add(l, lbl);

            fld.gridy = row++;
            JComponent comp = (JComponent) c[1];
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            form.add(comp, fld);
        }

        lbl.gridy = row++;
        form.add(new JLabel(" "), lbl);  // spacer
        fld.gridy = row++;
        chkCaptain.setBackground(BG_CARD);
        form.add(chkCaptain, fld);

        // Riempitore verticale
        GridBagConstraints spacer = new GridBagConstraints();
        spacer.gridy = row++; spacer.weighty = 1; spacer.fill = GridBagConstraints.VERTICAL;
        form.add(new JLabel(), spacer);

        JScrollPane scroll = new JScrollPane(form,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_CARD);
        wrapper.add(scroll, BorderLayout.CENTER);

        // ── Pulsanti azione ────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 6, 0));
        btnPanel.setBackground(BG_CARD);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        JButton btnAdd    = creaBottone("＋ Aggiungi", SUCCESS,   Color.WHITE);
        JButton btnUpdate = creaBottone("↻ Aggiorna",  ACCENT,    Color.WHITE);
        JButton btnDelete = creaBottone("✕ Rimuovi",   DANGER,    Color.WHITE);
        JButton btnClear  = creaBottoneFlat("⊘ Pulisci");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        JPanel btnPanel2 = new JPanel(new BorderLayout());
        btnPanel2.setBackground(BG_CARD);
        btnPanel2.setBorder(BorderFactory.createEmptyBorder(0, 12, 10, 12));
        btnPanel2.add(btnClear, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(BG_CARD);
        south.add(btnPanel,  BorderLayout.NORTH);
        south.add(btnPanel2, BorderLayout.SOUTH);
        wrapper.add(south, BorderLayout.SOUTH);

        // ── Azioni ────────────────────────────────────────────────────────
        btnAdd.addActionListener(e -> {
            try {
                Player p = creaGiocatoreDalForm();
                giocatori.add(p);
                aggiornaTabella();
                aggiornaStats();
                pulisciForm();
                setStatus("Giocatore aggiunto: " + p.getName() + " " + p.getSurname(), SUCCESS);
            } catch (Exception ex) { mostraErrore(ex.getMessage()); }
        });

        btnUpdate.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { mostraErrore("Seleziona un giocatore dalla tabella."); return; }
            try {
                int modelRow = table.convertRowIndexToModel(r);
                Player p = creaGiocatoreDalForm();
                giocatori.set(modelRow, p);
                aggiornaTabella();
                aggiornaStats();
                pulisciForm();
                setStatus("Giocatore aggiornato.", ACCENT);
            } catch (Exception ex) { mostraErrore(ex.getMessage()); }
        });

        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { mostraErrore("Seleziona un giocatore da rimuovere."); return; }
            int modelRow = table.convertRowIndexToModel(r);
            String nome = giocatori.get(modelRow).getName();
            giocatori.remove(modelRow);
            aggiornaTabella();
            aggiornaStats();
            pulisciForm();
            setStatus("Giocatore rimosso: " + nome, DANGER);
        });

        btnClear.addActionListener(e -> pulisciForm());

        return wrapper;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANNELLO DESTRA (tabella)
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);

        // Colonne
        String[] cols = {"Nome", "Cognome", "Nascita", "Sesso", "Maglia", "Ruolo", "Naz.", "Cap.", "C.F."};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(0x00, 0xC2, 0xFF, 60));
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    c.setForeground(col == 7
                            ? (getValueAt(row, col).equals("Sì") ? WARNING : TEXT_MUTED)
                            : TEXT_PRIMARY);
                }
                if (c instanceof JLabel) ((JLabel) c).setBorder(
                        BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        };

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 2));
        table.setBackground(ROW_EVEN);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(ROW_SELECT);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setFocusable(false);

        // Header stile
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(0x0D, 0x14, 0x1E));
        header.setForeground(TEXT_MUTED);
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setReorderingAllowed(false);

        // Larghezze colonne
        int[] widths = {100, 100, 85, 55, 60, 90, 50, 45, 140};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Sorter (ricerca + ordinamento)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1)
                caricaDatiSelezionati();
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_DARK);
        scroll.setBackground(BG_DARK);

        // Decorazione titolo colonna con bordo accent
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setBackground(new Color(0x0D, 0x14, 0x1E));
        JLabel tableTitle = new JLabel("  Registro Giocatori");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableTitle.setForeground(ACCENT);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 0));
        tableHeader.add(tableTitle, BorderLayout.WEST);

        panel.add(tableHeader, BorderLayout.NORTH);
        panel.add(scroll,      BorderLayout.CENTER);

        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  STATUS BAR
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel creaStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0x0D, 0x12, 0x1C));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x25, 0x35, 0x45)),
                BorderFactory.createEmptyBorder(4, 14, 4, 14)));

        lblStatus = new JLabel("Pronto.");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatus.setForeground(TEXT_MUTED);
        bar.add(lblStatus, BorderLayout.WEST);

        JLabel ver = new JLabel("Player Database v2.0  •  ⚽");
        ver.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ver.setForeground(new Color(0x35, 0x45, 0x55));
        bar.add(ver, BorderLayout.EAST);

        return bar;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENU
    // ══════════════════════════════════════════════════════════════════════════
    private void creaMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(BG_CARD);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0x25, 0x35, 0x45)));

        JMenu menuFile = new JMenu("File");
        menuFile.setForeground(TEXT_PRIMARY);
        menuFile.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem itemSalva  = new JMenuItem("💾  Salva Dati");
        JMenuItem itemCarica = new JMenuItem("📂  Carica Dati");
        JMenuItem itemEsporta = new JMenuItem("📋  Esporta CSV");

        for (JMenuItem item : new JMenuItem[]{itemSalva, itemCarica, itemEsporta}) {
            item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            item.setBackground(BG_CARD);
            item.setForeground(TEXT_PRIMARY);
        }

        itemSalva.addActionListener(e -> salvaSuFile());
        itemCarica.addActionListener(e -> caricaDaFile(true));
        itemEsporta.addActionListener(e -> esportaCSV());

        menuFile.add(itemSalva);
        menuFile.add(itemCarica);
        menuFile.addSeparator();
        menuFile.add(itemEsporta);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  LOGICA
    // ══════════════════════════════════════════════════════════════════════════
    private Player creaGiocatoreDalForm() {
        Player p = new Player();
        p.setName(txtName.getText().trim());
        p.setSurname(txtSurname.getText().trim());
        p.setDateBirth(txtDateBirth.getText().trim());
        p.setGender((Gender) cbGender.getSelectedItem());
        p.setNumberOfTshirt((Integer) spinShirtNumber.getValue());
        p.setPosition((Position) cbPosition.getSelectedItem());
        p.setNationality((Nationality) cbNationality.getSelectedItem());
        p.setCaptain(chkCaptain.isSelected());
        return p;
    }

    private void caricaDatiSelezionati() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return;
        int modelRow = table.convertRowIndexToModel(viewRow);
        Player p = giocatori.get(modelRow);

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
        tableModel.setRowCount(0);
        for (Player p : giocatori) {
            tableModel.addRow(new Object[]{
                    p.getName(), p.getSurname(), p.getDateBirth(),
                    p.getGender().name(), p.getNumberOfTshirt(),
                    p.getPosition().name(), p.getNationality().name(),
                    p.isCaptain() ? "Sì" : "No", p.generateTaxCode()
            });
        }
    }

    private void aggiornaStats() {
        long capitani = giocatori.stream().filter(Player::isCaptain).count();
        long naz = giocatori.stream()
                .map(p -> p.getNationality().name()).distinct().count();

        String capNome = giocatori.stream()
                .filter(Player::isCaptain)
                .map(p -> p.getName())
                .findFirst().orElse("—");

        aggiornaChip(lblTotale,   "Giocatori", String.valueOf(giocatori.size()), ACCENT);
        aggiornaChip(lblCap,      "Capitano",   capNome,                          WARNING);
        aggiornaChip(lblNazLabel, "Naz.",        String.valueOf(naz),              SUCCESS);
    }

    private void aggiornaChip(JLabel chip, String label, String val, Color accent) {
        chip.setText(label + "  " + val);
        chip.setForeground(accent);
        chip.repaint();
    }

    private void filtraTabella() {
        TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
        if (sorter == null) return;
        String testo = txtSearch.getText().trim();
        if (testo.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + testo));
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
        setStatus("Campi puliti.", TEXT_MUTED);
    }

    private void setStatus(String msg, Color c) {
        lblStatus.setText("● " + msg);
        lblStatus.setForeground(c);
    }

    private void mostraErrore(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SERIALIZZAZIONE
    // ══════════════════════════════════════════════════════════════════════════
    private void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(giocatori);
            setStatus("Dati salvati in " + FILE_NAME, SUCCESS);
        } catch (IOException e) {
            mostraErrore("Errore salvataggio: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void caricaDaFile(boolean mostraMessaggio) {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            giocatori = (List<Player>) ois.readObject();
            aggiornaTabella();
            aggiornaStats();
            if (mostraMessaggio) setStatus("Dati caricati da " + FILE_NAME, SUCCESS);
        } catch (IOException | ClassNotFoundException e) {
            mostraErrore("Errore caricamento: " + e.getMessage());
        }
    }

    private void esportaCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("giocatori.csv"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter pw = new PrintWriter(fc.getSelectedFile())) {
            pw.println("Nome,Cognome,Nascita,Sesso,Maglia,Posizione,Nazionalità,Capitano,CF");
            for (Player p : giocatori)
                pw.printf("%s,%s,%s,%s,%d,%s,%s,%s,%s%n",
                        p.getName(), p.getSurname(), p.getDateBirth(),
                        p.getGender(), p.getNumberOfTshirt(),
                        p.getPosition(), p.getNationality(),
                        p.isCaptain() ? "Sì" : "No", p.generateTaxCode());
            setStatus("CSV esportato: " + fc.getSelectedFile().getName(), SUCCESS);
        } catch (IOException e) {
            mostraErrore("Errore esportazione CSV: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FACTORY BOTTONI
    // ══════════════════════════════════════════════════════════════════════════
    private JButton creaBottone(String testo, Color bg, Color fg) {
        JButton btn = new JButton(testo) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isRollover()
                        ? bg.brighter()
                        : getModel().isPressed() ? bg.darker() : bg;
                g2.setColor(base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(0, 36));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton creaBottoneFlat(String testo) {
        JButton btn = new JButton(testo) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isRollover()
                        ? new Color(0x30, 0x40, 0x55)
                        : BG_CARD2;
                g2.setColor(base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(new Color(0x35, 0x45, 0x55));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(TEXT_MUTED);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setPreferredSize(new Dimension(0, 32));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void stilizzaSpinner(JSpinner sp) {
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x35, 0x45, 0x55), 1),
                BorderFactory.createEmptyBorder(3, 6, 3, 6)));
        JComponent editor = sp.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(BG_CARD2);
            tf.setForeground(TEXT_PRIMARY);
            tf.setCaretColor(ACCENT);
            tf.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerDatabaseGUI().setVisible(true));
    }
}