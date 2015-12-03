package tb.heigvd.tb_userapp.entity;

import android.util.Log;

import tb.heigvd.tb_userapp.AppConfig;

/**
 * Created by anthony on 03.12.2015.
 */
public class StandManager {
    private Stand[] stands;
    private int mapWidth;
    private int mapHeight;

    public StandManager(int width, int height){
        mapHeight = height;
        mapWidth = width;

        loadTestData();
        calculateHitbox();
    }

    public Stand[] getStands(){
        return stands;
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
        stands = new Stand[]{
                new Stand("Toilette", 2000, 1500),
                new Stand("Restaurant Chinois", 200, 600),
                new Stand("HEIG place", 1000, 300)
        };
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
