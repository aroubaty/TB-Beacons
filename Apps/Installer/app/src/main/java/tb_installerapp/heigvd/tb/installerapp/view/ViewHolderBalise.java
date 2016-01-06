package tb_installerapp.heigvd.tb.installerapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tb_installerapp.heigvd.tb.installerapp.R;

/**
 * Created by anthony on 06.01.2016.
 */
public class ViewHolderBalise {
    public TextView baliseId;
    public TextView pui;
    public ImageButton imageEditBalise;
    public ImageButton imageDeleteBalise;

    public ViewHolderBalise(View view) {
        baliseId = (TextView) view.findViewById(R.id.baliseId);
        pui = (TextView) view.findViewById(R.id.pui);
        imageEditBalise = (ImageButton) view.findViewById(R.id.imageEditBalise);
        imageDeleteBalise = (ImageButton) view.findViewById(R.id.imageDeleteBalise);
    }
}
