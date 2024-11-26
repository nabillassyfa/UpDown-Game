/*
 * Filename     : UpDown.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : ViewModel untuk mengelola game
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */

package viewmodel;

import model.Hooks;
import model.Player;
import model.Woods;
import view.DaftarUser;
import view.Pause;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Nabilla Assyfa Ramadhani
 */
public class UpDown extends JPanel implements ActionListener, KeyListener {
    // Random keluaran kayu dan kail
    boolean lastkWoodsWasUpper = false;
    private int minGap = 200; // Jarak minimum horizontal antara kayu dan pipa
    private int maxGap = 300; // Jarak maksimum horizontal antara kayu dan pipa
    private Random random = new Random(); // Untuk me random keluarann kayu dan kail
    // Sound
    private Clip clip; // Untuk Menampung background music
    private Clip end; // untuk menampung game over musik
    // Pause
    private Pause pausePanel;
    private boolean isPaused = false;
    // Pergerakan player
    boolean startMovingPlayer = true;
    // Frame layar
    int frameWidth = 640;
    int frameHeight = 480;

    //image attributes
    Image bakgroundImage;
    Image pandaImage;
    Image WoodsImage;
    Image HooksImage;

    //player
    int playerStartPosX = -2; // Posisi start player pada sumbu X
    int playerStartPosY = frameHeight/2; // Posisi start player pada sumbu Y
    int playerWidth = 50;
    int playerHeight = 50;
    Player player;

    // Timer
    Timer gameLoop;
    Timer woodsCooldown;

    boolean tamat = false;  // Variabel untuk penanda game over


    //woods attributes
    int woodstartPosX = frameWidth;
    int woodstartPosY = 0;
    int kayuWidth = 60;
    int kayuHeight = 300;

    //hooks attributes
    int hookstartPosX = frameWidth;
    int hookWidth = 60;
    int hookHeight = 260;

    // User data
    String username;
    int score = 0;    // variable to store score
    int up = 0;
    int down = 0;

    // List kayu dan kail
    ArrayList<Woods> woods;
    ArrayList<Hooks> kail;
    Woods currentwoods;
    Hooks currentHooks;

    public UpDown() {
        // SET FRAME GAME
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);

        // Memulai background sound
        Sound sound = new Sound();
        clip = sound.playSound(this.clip, "yello2.wav");

        // Insialisasi Image
        bakgroundImage = new ImageIcon(getClass().getResource("/assets/background.jpeg")).getImage();
        pandaImage = new ImageIcon(getClass().getResource("/assets/panda.png")).getImage();
        WoodsImage = new ImageIcon(getClass().getResource("/assets/lowerpipe.png")).getImage();
        HooksImage = new ImageIcon(getClass().getResource("/assets/kail.png")).getImage();

