/*
 * Filename     : Sound.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : ViewModel untuk mengelola musik
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */

package viewmodel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Nabilla Assyfa Ramadhani
 */

public class Sound {

    public Clip playSound(Clip clip, String filename) {
        try {
            // Mengambil music
            AudioInputStream audioInput = AudioSystem
                    .getAudioInputStream(new File("../src/assets/" + filename).getAbsoluteFile());
            clip = AudioSystem.getClip();

            clip.open(audioInput); // Membuka input audio
            clip.start(); // Memulai audio
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        return clip;
    }

    // Metode untuk Stop BGM
    public void stopSound(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    // Metode untuk mendapatkan durasi musik
    public long getSoundDuration(Clip clip) {
        long microseconds = clip.getMicrosecondLength();
        return microseconds / 11000;
    }
}



/*
    Sound
    Background Sound : Good-Fellow By Komiku (https://www.chosic.com/download-audio/25477/)
    Game Over Sound : game over By Leszek_Szary (https://freesound.org/people/Leszek_Szary/sounds/133283/)

    Image
    Background Image: Pinterest
    Panda Image: Canva
    Pipe Image : Canva
    Hooks Image : Canva

    Thank You :)
 */
