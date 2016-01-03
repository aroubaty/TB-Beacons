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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anthony on 24.12.2015.
 */
public class BaliseServlet extends MainServlet {
    private static final Logger logger = Logger.getLogger(BaliseServlet.class.getCanonicalName());

    /*
        /balise : crée une balise par rapport au JSON
        /balise/link/{idBalise}/{idStand} : lie une balise à un stand
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            // on est sur /balise
            createBalise(request, response);
            return;
        }
        if(pathInfo.equals("/")){
            // on est sur /balise/
            createBalise(request, response);
            return;
        }else{
            String[] pathParts = pathInfo.split("/");

            switch(pathParts[1]){
                case "link":
                    Balise.linkBalise(pathParts[2], pathParts[3]);
                    out.println("Link done!");
                    return;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        }

    }

    private void createBalise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
        String id = jobject.get("nom").toString().replace("\"", "");
        String standID = jobject.get("standId").toString().replace("\"", "");
        int puissance = Integer.parseInt(jobject.get("puissance").toString().replace("\"", ""));

        String key = Balise.createOrUpdateOrder(keyJson, id, standID, puissance).getId() + "";
        out.println(key);
    }

    /*
        /balise : affiche tout
        /balise/byStand/{idStand} : affiche les balises du stand
        /balise/free : retourne les balises libres
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

                case "free":
                    Iterable<Entity> baliseFreeEntities = Balise.getBalisesFree();
                    out.println(Util.writeJSON(baliseFreeEntities));
                    return;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        }
    }

    /*
        /balise/{idBalise} : supprime la balise avec l'ID
        /balise/unlink/{idBalise} : délie la balise à un stand
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            // on est sur /balise
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if(pathInfo.equals("/")){
            // on est sur /balise/
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }else{
            String[] pathParts = pathInfo.split("/");

            switch(pathParts[1]){
                case "unlink":
                    Balise.unlinkBalise(pathParts[2]);
                    out.println("Unlink done!");
                    return;

                default:
                    Key key = KeyFactory.createKey(DBConfig.ENTITY_BALISE, Long.parseLong(pathParts[1]));

                    //on libère les balises
                    Iterable<Entity> baliseEntities = Balise.getBalisesByStandId(pathParts[1]);
                    for(Entity e : baliseEntities)
                        Balise.unlinkBalise(e.getKey().getId()+"");


                    Util.deleteEntity(key);
                    out.println("delete done !");
                    break;
            }
        }


        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
