package tb_installerapp.heigvd.tb.installerapp.model;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.AppConfig;
import tb_installerapp.heigvd.tb.installerapp.utils.CustomHttpRequest;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllStand;
import tb_installerapp.heigvd.tb.installerapp.view.MyAdapter;

/**
 * Created by anthony on 26.12.2015.
 */
public class StandManager {
    private static StandManager ourInstance = new StandManager();

    public static StandManager getInstance() {
        return ourInstance;
    }


    private List<Stand> standList;
    private MyAdapter adapter;

    private StandManager() {
        standList = new ArrayList<>();
    }

    public void setAdapter(MyAdapter adapter) {
        this.adapter = adapter;
    }

    public void updateStandList(List<Stand> newList){
        standList = newList;
        adapter.replaceWith(standList);
    }
}
