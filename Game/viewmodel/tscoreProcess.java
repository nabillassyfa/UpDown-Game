/*
 * Filename     : tscoreProcess.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : ViewModel untuk mengelola data user
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */

package viewmodel;

import model.Querytscore;
import model.tscore;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
/**
 *
 * @author Nabilla Assyfa Ramadhani
 */
public class tscoreProcess {
    private String error; // variabel untuk menampung pesan error
    private Querytscore table; // Kelas untuk mengakses query
    private ArrayList<tscore> dataUser; // List untuk menampung hasil

    // Constructor
    public tscoreProcess() {
        try {
            // Inisialisasi tabel dan objek untuk score user
            table = new Querytscore();
            dataUser = new ArrayList<tscore>();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Menampilkan data dari database
    public DefaultTableModel readData() {
        DefaultTableModel data = null;
        try {
            // Menggambil semua data
            Object[] column = { "Username", "Score", "Up", "Down" };
            data = new DefaultTableModel(null, column);
            table.getDataDESC();

            if (table != null && table.getResult().next()) { // Memeriksa apakah ada data yang tersedia di ResultSet
                do {
                    // Mengambil semua hasil
                    tscore scr = new tscore();
                    scr.setUsername(table.getResult().getString(2));
                    scr.setSkor(table.getResult().getInt(3));
                    scr.setUp(table.getResult().getInt(4));
                    scr.setDown(table.getResult().getInt(5));

                    Object[] row = new Object[4];
                    row[0] = scr.getUsername();
                    row[1] = scr.getSkor();
                    row[2] = scr.getUp();
                    row[3] = scr.getDown();

                    // Memasukkan data kedalam list
                    data.addRow(row);
                    dataUser.add(scr);
                } while (table.getResult().next());
            }

            // Menutup hasil
            table.closeResult();

            // menutup koneksi database
            table.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }

        return data;
    }

    // Metode pengecekan user
    public boolean isDataExist(String username) {
        boolean result = false;
        int cek = 1;
        try {
            table.getDataDESC();  // mengambil semua data secara DESC
            while (table.getResult().next() && cek == 1) {
                if (table.getResult().getString(2).equals(username)) {  // kondisi jika user pernah bermian (data berada dalam database)
                    result = true;
                    cek = 0;
                }
            }
        } catch (Exception e) {
            error = e.toString();
        }

        return result;
    }

    // Get Data
    public void getData(String username) {
        try {
            table.getDetailData(username);  // mendapatkan data detail user
            tscore scr = new tscore();
            table.getResult().next();  // mengambil hasil
            scr.setUsername(table.getResult().getString(2));
            scr.setSkor(table.getResult().getInt(3));
            scr.setUp(table.getResult().getInt(4));
            scr.setDown(table.getResult().getInt(5));

            // menambahkan data kedalam list
            dataUser.add(scr);

            // menutup hasil
            table.closeResult();
            // menutup koneksi
            table.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Save Data
    public void saveData(String username, int score, int up, int down) {
        try {
            tscore scr = new tscore();
            scr.setUsername(username);
            scr.setSkor(score);
            scr.setUp(up);
            scr.setDown(down);

            /// KOndisi jika data user ada didalam database
            if (isDataExist(username)) {
                // update data
                table.updateData(scr.getUsername(), scr.getSkor(), scr.getUp(), scr.getDown());
            } else {
                // insert data
                table.insertData(username, score, up, down);
            }
            table.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Metode get data
    public String getUsername(int i) {
        return dataUser.get(i).getUsername();
    }

    public int getSkor(int i) {
        return dataUser.get(i).getSkor();
    }

    public int getUp(int i) {
        return dataUser.get(i).getUp();
    }

    public int getDown(int i) {
        return dataUser.get(i).getDown();
    }

    public int getSize() {
        return dataUser.size();
    }

    public String getError() {
        return this.error;
    }


}
