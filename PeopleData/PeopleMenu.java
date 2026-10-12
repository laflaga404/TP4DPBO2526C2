import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    private JPanel mainPanel;
    private JTable personTable;
    private JLabel titleLabel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JTextField emailField;
    private JComboBox<String> pangkatComboBox;
    private JComboBox<String> statusComboBox;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JLabel ID;

    private ArrayList<Person> listPerson;
    private int selectedIndex = -1;

    // Main method
    public static void main(String[] args) {
        // buat object window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize(1000, 400);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // tampilkan window
        menu.setVisible(true);
    }


    // constructor
    public PeopleMenu() {
        // inisialisasi listPerson
        listPerson = new ArrayList<>();

        // isi listPerson
        populateList();

        // isi tabel anggota
        personTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(
                titleLabel.getFont().deriveFont(Font.BOLD, 20f)
        );

        // atur isi combo box pangkat
        String[] pangkatData = {
                "Prada", "Pratu", "Praka",
                "Kopda", "Koptu", "Kopka",
                "Serda", "Sertu", "Serka", "Serma",
                "Pelda", "Peltu",
                "Letda", "Lettu", "Kapten", "Mayor",
                "Letkol", "Kolonel",
                "Brigjen", "Mayjen", "Letjen", "Jenderal"
        };

        pangkatComboBox.setModel(
                new DefaultComboBoxModel<>(pangkatData)
        );

        // atur isi combo box status
        String[] statusData = {
                "Aktif Dinas", "Cuti Dinas"
        };

        statusComboBox.setModel(
                new DefaultComboBoxModel<>(statusData)
        );

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        PeopleMenu.this,
                        "Apakah Anda yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = personTable.getSelectedRow();

                if (selectedIndex == -1) {
                    return;
                }

                // simpan value textfield dan combo box
                String curId = personTable.getModel()
                        .getValueAt(selectedIndex, 1).toString();

                String curNama = personTable.getModel()
                        .getValueAt(selectedIndex, 2).toString();

                String curTahunLahir = personTable.getModel()
                        .getValueAt(selectedIndex, 3).toString();

                String curPangkat = personTable.getModel()
                        .getValueAt(selectedIndex, 4).toString();

                String curEmail = personTable.getModel()
                        .getValueAt(selectedIndex, 5).toString();

                String curStatus = personTable.getModel()
                        .getValueAt(selectedIndex, 6).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                tahunLahirField.setText(curTahunLahir);
                pangkatComboBox.setSelectedItem(curPangkat);
                emailField.setText(curEmail);
                statusComboBox.setSelectedItem(curStatus);

                // ubah button Add menjadi Update
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    // method untuk mengisi data awal
    public void populateList() {
        listPerson.add(new Person(
                "TNI001", "Andi Pratama", 1998,
                "Prada", "andi@email.com", "Aktif Dinas"
        ));

        listPerson.add(new Person(
                "TNI002", "Budi Santoso", 1995,
                "Serda", "budi@email.com", "Aktif Dinas"
        ));

        listPerson.add(new Person(
                "TNI003", "Cahya Putra", 1990,
                "Kapten", "cahya@email.com", "Aktif Dinas"
        ));

        listPerson.add(new Person(
                "TNI004", "Dimas Saputra", 1985,
                "Mayor", "dimas@email.com", "Cuti Dinas"
        ));

        listPerson.add(new Person(
                "TNI005", "Eko Wijaya", 1975,
                "Kolonel", "eko@email.com", "Aktif Dinas"
        ));
    }

    // method untuk mengatur tabel
    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = {
                "No", "ID", "Nama", "Tahun Lahir",
                "Pangkat", "Email", "Status"
        };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        // isi tabel dengan listPerson
        for (int i = 0; i < listPerson.size(); i++) {
            Object[] row = {
                    i + 1,
                    listPerson.get(i).getId(),
                    listPerson.get(i).getNama(),
                    listPerson.get(i).getTahunLahir(),
                    listPerson.get(i).getPangkat(),
                    listPerson.get(i).getEmail(),
                    listPerson.get(i).getStatus()
            };

            tmp.addRow(row);
        }

        return tmp;
    }

    // method untuk menambahkan data
    public void insertData() {
        try {
            // ambil value dari textfield dan combo box
            String id = idField.getText();
            String nama = namaField.getText();
            int tahunLahir = Integer.parseInt(tahunLahirField.getText());
            String pangkat = pangkatComboBox.getSelectedItem().toString();
            String email = emailField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            // tambahkan data ke dalam list
            listPerson.add(new Person(
                    id, nama, tahunLahir, pangkat, email, status
            ));

            // update tabel
            personTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil");

            JOptionPane.showMessageDialog(
                    null,
                    "Data berhasil ditambahkan"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tahun lahir harus berupa angka!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // method untuk mengubah data
    public void updateData() {
        try {
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            int tahunLahir = Integer.parseInt(tahunLahirField.getText());
            String pangkat = pangkatComboBox.getSelectedItem().toString();
            String email = emailField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            // ubah data anggota di list
            listPerson.get(selectedIndex).setId(id);
            listPerson.get(selectedIndex).setNama(nama);
            listPerson.get(selectedIndex).setTahunLahir(tahunLahir);
            listPerson.get(selectedIndex).setPangkat(pangkat);
            listPerson.get(selectedIndex).setEmail(email);
            listPerson.get(selectedIndex).setStatus(status);

            // update tabel
            personTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update berhasil");

            JOptionPane.showMessageDialog(
                    null,
                    "Data berhasil diubah"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tahun lahir harus berupa angka!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // method untuk menghapus data
    public void deleteData() {
        // hapus data dari list
        listPerson.remove(selectedIndex);

        // update tabel
        personTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");

        JOptionPane.showMessageDialog(
                null,
                "Data berhasil dihapus"
        );
    }

    // method untuk membersihkan form
    public void clearForm() {
        // kosongkan semua textfield dan combo box
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        emailField.setText("");
        pangkatComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);

        // ubah button Update menjadi Add
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1
        selectedIndex = -1;

        // hilangkan pilihan pada tabel
        personTable.clearSelection();
    }
}
