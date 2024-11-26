/*
 * Filename     : GameObject.java
 * Programmer   : Nabilla Assyfa Ramadhani
 * Email        : nabillassyfa@upi.edu
 * Desc         : Package model for savedata game
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
 *
 **/

public class GameObject {
    private int posX;  // Posisi pemain di koordinat X
    private int posY;  // Posisi Pemain di koordinat Y
    private int width; // Lebar object
    private int height; // Tinggi Object
    private Image image;

    // Constructor
    public GameObject(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    // Metode Get dan Set
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setImage (Image image){
        this.image = image;
    }

    public Image getImage (){
        return this.image;
    }
}
