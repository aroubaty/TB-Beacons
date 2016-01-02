package tb_installerapp.heigvd.tb.installerapp.utils;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anthony on 02.01.2016.
 */
public class GetAllFreeBaliseSpinner extends CustomHttpRequest{
    private ArrayAdapter<String> spinnerAdapter;
    private HashMap<Integer, String> map;

    public GetAllFreeBaliseSpinner(ArrayAdapter<String> spinnerAdapter, HashMap<Integer, String> map) {
        this.spinnerAdapter = spinnerAdapter;
        this.map = map;
    }

    @Override
    protected String doInBackground(String... uri) {
        return super.doInBackground(uri);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        spinnerAdapter.clear();
        spinnerAdapter.add("Nouvelle balise...");

        try {
            JSONObject jObject = new JSONObject(result);

            JSONArray jArray = jObject.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++){
                JSONObject row = jArray.getJSONObject(i);

                String baliseName = row.getString("nom");
                String baliseKey = row.getString("id");
                spinnerAdapter.add(baliseName);
                map.put(i+1, baliseKey);
            }

            spinnerAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
