package tb.heigvd.tb_userapp.entity;

import android.util.Log;

import java.util.LinkedList;

import tb.heigvd.tb_userapp.AppConfig;

/**
 * Created by anthony on 03.12.2015.
 */
public class EntityManager {
    private LinkedList<Stand> stands;
    private LinkedList<Beacon> beacons;
    private int mapWidth;
    private int mapHeight;
    private float ratio;

    public EntityManager(int width, int height, float ratio){
        mapHeight = height;
        mapWidth = width;
        this.ratio = ratio;

        stands = new LinkedList<>();
        beacons = new LinkedList<>();

        loadTestData();
        calculateHitbox();
    }

    public Stand[] getStands(){
        return stands.toArray(new Stand[stands.size()]);
    }

    public Beacon getBeacon(String idBeacon){
        for(Beacon b : beacons){
            if(b.id.equals(idBeacon))
                return b;
        }

        return null;
    }

    public String touch(float x, float y){
        float percentageX = x * 100;
        float percentageY = y * 100;

        for(Stand s : stands){
            if(percentageX >= s.hitboxXMin && percentageX <= s.hitboxXMax){
                if(percentageY >= s.hitboxYMin && percentageY <= s.hitboxYMax)
                    return s.name;
            }
        }

        return "null";
    }

    private void loadTestData(){
        Stand s1 = new Stand("Chambre", (int)(580 * ratio),(int)(170* ratio));
        Stand s2 = new Stand("Salon", (int)(43 * ratio), (int)(690 * ratio));
        Stand s3 = new Stand("Bureau", (int)(744 * ratio), (int)(512 * ratio));
        stands.add(s1);
        stands.add(s2);
        stands.add(s3);

        Beacon b1 = new Beacon("rDd9", s1);
        Beacon b2 = new Beacon("bJxP", s2);
        Beacon b3 = new Beacon("3G0h", s3);
        beacons.add(b1);
        beacons.add(b2);
        beacons.add(b3);
    }

    private void calculateHitbox(){
        double onePercentWidth = 100.0/mapWidth;
        double onePercentHeight = 100.0/mapHeight;

        for(Stand s : stands){
            s.hitboxXMin = (float)((s.posX - AppConfig.CIRCLE_STAND_RADIUS) * onePercentWidth);
            s.hitboxXMax = (float)((s.posX + AppConfig.CIRCLE_STAND_RADIUS) * onePercentWidth);
            s.hitboxYMin = (float)((s.posY - AppConfig.CIRCLE_STAND_RADIUS) * onePercentHeight);
            s.hitboxYMax = (float)((s.posY + AppConfig.CIRCLE_STAND_RADIUS) * onePercentHeight);

            //Log.e("hitbox", s.name + " => x " + s.hitboxXMin + " - " + s.hitboxXMax);
        }
    }
}
