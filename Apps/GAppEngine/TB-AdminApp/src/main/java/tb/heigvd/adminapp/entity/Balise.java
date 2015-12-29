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

    public static Key createOrUpdateOrder(
            String nom,
            String standId,
            int puissance
    ) throws IOException {
        Key keyLast = null;
        Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
        try {

            Entity balise = new Entity(DBConfig.ENTITY_BALISE);
            balise.setProperty("nom", nom);
            balise.setProperty("standId", standId);
            balise.setProperty("puissance", puissance);
            keyLast = Util.getDatastoreServiceInstance().put(balise);

            txn.commit();

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return keyLast;
    }

    public static Iterable<Entity> getAllBalises() {
        Iterable<Entity> entities = Util.listEntities(DBConfig.ENTITY_BALISE, null, null);
        return entities;
    }

    public static Iterable<Entity> getBalisesFree(){
        Iterable<Entity> entities = Util.listEntities(DBConfig.ENTITY_BALISE, "standId", "free");
        return entities;
    }

    public static Iterable<Entity> getBalisesByStandId(String standId){
        Iterable<Entity> entities = Util.listEntities(DBConfig.ENTITY_BALISE, "standId", standId);
        return entities;
    }
}
