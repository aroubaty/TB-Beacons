package tb.heigvd.adminapp.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 18.12.2015.
 */
public class Stand {
    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());

    public static Key createOrUpdateOrder(
            String nom,
            int posX,
            int posY,
            String proprietaire,
            String idInformation,
            String idCarte
    ) throws IOException {
        Key keyLast = null;
        Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
        try {

            Entity balise = new Entity(DBConfig.ENTITY_STAND);
            balise.setProperty("nom", nom);
            balise.setProperty("posX", posX);
            balise.setProperty("posY", posY);
            balise.setProperty("proprietaire", proprietaire);
            balise.setProperty("idInformation", "idInformation");
            balise.setProperty("idCarte", "idCarte");
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

    public static Iterable<Entity> getAllStands() {
        Iterable<Entity> entities = Util.listEntities(DBConfig.ENTITY_STAND, null, null);
        return entities;
    }
}
