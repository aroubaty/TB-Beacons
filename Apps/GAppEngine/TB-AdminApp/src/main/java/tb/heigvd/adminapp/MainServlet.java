package tb.heigvd.adminapp;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import tb.heigvd.adminapp.entity.*;

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
    /*
    charge les données de test
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Key keyInfo = Information.createOrUpdateOrder("noKey", "Ma Maison",
                "http://www.maisons-delacour.com/photos/diapo/maison-traditionnelle-manche-6074.jpg",
                "Voilà ma maison elle est super jolie ! Voilà ma maison elle est super jolie !Voilà ma maison elle est super jolie !Voilà ma maison elle est super jolie !Voilà ma maison elle est super jolie !");

        Key keyChambre = Stand.createOrUpdateOrder("noKey", "Chambre", 580, 170, "Anthony", keyInfo.getId()+"", "idCarte");
        Key keySalon = Stand.createOrUpdateOrder("noKey", "Salon", 43, 690, "Anthony", keyInfo.getId()+"", "idCarte");
        Key keyBureau = Stand.createOrUpdateOrder("noKey", "Bureau", 744, 512, "Anthony", keyInfo.getId()+"", "idCarte");

        Balise.createOrUpdateOrder("noKey", "3G0h", keyChambre.getId() + "", 3);
        Balise.createOrUpdateOrder("noKey", "rDd9", keySalon.getId() + "", 5);
        Balise.createOrUpdateOrder("noKey", "ABCD", "free", 7);

        out.println("Data sample Load !");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
    }
}
