package tb_installerapp.heigvd.tb.installerapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tb_installerapp.heigvd.tb.installerapp.R;

/**
 * Created by anthony on 29.12.2015.
 */
public class ViewHolderEdit {
    public TextView nom;
    public TextView puissance;
    public ImageButton delBtn;

    public ViewHolderEdit(View view) {
        nom = (TextView)view.findViewById(R.id.baliseName);
        puissance = (TextView)view.findViewById(R.id.puissance);
        delBtn = (ImageButton)view.findViewById(R.id.imageDelete);
    }
}
