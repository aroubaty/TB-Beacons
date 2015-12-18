package tb.heigvd.adminapp.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 17.12.2015.
 */
public class Balise {
    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());

    public static void createOrUpdateOrder(
            String id,
            int posX,
            int posY,
            String standName,
            int puissance
    ) throws IOException {
        Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
        try {

            Entity balise = new Entity(DBConfig.ENTITY_BALISE);
            balise.setProperty("id", id);
            balise.setProperty("posX", posX);
            balise.setProperty("posY", posY);
            balise.setProperty("standName", "standName");
            balise.setProperty("puissance", puissance);
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

    public static Iterable<Entity> getAllBalises() {
        Iterable<Entity> entities = Util.listEntities(DBConfig.ENTITY_BALISE, null, null);
        return entities;
    }
}
