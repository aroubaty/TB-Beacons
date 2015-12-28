package tb_installerapp.heigvd.tb.installerapp.view;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.EditActivity;
import tb_installerapp.heigvd.tb.installerapp.MainActivity;
import tb_installerapp.heigvd.tb.installerapp.R;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;
import tb_installerapp.heigvd.tb.installerapp.model.StandManager;

/**
 * Created by anthony on 25.09.2015.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Stand> stands;
    private Activity mainActivity;

    public MyAdapter(Activity mainActivity) {
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity.getApplicationContext());
        stands = new ArrayList<>();
        StandManager.getInstance().setAdapter(this);
    }

    @Override
    public int getCount() {
        return stands.size();
    }

    @Override
    public Stand getItem(int position) {
        return stands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getTheSameOrInflate(convertView, parent);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        final Stand stand = (Stand) getItem(position);

        viewHolder.standName.setTextColor(Color.BLACK);
        viewHolder.standName.setText(stand.StandName);

        viewHolder.proprietaire.setTextColor(Color.BLACK);
        viewHolder.proprietaire.setText(stand.proprietaire);




        viewHolder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, EditActivity.class);
                intent.putExtra("standParcel", stand);
                intent.putExtra("mode", "edit");
                mainActivity.startActivity(intent);
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
        View view = inflater.inflate(R.layout.list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    public void replaceWith(List<Stand> stands) {
        this.stands.clear();
        this.stands.addAll(stands);
        notifyDataSetChanged();
    }
}
