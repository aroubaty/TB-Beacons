package tb.heigvd.tb_userapp.entity;

/**
 * Created by anthony on 03.12.2015.
 */
public class Stand {
    public String name;
    public Integer posX;
    public Integer posY;

    //hitbox pour la carte
    float hitboxXMin;
    float hitboxXMax;
    float hitboxYMin;
    float hitboxYMax;

    public Stand(String name, Integer posX, Integer posY) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }
}
