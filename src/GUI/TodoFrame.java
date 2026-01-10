package GUI;

import CRUD.TodoDAO;
import Model.Todo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TodoFrame extends JFrame {

    JTextField txtTitle, txtPriority, txtDeadline, txtSearch;
    JTable table;
    DefaultTableModel model;
    TodoDAO dao = new TodoDAO();

    public TodoFrame() {
        setTitle("To-Do List App");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10,10));

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitle = new JTextField();
        txtPriority = new JTextField();
        txtDeadline = new JTextField();
        txtSearch = new JTextField();

        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");

        // Judul
        gbc.gridy=0; gbc.gridx=0; gbc.weightx=0; form.add(new JLabel("Judul"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.gridwidth=3; form.add(txtTitle, gbc);

        // Prioritas
        gbc.gridy=1; gbc.gridx=0; gbc.weightx=0; gbc.gridwidth=1; form.add(new JLabel("Prioritas"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.gridwidth=3; form.add(txtPriority, gbc);

        // Deadline
        gbc.gridy=2; gbc.gridx=0; gbc.weightx=0; gbc.gridwidth=1; form.add(new JLabel("Deadline"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.gridwidth=3; form.add(txtDeadline, gbc);

        // Cari
        gbc.gridy=3; gbc.gridx=0; gbc.weightx=0; gbc.gridwidth=1; form.add(new JLabel("Cari"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.gridwidth=3; form.add(txtSearch, gbc);

        // Tombol
        gbc.gridy=4; gbc.gridwidth=1; gbc.weightx=1;
        gbc.gridx=0; form.add(btnAdd, gbc);
        gbc.gridx=1; form.add(btnUpdate, gbc);
        gbc.gridx=2; form.add(btnReset, gbc);
        gbc.gridx=3; form.add(btnDelete, gbc);

        panel.add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"ID","Judul","Prioritas","Deadline"},0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class,null);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel);

        // ===== EVENT =====
        btnAdd.addActionListener(e -> {
            dao.insert(new Todo(
                    txtTitle.getText(),
                    txtPriority.getText(),
                    txtDeadline.getText()
            ));
            loadData("");
            resetForm();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = Integer.parseInt(model.getValueAt(row,0).toString());
                dao.update(new Todo(
                        id,
                        txtTitle.getText(),
                        txtPriority.getText(),
                        txtDeadline.getText()
                ));
                loadData("");
                resetForm();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = Integer.parseInt(model.getValueAt(row,0).toString());
                dao.delete(id);
                loadData("");
                resetForm();
            }
        });

        btnReset.addActionListener(e -> resetForm());

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadData(txtSearch.getText());
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                txtTitle.setText(model.getValueAt(row,1).toString());
                txtPriority.setText(model.getValueAt(row,2).toString());
                txtDeadline.setText(model.getValueAt(row,3).toString());
            }
        });

        loadData("");
    }

    void loadData(String key) {
        model.setRowCount(0);
        for (Todo t : dao.getAll(key)) {
            model.addRow(new Object[]{
                    t.getId(), t.getTitle(), t.getPriority(), t.getDeadline()
            });
        }
    }

    void resetForm() {
        txtTitle.setText("");
        txtPriority.setText("");
        txtDeadline.setText("");
        txtSearch.setText("");
        table.clearSelection();
    }
}
