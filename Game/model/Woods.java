/*
 * Filename     : Woods.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : Package model for savedata woods
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
public class Woods extends GameObject{
    private int velocityX;
    boolean passed = false;
    boolean hasScored = false;
    public Woods (int posX, int posY, int width, int height, Image image){
        super(posX, posY, width, height, image);

        this.velocityX = -3;
        this.passed = false;
        this.hasScored = false;
    }
    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityY(int velocityX) {
        this.velocityX = velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    public boolean hasScored() {
        return hasScored;
    }

    public void setHasScored(boolean hasScored) {
        this.hasScored = hasScored;
    }

    // Metode untuk menghitung score
    public int getScore(){
        return (500 - (this.getPosY() + this.getHeight())) * -1;
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
