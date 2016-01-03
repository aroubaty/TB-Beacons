package tb_installerapp.heigvd.tb.installerapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tb_installerapp.heigvd.tb.installerapp.map.Map;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;
import tb_installerapp.heigvd.tb.installerapp.utils.CustomHttpRequest;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllFreeBaliseSpinner;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;
import tb_installerapp.heigvd.tb.installerapp.view.EditAdapter;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nom;
    private EditText pro;
    private TextView posX;
    private TextView posY;
    private Spinner addBalise;
    ArrayAdapter<String> spinnerAdapter;
    HashMap<Integer, String> posIdMap;
    private ListView listBalise;

    public Stand stand;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //custom action bar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.action_bar_edit_activity, null);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(
                android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM,
                android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM |
                        android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME
                        | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new android.support.v7.app.ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(R.layout.activity_edit);

        Button saveBtn = (Button)findViewById(R.id.actionSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStand();
            }
        });

        Button cancelBtn = (Button)findViewById(R.id.actionCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ///////////////////////////////////

        //link with ui
        nom = (EditText) findViewById(R.id.nom);
        pro = (EditText) findViewById(R.id.pro);
        posX = (TextView) findViewById(R.id.posX);
        posY = (TextView) findViewById(R.id.posY);
        addBalise = (Spinner) findViewById(R.id.add_balise);
        listBalise = (ListView) findViewById(R.id.list_balise);

        //initialize variable
        Map map = new Map((ImageView)findViewById(R.id.imageMap), this, posX, posY);
        posIdMap = new HashMap<>();
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addBalise.setAdapter(spinnerAdapter);
        addBalise.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        mode = intent.getExtras().getString("mode");

        listBalise.setAdapter(new EditAdapter(this));

        //Initialise data
        if(mode.equals("edit")){
            stand = intent.getExtras().getParcelable("standParcel");
            nom.setText(stand.StandName);
            pro.setText(stand.proprietaire);
            posX.setText(stand.posX + "");
            posY.setText(stand.posY + "");

            //on liste les balises liées au stand
            new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE + "/byStand/" + stand.standKey, "GET");

            //initialisation du spinner
            new GetAllFreeBaliseSpinner(spinnerAdapter, posIdMap).execute(AppConfig.URL_GET_ALL_BALISE + "/free", "GET");
        }else{
            //new mode
            spinnerAdapter.add("Recharger stand ...");
            spinnerAdapter.notifyDataSetChanged();
        }



        //cache le clavier du début
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void saveStand(){
        try {
            //création du Json pour le stand
            JSONObject root = new JSONObject();

            if(stand == null)
                root.put("key", "noKey");
            else
                root.put("key", stand.standKey);

            root.put("nom", nom.getText().toString());
            root.put("proprietaire", pro.getText().toString());
            root.put("posX", posX.getText().toString());
            root.put("posY", posY.getText().toString());
            root.put("idInformation", "NotDoneYet");
            root.put("idCarte", "NotDoneYet");

            Log.w("JsonPost", root.toString());
            new CustomHttpRequest(){
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    Log.w("HTTPResult", result);

                    finish();
                }
            }.execute(AppConfig.URL_GET_ALL_STAND, "POST", root.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position != 0 && mode.equals("edit")) {
            //on lie la nouvelle balise
            new CustomHttpRequest(){
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    Log.w("HTTPResult", result);

                    Snackbar snackbar = Snackbar
                            .make((RelativeLayout)findViewById(R.id.editRootLayout),
                                    "Association de la balise terminée",
                                    Snackbar.LENGTH_LONG);

                    snackbar.show();

                    //actualise le spinner
                    /*new GetAllFreeBaliseSpinner(spinnerAdapter, posIdMap)
                            .execute(AppConfig.URL_GET_ALL_BALISE + "/free", "GET");*/

                    //actualise la liste
                    new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE + "/byStand/" + stand.standKey, "GET");
                }
            }.execute(AppConfig.URL_GET_ALL_BALISE + "/link/" + posIdMap.get(position) + "/" + stand.standKey, "POST");
            Log.e("spinner", position + "   --------  " + id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
