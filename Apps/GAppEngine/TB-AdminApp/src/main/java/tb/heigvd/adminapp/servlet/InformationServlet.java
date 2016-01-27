package tb.heigvd.adminapp.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;
import tb.heigvd.adminapp.MainServlet;
import tb.heigvd.adminapp.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by anthony on 18.01.2016.
 */
public class InformationServlet extends MainServlet {

    /*
        /info : crée une info par rapport au JSON
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
        String title = jobject.get("title").toString().replace("\"", "");
        String imgUrl = jobject.get("imgUrl").toString().replace("\"", "");
        String description = jobject.get("description").toString().replace("\"", "");

        String key = Information.createOrUpdateOrder(keyJson, title, imgUrl, description).getId() + "";
        out.println(key);
    }

    /*
        /info : retourne toutes les infos
        /info/{infoID} : retourne l'info en fonction de la clé
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if(pathInfo == null){
            // on est sur /info
            Iterable<Entity> infoEntities = Information.getAllInfos();
            out.println(Util.writeJSON(infoEntities));
            return;
        }
        if(!pathInfo.equals("/")){
            String[] pathParts = pathInfo.split("/");
            Entity info = Information.getInfo(pathParts[1]);

            ArrayList<Entity> iterable = new ArrayList<>();
            iterable.add(info);

            out.println(Util.writeJSON(iterable));
            return ;
        }else {
            // on est sur /info/
            Iterable<Entity> infoEntities = Information.getAllInfos();
            out.println(Util.writeJSON(infoEntities));
            return;
        }

    }
}
