package tb_installerapp.heigvd.tb.installerapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 26.12.2015.
 */
public class StandManager {
    private static StandManager ourInstance = new StandManager();

    public static StandManager getInstance() {
        return ourInstance;
    }


    private List<Stand> standList;

    private StandManager() {
        standList = new ArrayList<>();


    }


    public void updateStandList(){

    }
}
