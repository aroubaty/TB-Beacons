package tb_installerapp.heigvd.tb.installerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import tb_installerapp.heigvd.tb.installerapp.map.Map;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;

public class EditActivity extends AppCompatActivity {
    private Stand stand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        String mode = intent.getExtras().getString("mode");
        stand = intent.getExtras().getParcelable("standParcel");

        /*TextView text = (TextView) findViewById(R.id.textView);
        text.setText(stand.StandName + " mode : " + mode);*/

        Map map = new Map((ImageView)findViewById(R.id.imageMap), this);


    }
}
