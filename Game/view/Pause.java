/*
 * Filename     : Pause.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : Tampilan Saat mem-pause game
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 *
 * @author Nabilla Assyfa Ramadhani
 */
public class Pause extends JPanel {
    private JButton resumeButton;
    private JButton mainMenuButton;

    public Pause() {
        setLayout(new BorderLayout());

        JLabel pauseLabel = new JLabel("Game Paused", SwingConstants.CENTER);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(pauseLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        resumeButton = new JButton("Resume");
        mainMenuButton = new JButton("Main Menu");

        buttonPanel.add(resumeButton);
        buttonPanel.add(mainMenuButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public void addResumeButtonListener(ActionListener listener) {
        resumeButton.addActionListener(listener);
    }

    public void addMainMenuButtonListener(ActionListener listener) {
        mainMenuButton.addActionListener(listener);
    }
}
