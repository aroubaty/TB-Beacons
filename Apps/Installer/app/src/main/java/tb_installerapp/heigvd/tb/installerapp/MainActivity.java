package tb_installerapp.heigvd.tb.installerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;
import tb_installerapp.heigvd.tb.installerapp.view.MainAdapter;
import tb_installerapp.heigvd.tb.installerapp.view.MyAdapter;

public class MainActivity extends AppCompatActivity {
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("mode", "new");
                startActivity(intent);
            }
        });

        mainAdapter = new MainAdapter(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mainAdapter);

        new GetAllStand().execute(AppConfig.URL_GET_ALL_STAND, "GET");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EntityManager.getInstance().setAdapter(mainAdapter);
        new GetAllStand().execute(AppConfig.URL_GET_ALL_STAND, "GET");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {

            return true;
        }*/

        if (id == R.id.refresh_stands) {
            new GetAllStand().execute(AppConfig.URL_GET_ALL_STAND, "GET");
            return true;
        }else if(id == R.id.open_balise){
            Intent intent = new Intent(MainActivity.this, BaliseActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
