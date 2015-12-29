package tb_installerapp.heigvd.tb.installerapp.model;

import java.util.ArrayList;
import java.util.List;

import tb_installerapp.heigvd.tb.installerapp.view.MyAdapter;

/**
 * Created by anthony on 26.12.2015.
 */
public class EntityManager {
    private static EntityManager ourInstance = new EntityManager();

    public static EntityManager getInstance() {
        return ourInstance;
    }


    private List<Stand> standList;
    private List<Balise> baliseList;
    private MyAdapter adapter;

    private EntityManager() {
        standList = new ArrayList<>();
        baliseList = new ArrayList<>();
    }

    public void setAdapter(MyAdapter adapter) {
        this.adapter = adapter;
    }

    public void updateStandList(List<Stand> newList){
        standList = newList;
        adapter.replaceWith(standList);
    }

    public void updateBaliseList(List<Balise> newList){
        baliseList = newList;
        adapter.replaceWith(baliseList);
    }
}
