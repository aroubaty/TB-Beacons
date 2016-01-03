package tb.heigvd.adminapp.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;
import tb.heigvd.adminapp.MainServlet;
import tb.heigvd.adminapp.entity.Balise;
import tb.heigvd.adminapp.entity.DBConfig;
import tb.heigvd.adminapp.entity.Stand;
import tb.heigvd.adminapp.entity.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by anthony on 24.12.2015.
 */
public class StandServlet extends MainServlet {
    private static final Logger logger = Logger.getLogger(BaliseServlet.class.getCanonicalName());

    /*
        /stand : crée un stand par rapport au JSON
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        //on lit le "body" de la requête
        BufferedReader br = new BufferedReader(request.getReader());
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = br.readLine();

            if(line != null)
                sb.append(line).append("\n");
        } while (line != null);

        JsonElement jelement = new JsonParser().parse(sb.toString());
        JsonObject jobject = jelement.getAsJsonObject();

        String keyJson = jobject.get("key").toString().replace("\"", "");
        String nom = jobject.get("nom").toString().replace("\"", "");
        int posX = Integer.parseInt(jobject.get("posX").toString().replace("\"", ""));
        int posY = Integer.parseInt(jobject.get("posY").toString().replace("\"", ""));
        String proprietaire = jobject.get("proprietaire").toString().replace("\"", "");
        String idInformation = jobject.get("idInformation").toString().replace("\"", "");
        String idCarte = jobject.get("idCarte").toString().replace("\"", "");

        String key = Stand.createOrUpdateOrder(keyJson, nom, posX, posY, proprietaire, idInformation, idCarte).getId() + "";
        out.println(key);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        PrintWriter out = response.getWriter();

        Iterable<Entity> baliseEntities = Stand.getAllStands();
        out.println(Util.writeJSON(baliseEntities));
    }

    /*
        /stand/{idStand} : supprime le stand avec l'ID
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if(!pathInfo.equals("/")){
            String[] pathParts = pathInfo.split("/");
            Key key = KeyFactory.createKey(DBConfig.ENTITY_STAND, Long.parseLong(pathParts[1]));

            //libère les balises liés
            Iterable<Entity> allBalise = Util.listEntities(DBConfig.ENTITY_BALISE, "standId", pathParts[1]);
            for(Entity e : allBalise){
                e.setProperty("standId", "free");
                Util.persistEntity(e);
            }

            Util.deleteEntity(key);

            out.println("delete done !");
        }


        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
