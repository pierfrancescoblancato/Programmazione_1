import exceptionsCustoms.*;
import tasks.*;
import interfaceTask.ToDoListManager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Swing GUI for ToDoList — uses JOptionPane for dialogs.
 * Replaces the empty UiToDoList skeleton.
 */
public class UiToDoList extends JFrame {

    // ── colours ──────────────────────────────────────────────────────────────
    private static final Color BG         = new Color(15,  17,  26);
    private static final Color PANEL_BG   = new Color(24,  27,  42);
    private static final Color CARD_BG    = new Color(32,  36,  56);
    private static final Color ACCENT     = new Color(99, 179, 237);
    private static final Color ACCENT2    = new Color(246,135, 90);
    private static final Color TEXT_PRI   = new Color(226,232,240);
    private static final Color TEXT_SEC   = new Color(113,128,150);
    private static final Color URGENT_CLR = new Color(245, 101, 101);
    private static final Color RECUR_CLR  = new Color(104, 211, 145);
    private static final Color SUCCESS    = new Color(72,  187,120);
    private static final Color BORDER_CLR = new Color(49,  57,  85);

    // ── state ─────────────────────────────────────────────────────────────────
    private final ToDoListManager manager;
    private final TaskManagementFile fileManager = new TaskManagementFile();

    // ── widgets ───────────────────────────────────────────────────────────────
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel statusLabel;
    private JLabel countLabel;

    // ── column indices ────────────────────────────────────────────────────────
    private static final int COL_FAV   = 0;
    private static final int COL_TYPE  = 1;
    private static final int COL_TITLE = 2;
    private static final int COL_DESC  = 3;
    private static final int COL_PRIO  = 4;
    private static final int COL_STAT  = 5;
    private static final int COL_EXTRA = 6;

    // ─────────────────────────────────────────────────────────────────────────
    public UiToDoList(ToDoListManager manager) {
        this.manager = manager;
        buildFrame();
        refreshTable();
    }

