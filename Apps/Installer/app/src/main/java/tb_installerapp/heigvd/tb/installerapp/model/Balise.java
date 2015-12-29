package tb_installerapp.heigvd.tb.installerapp.model;

/**
 * Created by anthony on 29.12.2015.
 */
public class Balise{
    public String baliseKey;
    public String nom;
    public String standKey;
    public int puissance;

    public Balise(String baliseKey, String nom, String standKey, int puissance) {
        this.baliseKey = baliseKey;
        this.nom = nom;
        this.standKey = standKey;
        this.puissance = puissance;
    }
}
