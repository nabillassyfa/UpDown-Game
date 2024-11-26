/*
 * Filename     : Player.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : Package model for savedata player
 */

/*
 * Saya Nabilla Assyfa Ramadhani (2205297) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti
 * yang telah dispesifikasikan. Aamiin.
 */

package model;

import java.awt.*;
/**
 *
 * @author Nabilla Assyfa Ramadhani
 */

public class Player extends GameObject{
    int velocityY;  // kecepatana pemain

    // constructor
    public Player (int posX, int posY, int width, int height, Image image){
        super(posX, posY, width, height, image);
        this.velocityY = 0;
    }

    // Metode Get dan Set
    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
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