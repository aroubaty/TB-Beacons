package tb_installerapp.heigvd.tb.installerapp.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.AppConfig;
import tb_installerapp.heigvd.tb.installerapp.EditActivity;
import tb_installerapp.heigvd.tb.installerapp.R;
import tb_installerapp.heigvd.tb.installerapp.model.Balise;
import tb_installerapp.heigvd.tb.installerapp.model.EntityManager;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;
import tb_installerapp.heigvd.tb.installerapp.utils.CustomHttpRequest;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;

/**
 * Created by anthony on 29.12.2015.
 */
public class EditAdapter extends MyAdapter{
    private LayoutInflater inflater;
    private List<Balise> baliseList;
    private Activity mainActivity;

    public EditAdapter(Activity mainActivity) {
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity.getApplicationContext());
        baliseList = new ArrayList<>();
        EntityManager.getInstance().setAdapter(this);
    }

    @Override
    public int getCount() {
        return baliseList.size();
    }

    @Override
    public Balise getItem(int position) {
        return baliseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getTheSameOrInflate(convertView, parent);
        final ViewHolderEdit viewHolderEdit = (ViewHolderEdit) convertView.getTag();
        final Balise balise = (Balise) getItem(position);

        viewHolderEdit.nom.setTextColor(Color.BLACK);
        viewHolderEdit.nom.setText(balise.nom);

        viewHolderEdit.puissance.setTextColor(Color.BLACK);
        viewHolderEdit.puissance.setText("tx" + balise.puissance);

        viewHolderEdit.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomHttpRequest(){
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        Log.w("HTTPResult", result);

                        Snackbar snackbar = Snackbar
                                .make((RelativeLayout)mainActivity.findViewById(R.id.editRootLayout),
                                        "Dissociation terminée",
                                        Snackbar.LENGTH_LONG);

                        snackbar.show();

                        //actualise la liste
                        EditActivity editActivity = (EditActivity)(mainActivity);
                        new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE + "/byStand/" + editActivity.stand.standKey, "GET");

                    }
                }.execute(AppConfig.URL_GET_ALL_BALISE + "/unlink/" + balise.baliseKey, "DELETE");
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
        View view = inflater.inflate(R.layout.list_row_editview, parent, false);
        ViewHolderEdit viewHolder = new ViewHolderEdit(view);
        view.setTag(viewHolder);
        return view;
    }

    public void replaceWith(List<?> balises) {
        this.baliseList.clear();
        this.baliseList.addAll((List<Balise>) balises);
        notifyDataSetChanged();
    }
}
