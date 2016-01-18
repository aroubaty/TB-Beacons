package tb.heigvd.adminapp.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 18.01.2016.
 */
public class Information {
    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());

    public static Key createOrUpdateOrder(
            String key,
            String title,
            String imgUrl,
            String description
    ) throws IOException {
        Key keyLast = null;
        Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
        try {
            Entity stand;

            if(key.equals("noKey")){
                stand = new Entity(DBConfig.ENTITY_INFORMATION);
            }else{
                Key keyObj = KeyFactory.createKey(DBConfig.ENTITY_INFORMATION, Long.parseLong(key));
                stand = Util.findEntity(keyObj);
            }

            stand.setProperty("title", title);
            stand.setProperty("imgUrl", imgUrl);
            stand.setProperty("description", description);
            keyLast = Util.getDatastoreServiceInstance().put(stand);

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

    public static Entity getInfo(String key){
        Key keyObj = KeyFactory.createKey(DBConfig.ENTITY_INFORMATION, Long.parseLong(key));
        return Util.findEntity(keyObj);
    }
}
