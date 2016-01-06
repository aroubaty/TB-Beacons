package tb_installerapp.heigvd.tb.installerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;
import tb_installerapp.heigvd.tb.installerapp.view.BaliseAdapter;

public class BaliseActivity extends AppCompatActivity {
    private BaliseAdapter baliseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo new balise
            }
        });

        baliseAdapter = new BaliseAdapter(this);
        ListView listView = (ListView)findViewById(R.id.listViewBalise);
        listView.setAdapter(baliseAdapter);

        new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE, "GET");
    }

}
