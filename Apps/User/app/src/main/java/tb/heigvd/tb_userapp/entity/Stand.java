package tb.heigvd.tb_userapp.entity;

/**
 * Created by anthony on 03.12.2015.
 */
public class Stand {
    public String name;
    public String standKey;
    public String proprietaire;
    public int posX;
    public int posY;

    //hitbox pour la carte
    float hitboxXMin;
    float hitboxXMax;
    float hitboxYMin;
    float hitboxYMax;

    public Stand(String name, String standKey, String proprietaire, int posX, int posY) {
        this.name = name;
        this.standKey = standKey;
        this.proprietaire = proprietaire;
        this.posX = posX;
        this.posY = posY;
    }
}
