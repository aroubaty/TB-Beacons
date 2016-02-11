package tb.heigvd.tb_userapp.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tb.heigvd.tb_userapp.AppConfig;
import tb.heigvd.tb_userapp.MainActivity;
import tb.heigvd.tb_userapp.map.Map;
import tb.heigvd.tb_userapp.network.CustomHttpRequest;

/**
 * Created by anthony on 03.12.2015.
 */
public class EntityManager {
    private static EntityManager instance;
    public static EntityManager createInstance(Map map, MainActivity activity, int width, int height, float ratio){
        instance = new EntityManager(map, activity, width, height, ratio);
        return instance;
    }

    public static EntityManager getInstance(){
        return instance;
    }

    private LinkedList<Stand> stands;
    private LinkedList<Beacon> beacons;
    private int mapWidth;
    private int mapHeight;
    private float ratio;
    private Map map;
    private MainActivity activity;

    private EntityManager(Map map, MainActivity activity, int width, int height, float ratio){
        mapHeight = height;
        mapWidth = width;
        this.ratio = ratio;
        this.map = map;
        this.activity = activity;

        stands = new LinkedList<>();
        beacons = new LinkedList<>();

        updateData();
    }

    public Stand[] getStands(){
        return stands.toArray(new Stand[stands.size()]);
    }

    public Stand getStand(String idBeacon){
        for(Beacon b : beacons){
            if(b.id.equals(idBeacon)){
                String standKey = b.stand;

                for(Stand s : stands){
                    if(s.standKey.equals(standKey))
                        return s;
                }

            }

        }

        return null;
    }

    public Stand touch(float x, float y){
        float percentageX = x * 100;
        float percentageY = y * 100;

        for(Stand s : stands){
            if(percentageX >= s.hitboxXMin && percentageX <= s.hitboxXMax){
                if(percentageY >= s.hitboxYMin && percentageY <= s.hitboxYMax)
                    return s;
            }
        }

        return null;
    }

    public void updateData(){
        //update Stand
        new CustomHttpRequest(){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                List<Stand> outputList = new ArrayList<>();
                try {
                    JSONObject jObject = new JSONObject(result);

                    JSONArray jArray = jObject.getJSONArray("data");
                    for (int i = 0; i < jArray.length(); i++){
                        JSONObject row = jArray.getJSONObject(i);

                        String standKey = row.getString("id");
                        String StandName = row.getString("nom");
                        String proprietaire = row.getString("proprietaire");
                        String infoKey = row.getString("idInformation");
                        int posX = row.getInt("posX");
                        int posY = row.getInt("posY");

                        Stand newStand = new Stand(StandName, standKey, proprietaire, (int)(posX * ratio), (int)(posY * ratio), infoKey);
                        outputList.add(newStand);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                stands.clear();
                stands.addAll(outputList);
                calculateHitbox();
                map.update();
                activity.updateMenu(stands.toArray(new Stand[stands.size()]));
            }

        }.execute(AppConfig.URL_GET_ALL_STAND, "GET");

        //update Balise
        new CustomHttpRequest(){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                List<Beacon> outputList = new ArrayList<>();
                try {
                    JSONObject jObject = new JSONObject(result);

                    JSONArray jArray = jObject.getJSONArray("data");
                    for (int i = 0; i < jArray.length(); i++){
                        JSONObject row = jArray.getJSONObject(i);

                        String baliseKey = row.getString("id");
                        String baliseName = row.getString("nom");
                        String standId = row.getString("standId");
                        int puissance = row.getInt("puissance");

                        Beacon newBalise = new Beacon(baliseName, standId, baliseKey);
                        outputList.add(newBalise);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                beacons.clear();
                beacons.addAll(outputList);
            }

        }.execute(AppConfig.URL_GET_ALL_BALISE, "GET");

    }

    private void calculateHitbox(){
        double onePercentWidth = 100.0/mapWidth;
        double onePercentHeight = 100.0/mapHeight;

        for(Stand s : stands){
            s.hitboxXMin = (float)((s.posX - AppConfig.CIRCLE_STAND_RADIUS) * onePercentWidth);
            s.hitboxXMax = (float)((s.posX + AppConfig.CIRCLE_STAND_RADIUS) * onePercentWidth);
            s.hitboxYMin = (float)((s.posY - AppConfig.CIRCLE_STAND_RADIUS) * onePercentHeight);
            s.hitboxYMax = (float)((s.posY + AppConfig.CIRCLE_STAND_RADIUS) * onePercentHeight);
        }
    }
}
