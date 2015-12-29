package tb_installerapp.heigvd.tb.installerapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import tb_installerapp.heigvd.tb.installerapp.map.Map;
import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;
import tb_installerapp.heigvd.tb.installerapp.view.EditAdapter;
import tb_installerapp.heigvd.tb.installerapp.view.MainAdapter;

public class EditActivity extends AppCompatActivity {
    private EditText nom;
    private EditText pro;
    private TextView posX;
    private TextView posY;
    private Spinner addBalise;
    private ListView listBalise;

    private Stand stand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //custom action bar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.action_bar_edit_activity, null);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
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

            }
        });

        Button cancelBtn = (Button)findViewById(R.id.actionCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nom = (EditText) findViewById(R.id.nom);
        pro = (EditText) findViewById(R.id.pro);
        posX = (TextView) findViewById(R.id.posX);
        posY = (TextView) findViewById(R.id.posY);
        addBalise = (Spinner) findViewById(R.id.add_balise);
        listBalise = (ListView) findViewById(R.id.list_balise);

        Intent intent = getIntent();
        String mode = intent.getExtras().getString("mode");

        //Initialise data
        if(mode.equals("edit")){
            stand = intent.getExtras().getParcelable("standParcel");
            nom.setText(stand.StandName);
            pro.setText(stand.proprietaire);
            posX.setText(stand.posX + "");
            posY.setText(stand.posY + "");
        }

        Map map = new Map((ImageView)findViewById(R.id.imageMap), this, posX, posY);

        ListView listView = (ListView) findViewById(R.id.list_balise);
        listView.setAdapter(new EditAdapter(this));

        new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE, "GET");

        //cache le clavier du début
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
