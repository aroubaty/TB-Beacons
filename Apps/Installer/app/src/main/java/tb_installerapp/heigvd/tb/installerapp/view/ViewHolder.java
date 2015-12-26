package tb_installerapp.heigvd.tb.installerapp.view;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tb_installerapp.heigvd.tb.installerapp.R;

/**
 * Created by anthony on 25.09.2015.
 */
public class ViewHolder {

    public TextView idBeacon;
    public TextView linkTo;
    public ImageButton imageEdit;
    public ImageButton imageDelete;

    public ViewHolder(View view) {
        idBeacon = (TextView) view.findViewById(R.id.idBeacon);
        linkTo = (TextView) view.findViewById(R.id.linkTo);
        imageEdit = (ImageButton) view.findViewById(R.id.imageEdit);
        imageDelete = (ImageButton) view.findViewById(R.id.imageDelete);
    }
}
