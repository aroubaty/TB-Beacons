package tb.heigvd.adminapp;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import tb.heigvd.adminapp.entity.Balise;
import tb.heigvd.adminapp.entity.DBConfig;
import tb.heigvd.adminapp.entity.Stand;
import tb.heigvd.adminapp.entity.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by anthony on 17.12.2015.
 */
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Key keyChambre = Stand.createOrUpdateOrder("noKey", "Chambre", 580, 170, "Anthony", "idInfo", "idCarte");
        Key keySalon = Stand.createOrUpdateOrder("noKey", "Salon", 43, 690, "Anthony", "idInfo", "idCarte");
        Key keyBureau = Stand.createOrUpdateOrder("noKey", "Bureau", 744, 512, "Anthony", "idInfo", "idCarte");

        Balise.createOrUpdateOrder("noKey", "3G0h", keyChambre.getId() + "", 3);
        Balise.createOrUpdateOrder("noKey", "rDd9", keySalon.getId() + "", 5);
        Balise.createOrUpdateOrder("noKey", "ABCD", "free", 7);

        out.println("Data sample Load !");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");


        /*PrintWriter out = response.getWriter();


        String actionValue = request.getParameter("action");

        switch(actionValue){
            case "sample":
                Balise.createOrUpdateOrder("3G0h", 100, 100, "Chambre", 3);
                Balise.createOrUpdateOrder("rDd9", 250, 740, "Salon", 3);

                Stand.createOrUpdateOrder("Chambre", 580, 170, "Anthony", 1);
                Stand.createOrUpdateOrder("Salon", 43, 690, "Anthony", 1);
                Stand.createOrUpdateOrder("Bureau", 744, 512, "Anthony", 1);


                out.println("Data sample Load !");
                break;

            case "getStand":
                response.setContentType("application/json; charset=utf-8");
                response.setHeader("Cache-Control", "no-cache");

                Iterable<Entity> standEntities = Util.listEntities(DBConfig.ENTITY_STAND, null, null);
                out.println(Util.writeJSON(standEntities));
                break;

            case "getBalise":
                response.setContentType("application/json; charset=utf-8");
                response.setHeader("Cache-Control", "no-cache");

                Iterable<Entity> baliseEntities = Util.listEntities(DBConfig.ENTITY_BALISE, null, null);
                out.println(Util.writeJSON(baliseEntities));
                break;

            default:
                out.println("Hello, world");
                break;
        }*/


    }
}