    // ── frame setup ───────────────────────────────────────────────────────────
    private void buildFrame() {
        setTitle("✔  ToDoList Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(),  BorderLayout.CENTER);
        add(buildToolbar(), BorderLayout.SOUTH);

        setVisible(true);
    }

    // ── header ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(PANEL_BG);
        p.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_CLR));

        JLabel title = new JLabel("  ✔  ToDo List Manager");
        title.setFont(new Font("Monospaced", Font.BOLD, 20));
        title.setForeground(ACCENT);
        title.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 0));

        countLabel = new JLabel("0 tasks");
        countLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
        countLabel.setForeground(TEXT_SEC);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 22));

        p.add(title,      BorderLayout.WEST);
        p.add(countLabel, BorderLayout.EAST);
        return p;
    }

    // ── table area ────────────────────────────────────────────────────────────
    private JScrollPane buildCenter() {
        String[] cols = {"★", "Type", "Title", "Description", "Priority", "Status", "Extra"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_PRI);
        table.setFont(new Font("Monospaced", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 2));
        table.setSelectionBackground(new Color(60, 70, 110));
        table.setSelectionForeground(TEXT_PRI);

        JTableHeader header = table.getTableHeader();
        header.setBackground(PANEL_BG);
        header.setForeground(ACCENT);
        header.setFont(new Font("Monospaced", Font.BOLD, 13));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_CLR));
        header.setReorderingAllowed(false);

        // column widths
        int[] widths = {30, 80, 150, 220, 80, 100, 180};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // custom cell renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? new Color(60, 70, 110) : (row % 2 == 0 ? CARD_BG : new Color(28, 32, 50)));
                setForeground(colourFor(col, val));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(col == COL_FAV || col == COL_TYPE || col == COL_PRIO || col == COL_STAT
                        ? CENTER : LEFT);
                return this;
            }
        };
        for (int i = 0; i < cols.length; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);

        // double-click → detail dialog
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) showDetailDialog();
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(BG);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        sp.getViewport().setBackground(CARD_BG);
        return sp;
    }

    private Color colourFor(int col, Object val) {
        if (val == null) return TEXT_SEC;
        String s = val.toString();
        return switch (col) {
            case COL_FAV  -> s.equals("★") ? Color.YELLOW : TEXT_SEC;
            case COL_TYPE -> switch (s) {
                case "URGENT"    -> URGENT_CLR;
                case "RECURRING" -> RECUR_CLR;
                default          -> ACCENT;
            };
            case COL_PRIO -> switch (s) {
                case "HIGH"   -> URGENT_CLR;
                case "MEDIUM" -> ACCENT2;
                default       -> SUCCESS;
            };
            case COL_STAT -> switch (s) {
                case "COMPLETED"   -> SUCCESS;
                case "IN_PROGRESS" -> ACCENT2;
                default            -> TEXT_SEC;
            };
            default -> TEXT_PRI;
        };
    }

    // ── toolbar ───────────────────────────────────────────────────────────────
    private JPanel buildToolbar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        p.setBackground(PANEL_BG);
        p.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_CLR));

        p.add(btn("＋  Add Task",      ACCENT,    e -> addTaskDialog()));
        p.add(btn("✎  Edit Status",    ACCENT2,   e -> editStatusDialog()));
        p.add(btn("★  Toggle Fav",     Color.YELLOW, e -> toggleFavDialog()));
        p.add(btn("✖  Remove",         URGENT_CLR, e -> removeDialog()));
        p.add(Box.createHorizontalStrut(20));
        p.add(btn("💾  Save",          SUCCESS,   e -> saveDialog()));
        p.add(btn("📂  Load",          RECUR_CLR, e -> loadDialog()));

        statusLabel = new JLabel("  Ready");
        statusLabel.setFont(new Font("Monospaced", Font.ITALIC, 12));
        statusLabel.setForeground(TEXT_SEC);
        p.add(Box.createHorizontalStrut(10));
        p.add(statusLabel);

        return p;
    }

    private JButton btn(String label, Color fg, ActionListener al) {
        JButton b = new JButton(label);
        b.setFont(new Font("Monospaced", Font.BOLD, 12));
        b.setForeground(fg);
        b.setBackground(CARD_BG);
        b.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(fg.darker(), 1, true),
                BorderFactory.createEmptyBorder(5, 14, 5, 14)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 40)); }
            @Override public void mouseExited (MouseEvent e) { b.setBackground(CARD_BG); }
        });
        b.addActionListener(al);
        return b;
    }

    // ── table refresh ─────────────────────────────────────────────────────────
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Task> tasks = manager.getAllTasks();
        for (Task t : tasks) {
            String type  = t instanceof UrgentTask    ? "URGENT"
                    : t instanceof RecurringTask ? "RECURRING"
                      :                              "TASK";
            String extra = t instanceof UrgentTask    ? "Deadline: " + ((UrgentTask)    t).getDeadline()
                    : t instanceof RecurringTask ? "Freq: "     + ((RecurringTask) t).getFrequency()
                      :                              "";
            tableModel.addRow(new Object[]{
                    t.isFavorites() ? "★" : "☆",
                    type,
                    t.getTitle(),
                    t.getDescription(),
                    t.getPriority().name(),
                    t.getTaskStatus().name(),
                    extra
            });
        }
        countLabel.setText(tasks.size() + " task" + (tasks.size() != 1 ? "s" : ""));
    }

    private void setStatus(String msg) { statusLabel.setText("  " + msg); }

    // ─────────────────────────────────────────────────────────────────────────
    //  DIALOGS  (all use JOptionPane)
    // ─────────────────────────────────────────────────────────────────────────

    /** Adds a new task via a multi-field JOptionPane form. */
    private void addTaskDialog() {
        // type selector
        String[] types = {"Task", "Urgent Task", "Recurring Task"};
        String typeChoice = (String) JOptionPane.showInputDialog(this,
                "Select task type:", "Add Task — Step 1/2",
                JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
        if (typeChoice == null) return;

        // shared fields
        JTextField tfTitle = styledField("");
        JTextField tfDesc  = styledField("");
        String[]   prios   = {"LOW", "MEDIUM", "HIGH"};
        JComboBox<String> cbPrio = styledCombo(prios);
        String[]   stats   = {"PENDING", "IN_PROGRESS", "COMPLETED"};
        JComboBox<String> cbStat = styledCombo(stats);

        // type-specific fields
        JTextField tfExtra = styledField(typeChoice.equals("Urgent Task")
                ? "" + (System.currentTimeMillis() + 86_400_000L)
                : "Every Monday");

        JPanel panel = darkPanel(new GridLayout(0, 2, 6, 8));
        panel.add(darkLabel("Title:"));        panel.add(tfTitle);
        panel.add(darkLabel("Description:"));  panel.add(tfDesc);
        panel.add(darkLabel("Priority:"));     panel.add(cbPrio);
        panel.add(darkLabel("Status:"));       panel.add(cbStat);
        if (typeChoice.equals("Urgent Task"))   { panel.add(darkLabel("Deadline (ms):")); panel.add(tfExtra); }
        if (typeChoice.equals("Recurring Task")){ panel.add(darkLabel("Frequency:"));     panel.add(tfExtra); }

        int r = JOptionPane.showConfirmDialog(this, wrapPanel(panel),
                "Add " + typeChoice, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return;

        try {
            String title = tfTitle.getText().trim();
            String desc  = tfDesc .getText().trim();
            Task.Priority  prio = Task.Priority .valueOf((String) cbPrio.getSelectedItem());
            Task.TaskStatus stat = Task.TaskStatus.valueOf((String) cbStat.getSelectedItem());
            long now = System.currentTimeMillis();

            Task task = switch (typeChoice) {
                case "Urgent Task"    -> new UrgentTask   (title, desc, now, prio, stat, false, Long.parseLong(tfExtra.getText().trim()));
                case "Recurring Task" -> new RecurringTask(title, desc, now, prio, stat, false, tfExtra.getText().trim());
                default               -> new Task         (title, desc, now, prio, stat, false);
            };
            manager.addTask(task);
            refreshTable();
            setStatus("Task \"" + title + "\" added.");
        } catch (DuplicateTaskException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplicate", JOptionPane.WARNING_MESSAGE);
        } catch (InvalidTaskException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Task", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Deadline must be a number (ms).", "Format error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Changes the status of the selected (or named) task. */
    private void editStatusDialog() {
        String title = selectedTitle();
        if (title == null) return;

        String[] statuses = {"PENDING", "IN_PROGRESS", "COMPLETED"};
        String choice = (String) JOptionPane.showInputDialog(this,
                "New status for  \"" + title + "\":",
                "Edit Status", JOptionPane.PLAIN_MESSAGE, null, statuses, statuses[0]);
        if (choice == null) return;

        try {
            manager.updateTaskStatus(title, Task.TaskStatus.valueOf(choice));
            refreshTable();
            setStatus("Status of \"" + title + "\" → " + choice);
        } catch (TaskNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Not found", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Toggles favourite on the selected task. */
    private void toggleFavDialog() {
        String title = selectedTitle();
        if (title == null) return;
        try {
            manager.toggleFavorite(title);
            refreshTable();
            boolean isFav = manager.findTaskByTitle(title).isFavorites();
            setStatus("\"" + title + "\" is now " + (isFav ? "★ favourite." : "not favourite."));
        } catch (TaskNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Not found", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Removes the selected task after confirmation. */
    private void removeDialog() {
        String title = selectedTitle();
        if (title == null) return;
        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove  \"" + title + "\"  permanently?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            manager.removeTaskByTitle(title);
            refreshTable();
            setStatus("\"" + title + "\" removed.");
        } catch (TaskNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Not found", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Saves tasks to file in a background thread. */
    private void saveDialog() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Save all tasks to file?", "Save", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Thread t = new Thread(new FileSaveThread(fileManager,
                (java.util.ArrayList<Task>) manager.getAllTasks()));
        t.start();
        setStatus("Saved to file.");
        JOptionPane.showMessageDialog(this, "Tasks saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Loads tasks from file and adds non-duplicate ones. */
    private void loadDialog() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Load tasks from file?\n(Duplicates will be skipped.)",
                "Load", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        java.util.ArrayList<Task> loaded = fileManager.loadFromFile();
        int added = 0, skipped = 0;
        for (Task t : loaded) {
            try { manager.addTask(t); added++;
            } catch (DuplicateTaskException ignored) { skipped++; }
        }
        refreshTable();
        setStatus("Loaded: " + added + " added, " + skipped + " skipped.");
        JOptionPane.showMessageDialog(this,
                added + " task(s) loaded.\n" + skipped + " duplicate(s) skipped.",
                "Load complete", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Shows a read-only detail dialog for the double-clicked row. */
    private void showDetailDialog() {
        String title = selectedTitle();
        if (title == null) return;
        try {
            Task t = manager.findTaskByTitle(title);
            String info = buildDetailText(t);
            JTextArea ta = new JTextArea(info);
            ta.setFont(new Font("Monospaced", Font.PLAIN, 13));
            ta.setEditable(false);
            ta.setBackground(CARD_BG);
            ta.setForeground(TEXT_PRI);
            ta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JOptionPane.showMessageDialog(this, ta, "Task Details — " + title, JOptionPane.PLAIN_MESSAGE);
        } catch (TaskNotFoundException ignored) {}
    }

    private String buildDetailText(Task t) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title       : ").append(t.getTitle()).append("\n");
        sb.append("Description : ").append(t.getDescription()).append("\n");
        sb.append("Priority    : ").append(t.getPriority()).append("\n");
        sb.append("Status      : ").append(t.getTaskStatus()).append("\n");
        sb.append("Favourite   : ").append(t.isFavorites() ? "★ Yes" : "No").append("\n");
        sb.append("Scheduled   : ").append(t.getScheduledDateTime()).append("\n");
        if (t instanceof UrgentTask ut)
            sb.append("Deadline    : ").append(ut.getDeadline()).append("\n");
        if (t instanceof RecurringTask rt)
            sb.append("Frequency   : ").append(rt.getFrequency()).append("\n");
        return sb.toString();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Returns the title of the currently selected table row, or shows an error. */
    private String selectedTitle() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a task from the list first.",
                    "No selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (String) tableModel.getValueAt(row, COL_TITLE);
    }

    private JTextField styledField(String text) {
        JTextField f = new JTextField(text, 18);
        f.setBackground(CARD_BG);
        f.setForeground(TEXT_PRI);
        f.setCaretColor(ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR), BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        f.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return f;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setBackground(CARD_BG);
        cb.setForeground(TEXT_PRI);
        cb.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return cb;
    }

    private JLabel darkLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_SEC);
        l.setFont(new Font("Monospaced", Font.BOLD, 12));
        return l;
    }

    private JPanel darkPanel(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return p;
    }

    private JPanel wrapPanel(JPanel inner) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(PANEL_BG);
        outer.add(inner, BorderLayout.CENTER);
        return outer;
    }
}