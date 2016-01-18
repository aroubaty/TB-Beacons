package tb.heigvd.tb_userapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tb.heigvd.tb_userapp.entity.Information;
import tb.heigvd.tb_userapp.entity.Stand;
import tb.heigvd.tb_userapp.network.CustomHttpRequest;
import tb.heigvd.tb_userapp.network.DownloadImage;

public class InfoActivity extends AppCompatActivity {
    private TextView title;
    private TextView description;
    private ImageView imageUrl;

    private Information info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        title = (TextView) findViewById(R.id.info_title);
        description = (TextView) findViewById(R.id.info_desc);
        imageUrl = (ImageView) findViewById(R.id.info_img);

        //on charge les infos depuis l'intent
        String infoKey = getIntent().getExtras().getString("infoKey");

        new CustomHttpRequest(){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jObject = new JSONObject(result);

                    JSONArray jArray = jObject.getJSONArray("data");
                    for (int i = 0; i < jArray.length(); i++){
                        JSONObject row = jArray.getJSONObject(i);

                        String id = row.getString("id");
                        String title = row.getString("title");
                        String description = row.getString("description");
                        String imgUrl = row.getString("imgUrl");

                        info = new Information(id, title, imgUrl, description);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                title.setText(info.title);
                description.setText(info.description);

                //on charge l'image
                new DownloadImage(imageUrl).execute(info.imgUrl);

            }
        }.execute(AppConfig.URL_GET_ALL_INFORMATION + "/" + infoKey, "GET");


    }
}