        // Insialisasi Atribut game
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, pandaImage);
        woods = new ArrayList<Woods>();
        kail = new ArrayList<Hooks>();

        // Inisialisasi Pause Panel
        pausePanel = new Pause();
        pausePanel.setVisible(false); // Mulai dengan panel tidak terlihat
        add(pausePanel); // Tambahkan panel ke panel utama
        pausePanel.addResumeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = false;
                pausePanel.setVisible(false);
                gameLoop.start();
            }
        });
        pausePanel.addMainMenuButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sound().stopSound(clip);
                addUser(score, up, down);
                DaftarUser.main(new String[0]);
                // Menutup Game
                Window currentWindow = SwingUtilities.getWindowAncestor(UpDown.this);
                currentWindow.dispose();
            }
        });

        // Waktu untuk kembali me render kayu dan kail
        woodsCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placekayu();
            }
        });
        woodsCooldown.start();

        gameLoop = new Timer(1000 / 50, this); // kecepatan pergerakan kayu dan kail
        gameLoop.start();
    }

    // Metode untuk draw object
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        g.drawImage(bakgroundImage, 0, 0, frameWidth, frameHeight, null); // menambhkan background pada game

        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        // Menampilkan kayu
        for (int i = 0; i < woods.size(); i++) {
            Woods kayu = woods.get(i);
            g.setColor(Color.black);
            g.drawString(String.valueOf((int) kayu.getScore()), kayu.getPosX() + 5, kayu.getPosY());
            // Draw the lower kayu
            g.drawImage(WoodsImage, kayu.getPosX(), kayu.getPosY(), kayu.getWidth(), kayu.getHeight(), null);
        }

        // Menampilkan Kail
        for (int i = 0; i < kail.size(); i++) {
            Hooks hook = kail.get(i);
            g.setColor(Color.black);
            g.drawString(String.valueOf((int) hook.getScore()), hook.getPosX(), hook.getPosY() + hook.getHeight() + 20);
            // Draw the upper kayu (hook)
            g.drawImage(HooksImage, hook.getPosX(), hook.getPosY(), hook.getWidth(), hook.getHeight(), null);
        }

        // Menampilkan poin user
        g.setColor(Color.black);
        g.drawString("Score : " + String.valueOf((int) score), 20, 20);
        g.setColor(Color.black);
        g.drawString("Up: " + String.valueOf((int) up), 20, 50);
        g.setColor(Color.black);
        g.drawString("Down: " + String.valueOf((int) down), 20, 80);
    }

    // Metode untuk pergerakan
    public void move() {
        if (!tamat) {
            if (startMovingPlayer) {
                // Memastikan pemain dimulai dari posisi awal
                if (playerStartPosX < 0) {
                    // Pergerakan pemain dari kiri ke kanan
                    for (int i = 5; i <= 10; i += 5) {
                        // Perbarui posisi pemain dan gambar ulang layar
                        player.setPosX(player.getPosX() + i);
                        repaint();
                        // Tunda sementara untuk memberikan efek pergerakan
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (player.getPosX() >= 50) {
                        startMovingPlayer = false;
                    }
                }
            } else {
                boolean onkayu = false;
                boolean onSwing = false;

                for (int i = 0; i < woods.size(); i++) {
                    Woods kayu = woods.get(i);
                    kayu.setPosX(kayu.getPosX() + kayu.getVelocityX());

                    // Mengecek player Berdiri diatas kayu
                    if (player.getPosX() + player.getWidth() > kayu.getPosX() &&
                            player.getPosX() < kayu.getPosX() + kayu.getWidth() &&
                            player.getPosY() + player.getHeight() >= kayu.getPosY() &&
                            player.getPosY() + player.getHeight() <= kayu.getPosY() + 10 &&
                            player.getVelocityY() >= 0) {

                        player.setVelocityY(0);
                        player.setPosY(kayu.getPosY() - player.getHeight());
                        onkayu = true;
                        currentwoods = kayu;
                    }
                }
                // menambahkan score dan nilai down
                if (onkayu && currentwoods != null && !currentwoods.hasScored()) {
                    score = score + currentwoods.getScore();
                    down += 1;
                    currentwoods.setHasScored(true);
                }

                for (int i = 0; i < kail.size(); i++) {
                    Hooks kayu = kail.get(i);
                    kayu.setPosX(kayu.getPosX() + kayu.getVelocityX());

                    // Mengecek player bergantung diatas kail
                    if (player.getPosX() + player.getWidth() > kayu.getPosX() &&
                            player.getPosX() < kayu.getPosX() + kayu.getWidth() &&
                            player.getPosY() + player.getHeight() >= kayu.getPosY() + kayu.getHeight() &&
                            player.getPosY() <= kayu.getPosY() + kayu.getHeight() &&
                            player.getVelocityY() >= 0) {

                        player.setVelocityY(0);
                        player.setPosY(kayu.getPosY() + kayu.getHeight() - player.getHeight());
                        onSwing = true;
                        currentHooks = kayu;

                        player.setPosX(player.getPosX() + kayu.getVelocityX());
                    }

                    // Menambahkan score dan nilai up
                    if (onSwing && currentHooks != null && !currentHooks.hasScored()) {
                        score = score + currentHooks.getScore();
                        up += 1;
                        currentHooks.setHasScored(true);
                    }
                }

                // kondisi jika player tidak diatas kayu atau kail
                if (!onkayu || !onSwing) {
                    player.setVelocityY(player.getVelocityY() + 1);
                    player.setPosY(player.getPosY() + player.getVelocityY());
                    player.setPosY(Math.max(player.getPosY(), 0));

                }
            }

            // kondisi jika player jatuh atau menabrak sisi kiri layar
            if ((player.getPosX() < 0 || player.getPosX() > frameWidth || player.getPosY() < 0 || player.getPosY() > frameHeight) && !tamat) {
                new Sound().stopSound(this.clip);
                tamat = true;
                addUser(score, up, down);
                gameOver(score, up, down);
            }
        }
    }

    // Metode untuk memasukan poin yang diperoleh user
    public void addUser (int score, int up, int down){
        tscoreProcess prosess = new tscoreProcess();
        prosess.saveData(username, score, up, down);
    }

    // Metode Game Over
    public void gameOver(int score, int up, int down) {
        new Sound().stopSound(this.clip);  // stop background sound

        // memulai game over sound
        Sound sound = new Sound();
        end = sound.playSound(this.end, "end.wav");
        long duration = sound.getSoundDuration(end);

        // Timer untuk menunda eksekusi setelah suara selesai diputar
        Timer timer = new Timer((int) duration, e -> {
            // tampilkan total poin
            JOptionPane.showMessageDialog(this, "Total Poin: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            // Stop sound
            new Sound().stopSound(end);

            // Pindah ke tampilan utama setelah pengguna menekan "OK"
            SwingUtilities.invokeLater(() -> {
                DaftarUser.main(new String[0]);
                Window currentWindow = SwingUtilities.getWindowAncestor(UpDown.this);
                if (currentWindow != null) {
                    currentWindow.dispose();
                }
            });
        });
        timer.setRepeats(false);
        timer.start();
    }
    public void placekayu() {
        int randomPosY;
        if (lastkWoodsWasUpper) {
           // menempatkan kayu di bawah
            randomPosY = woods.get(woods.size() - 1).getPosY() + kayuHeight + minGap;
        } else {
            // Menempatkan kail di posisi random
            randomPosY = (int) (woodstartPosY - kayuHeight / 4 - Math.random() * (kayuHeight / 2));
        }

        // Jarak horizontal acak antara kayu dan kail
        int gap = minGap + random.nextInt(maxGap - minGap);

        // Kail
        Hooks upperkayu = new Hooks(hookstartPosX, randomPosY, hookWidth, hookHeight, HooksImage);
        kail.add(upperkayu);

        // kayu
        Woods lowerkayu = new Woods(woodstartPosX + gap, (randomPosY + minGap + kayuHeight), kayuWidth, kayuHeight, WoodsImage);
        woods.add(lowerkayu);

        lastkWoodsWasUpper = !lastkWoodsWasUpper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (tamat) {
            woodsCooldown.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Masukan perintah pada keyboard
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) { // Kondisi untuk menggerakan player keatas
            player.setVelocityY(-20);
            player.setPosX(player.getPosX() + 10);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {// Kondisi untuk menggerakan player kekanan
            player.setPosX(player.getPosX() + 20);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Kondisi untuk menggerakan player kekiri
            player.setPosX(player.getPosX() - 10);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // Kondisi untuk menggerakan player kebawah
            player.setVelocityY(20);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Kondisi untuk mem-pause game
            isPaused = !isPaused;

            if (isPaused) {
                // kondisi Jika sedang di-pause, hentikan timer permainan dan tampilkan panel pause
                gameLoop.stop();
                pausePanel.setVisible(true);
            } else {
                // kondisi Jika sedang tidak di-pause, lanjutkan timer permainan dan sembunyikan panel pause
                gameLoop.start();
                pausePanel.setVisible(false);
            }
        }

        if (tamat) {
            // Reset game jika sudah tamat
            player.setPosY(playerStartPosY);
            player.setVelocityY(0);
            woods.clear();
            tamat = false;
            score = 0;
            gameLoop.start();
            woodsCooldown.start();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Metode Get Set
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSkor() {
        return score;
    }

    public void setSkor(int score) {
        this.score = score;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }
}