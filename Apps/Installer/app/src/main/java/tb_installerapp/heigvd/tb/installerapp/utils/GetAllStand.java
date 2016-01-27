package tb_installerapp.heigvd.tb.installerapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.model.Stand;
import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;

/**
 * Created by anthony on 28.12.2015.
 */
public class GetAllStand extends CustomHttpRequest {

    @Override
    protected String doInBackground(String... uri) {
        return super.doInBackground(uri);
    }

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
                int posX = row.getInt("posX");
                int posY = row.getInt("posY");
                String infoKey  = row.getString("idInformation");

                Stand newStand = new Stand(standKey, StandName, proprietaire, posX, posY, infoKey);
                outputList.add(newStand);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        EntityManager.getInstance().updateStandList(outputList);
    }
}
