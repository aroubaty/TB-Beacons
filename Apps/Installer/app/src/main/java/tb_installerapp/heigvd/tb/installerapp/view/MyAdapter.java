package tb_installerapp.heigvd.tb.installerapp.view;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.R;
import tb_installerapp.heigvd.tb.installerapp.model.Stand;

/**
 * Created by anthony on 25.09.2015.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Stand> devices;

    public MyAdapter(final Context context) {
        inflater = LayoutInflater.from(context);
        devices = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Stand getItem(int position) {
        return devices.get(position);
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

        //final BeaconDevice beacon = (BeaconDevice) getItem(position);

        /*DistanceManager dm = DistanceManager.getInstance();
        newAlgo na = newAlgo.getInstance();

        na.addRssi(beacon.getUniqueId(), beacon.getRssi());*/

        /*viewHolder.distance.setText(String.format("%s / %s",
                new DecimalFormat("#.##").format(beacon.getDistance()),
                new DecimalFormat("#.##").format(dm.getAverage(beacon.getUniqueId()))));*/
        /*double expo = (beacon.getRssi() - (-59.0)) / (-10.0 * 2.0);
        double dAlgo2 = Math.pow(10, expo);
        Log.e("algo2", "rssi -> " + beacon.getRssi() + "expo -> " + expo + " d -> " + dAlgo2);*/


        /*double d = 0.0;
        for(IBeaconDevice b: devices)
            d += b.getDistance();

        d /= (double)devices.size();
        //d /= 1.9;

        viewHolder.distance.setText(String.format("%s",
                new DecimalFormat("#.##").format(beacon.getDistance())));
        //viewHolder.nbEsti.setText(String.format("Count mesure %s", CountBeacons.getInstance().getCount()));
        viewHolder.nbEsti.setText(String.format("Moyenne %s", d));

        viewHolder.rssi.setText(String.format("Rssi : %f", beacon.getRssi()));
        viewHolder.TX.setText(String.format("Proximity : %s", beacon.getProximity().toString()));
        viewHolder.ID.setText(String.format("ID: %s", beacon.getUniqueId()));*/

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

    public void replaceWith(List<Stand> devices) {
        this.devices.clear();
        this.devices.addAll(devices);
        notifyDataSetChanged();
    }
}
