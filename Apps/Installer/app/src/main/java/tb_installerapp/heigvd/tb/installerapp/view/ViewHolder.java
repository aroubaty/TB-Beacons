package tb_installerapp.heigvd.tb.installerapp.view;


import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tb_installerapp.heigvd.tb.installerapp.R;

/**
 * Created by anthony on 25.09.2015.
 */
public class ViewHolder {

    public TextView standName;
    public TextView proprietaire;
    public ImageButton imageEdit;
    public ImageButton imageDelete;

    public ViewHolder(View view) {
        standName = (TextView) view.findViewById(R.id.standName);
        proprietaire = (TextView) view.findViewById(R.id.proprio);
        imageEdit = (ImageButton) view.findViewById(R.id.imageEdit);
        imageDelete = (ImageButton) view.findViewById(R.id.imageDelete);
    }
}
