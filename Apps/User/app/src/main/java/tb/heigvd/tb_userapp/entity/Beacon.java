package tb.heigvd.tb_userapp.entity;

/**
 * Created by anthony on 11.12.2015.
 */
public class Beacon {
    public String id;
    public String stand;
    String baliseKey;

    public Beacon(String id, String stand, String baliseKey) {
        this.id = id;
        this.stand = stand;
        this.baliseKey = baliseKey;
    }

}
