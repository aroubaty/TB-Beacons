package tb.heigvd.adminapp.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.common.base.Flag;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;
import tb.heigvd.adminapp.MainServlet;
import tb.heigvd.adminapp.entity.Balise;
import tb.heigvd.adminapp.entity.DBConfig;
import tb.heigvd.adminapp.entity.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 24.12.2015.
 */
public class BaliseServlet extends MainServlet {
    private static final Logger logger = Logger.getLogger(BaliseServlet.class.getCanonicalName());

    /*
        /balise : crée une balise par rapport au JSON
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

        String id = jobject.get("nom").toString().replace("\"", "");
        String standID = jobject.get("standId").toString().replace("\"", "");
        int puissance = Integer.parseInt(jobject.get("puissance").toString().replace("\"", ""));

        String key = Balise.createOrUpdateOrder(id, standID, puissance).getId() + "";
        out.println(key);
    }

    /*
        /balise : affiche tout
        /balise/byStand/{idStand} : affiche les balises du stand
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        PrintWriter out = response.getWriter();

        //null si on est a la racine
        //sinon prendre le deuxième elements pour le premier param
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            // on est sur /balise
            Iterable<Entity> baliseEntities = Balise.getAllBalises();
            out.println(Util.writeJSON(baliseEntities));
            return;
        }
        if(pathInfo.equals("/")){
            // on est sur /balise/
            Iterable<Entity> baliseEntities = Balise.getAllBalises();
            out.println(Util.writeJSON(baliseEntities));
            return;
        }else{
            String[] pathParts = pathInfo.split("/");

            switch(pathParts[1]){
                case "byStand":
                    Iterable<Entity> baliseEntities = Balise.getBalisesByStandId(pathParts[2]);
                    out.println(Util.writeJSON(baliseEntities));
                    return;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        }
    }

    /*
        /balise/{idBalise} : supprime la balise avec l'ID
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if(!pathInfo.equals("/")){
            String[] pathParts = pathInfo.split("/");
            Key key = KeyFactory.createKey(DBConfig.ENTITY_BALISE, Long.parseLong(pathParts[1]));
            Util.deleteEntity(key);

            out.println("delete done !");
            return ;
        }


        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
