/*
 * Filename     : Querytscore.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : Package model for save query
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */


package model;

import java.sql.SQLException;
/**
 *
 * @author Nabilla Assyfa Ramadhani
 */

public class Querytscore extends DB {
    public Querytscore() throws Exception, SQLException {
        super();
    }

    // Mendapatkan data tscore secara DESC
    public void getDataDESC() {
        try {
            // getting all data from table
            String query = "SELECT * FROM tscore ORDER by score DESC";
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Metode untuk detail data pengguna
    public void getDetailData(String username) {
        try {
            // getting specific data from table
            String query = "SELECT * FROM tscore WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    // Metode Insert data
    public void insertData(String username, int score, int up, int down) {
        try {
            // input data to database
            String query = "INSERT INTO tscore (username, score, up, down) VALUES ('" + username + "', '" + score + "', '"
                    + up + "', '" + down + "')";
            createUpdate(query);
            System.out.println("berhasil add data");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Metode Update Data
    public void updateData(String Username, int Score, int Up, int Down) {
        try {
            // update data
            String query = "UPDATE tscore SET up=" + Up + ", down=" + Down + ", score=" + Score
                    + " WHERE username='" + Username + "'";
            createUpdate(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
