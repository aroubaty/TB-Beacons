package tb_installerapp.heigvd.tb.installerapp.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.AppConfig;
import tb_installerapp.heigvd.tb.installerapp.EditActivity;
import tb_installerapp.heigvd.tb.installerapp.R;
import tb_installerapp.heigvd.tb.installerapp.model.Balise;
import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;
import tb_installerapp.heigvd.tb.installerapp.utils.CustomHttpRequest;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;

/**
 * Created by anthony on 06.01.2016.
 */
public class BaliseAdapter extends MyAdapter{
    private LayoutInflater inflater;
    private List<Balise> balises;
    private Activity mainActivity;

    public BaliseAdapter(Activity mainActivity) {
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity.getApplicationContext());
        balises = new ArrayList<>();
        EntityManager.getInstance().setAdapter(this);
    }

    @Override
    public int getCount() {
        return balises.size();
    }

    @Override
    public Balise getItem(int position) {
        return balises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getTheSameOrInflate(convertView, parent);
        final ViewHolderBalise viewHolder = (ViewHolderBalise) convertView.getTag();
        final Balise balise = getItem(position);

        viewHolder.baliseId.setTextColor(Color.BLACK);
        viewHolder.baliseId.setText(balise.nom);

        viewHolder.pui.setTextColor(Color.BLACK);
        viewHolder.pui.setText("lié à " + balise.standKey);

        viewHolder.imageEditBalise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment df = DialogBalise.newInstance(balise.baliseKey, balise.nom, balise.puissance+"");
                df.show(mainActivity.getFragmentManager(), "df");
            }
        });

        viewHolder.imageDeleteBalise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomHttpRequest(){
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        Log.w("HTTPResult", result);

                        Snackbar snackbar = Snackbar
                                .make((CoordinatorLayout)mainActivity.findViewById(R.id.baliseRootLayout),
                                        "Suppression terminée",
                                        Snackbar.LENGTH_LONG);

                        snackbar.show();

                        //actualise la liste
                        new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE, "GET");
                    }
                }.execute(AppConfig.URL_GET_ALL_BALISE + "/" + balise.baliseKey, "DELETE");
            }
        });

        return convertView;
    }

    //check if we need to inflate
    public View getTheSameOrInflate(View view, final ViewGroup parent) {
        if (view == null) {
            return inflate(parent);
        }
        return view;
    }

    View inflate(ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_row_baliseview, parent, false);
        ViewHolderBalise viewHolder = new ViewHolderBalise(view);
        view.setTag(viewHolder);
        return view;
    }

    public void replaceWith(List<?> balises) {
        this.balises.clear();
        this.balises.addAll((List<Balise>) balises);
        notifyDataSetChanged();
    }
}
