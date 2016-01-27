package tb_installerapp.heigvd.tb.installerapp.utils;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anthony on 27.01.2016.
 */
public class GetAllInfoSpinner extends CustomHttpRequest{
    private ArrayAdapter<String> spinnerAdapter;
    private HashMap<Integer, String> map;
    private String standInfoKey;
    private Spinner info;

    public GetAllInfoSpinner(ArrayAdapter<String> spinnerAdapter, HashMap<Integer, String> map, String standInfoKey, Spinner info) {
        this.spinnerAdapter = spinnerAdapter;
        this.map = map;
        this.standInfoKey = standInfoKey;
        this.info = info;
    }

    @Override
    protected String doInBackground(String... uri) {
        return super.doInBackground(uri);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        map.clear();
        spinnerAdapter.clear();
        spinnerAdapter.add("Créer information");

        try {
            JSONObject jObject = new JSONObject(result);

            JSONArray jArray = jObject.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++){
                JSONObject row = jArray.getJSONObject(i);

                String titreInfo = row.getString("title");
                String infoKey = row.getString("id");
                spinnerAdapter.add(titreInfo);
                map.put(i+1, infoKey);
            }

            spinnerAdapter.notifyDataSetChanged();
            for(Map.Entry<Integer, String> entry : map.entrySet()){
                if(entry.getValue().equals(standInfoKey))
                    info.setSelection(entry.getKey());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
