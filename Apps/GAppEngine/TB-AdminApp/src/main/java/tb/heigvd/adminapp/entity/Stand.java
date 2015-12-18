package tb.heigvd.adminapp.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 18.12.2015.
 */
public class Stand {
    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());

    public static void createOrUpdateOrder(
            String standName,
            int posX,
            int posY,
            String proprietaire,
            int idInformation
    ) throws IOException {
        Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
        try {

            Entity balise = new Entity(DBConfig.ENTITY_STAND);
            balise.setProperty("standName", standName);
            balise.setProperty("posX", posX);
            balise.setProperty("posY", posY);
            balise.setProperty("proprietaire", proprietaire);
            balise.setProperty("keyInformation", "NotDoneYet");
            Util.getDatastoreServiceInstance().put(balise);

            txn.commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

}
