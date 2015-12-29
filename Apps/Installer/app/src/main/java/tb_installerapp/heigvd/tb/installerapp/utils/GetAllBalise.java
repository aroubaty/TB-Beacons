package tb_installerapp.heigvd.tb.installerapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.model.Balise;
import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;

/**
 * Created by anthony on 29.12.2015.
 */
public class GetAllBalise extends CustomHttpRequest {

    @Override
    protected String doInBackground(String... uri) {
        return super.doInBackground(uri);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        List<Balise> outputList = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(result);

            JSONArray jArray = jObject.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++){
                JSONObject row = jArray.getJSONObject(i);

                String baliseKey = row.getString("id");
                String baliseName = row.getString("nom");
                String standId = row.getString("standId");
                int puissance = row.getInt("puissance");

                Balise newBalise = new Balise(baliseKey, baliseName, standId, puissance);
                outputList.add(newBalise);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        EntityManager.getInstance().updateBaliseList(outputList);
    }
}
