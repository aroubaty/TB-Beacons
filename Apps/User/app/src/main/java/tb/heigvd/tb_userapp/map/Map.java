package tb.heigvd.tb_userapp.map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import tb.heigvd.tb_userapp.AppConfig;
import tb.heigvd.tb_userapp.InfoActivity;
import tb.heigvd.tb_userapp.MainActivity;
import tb.heigvd.tb_userapp.entity.Stand;
import tb.heigvd.tb_userapp.entity.EntityManager;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by anthony on 30.11.2015.
 */
public class Map {
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Resources resources;
    private Bitmap mapNonMutable;
    private MainActivity mainActivity;
    private String destination;
    private String[] beaconProximity;

    private float lastScale;
    private Matrix m;

    //Manager
    public EntityManager entityManager;

    public Map(ImageView mapImage, MainActivity activity){
        mImageView = mapImage;
        this.resources = activity.getResources();
        mainActivity = activity;
        destination = "";
        beaconProximity = new String[]{};

        //On récupère l'image de la mémoire
        mapNonMutable = BitmapFactory.decodeResource(resources,
                AppConfig.MAP);

        //on récupère la vrai taille
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //Returns null, sizes are in the options variable
        BitmapFactory.decodeResource(resources, AppConfig.MAP, options);
        int realWidth = options.outWidth;
        //int realHeight = options.outHeight;

        entityManager = EntityManager.createInstance(this, mainActivity, mapNonMutable.getWidth(), mapNonMutable.getHeight(), (float)mapNonMutable.getWidth() / (float)realWidth);
        update();
    }

    public void update(){
        //on fait une copie que l'on peu modifer
        Bitmap mapMuntable = mapNonMutable.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(mapMuntable);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        //on dessine la position
        for(String idBeacon : beaconProximity){
            Stand toDraw = entityManager.getStand(idBeacon);
            if(toDraw != null) {
                p.setColor(AppConfig.COLOR_POSITION);
                c.drawCircle(toDraw.posX, toDraw.posY, AppConfig.CIRCLE_POSITION_RADIUS, p);
            }
        }

        //on dessine les différents éléments (stand, etc..)
        for(Stand s : entityManager.getStands()){

            if(s.name.equals(destination))
                p.setColor(AppConfig.COLOR_DESTINATION);
            else
                p.setColor(AppConfig.COLOR_DEFAULT);

            c.drawCircle(s.posX, s.posY, AppConfig.CIRCLE_STAND_RADIUS, p);
        }

        //on crée une image
        Drawable d = new BitmapDrawable(resources, mapMuntable);

        //on l'insère dans la vue
        mImageView.setImageDrawable(d);

        //la première fois on instancie le zoom
        if(mAttacher == null) {
            //on active le zoom
            mAttacher = new PhotoViewAttacher(mImageView);
            mAttacher.setOnPhotoTapListener(new MapTapListener());
        }
    }

    public void update(String destination){
        //met à jour la destination
        this.destination = destination;

        update();
    }

    public void update(String[] beaconsProximiy){
        this.beaconProximity = beaconsProximiy;

        update();
    }

    private class MapTapListener implements PhotoViewAttacher.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {
            Stand standTouch = entityManager.touch(x, y);

            if(standTouch !=  null)
                //Toast.makeText(mainActivity.getApplicationContext(), "Touch : " + standTouch.name, Toast.LENGTH_SHORT).show();
                mainActivity.loadInfo(standTouch.infoKey);

        }
    }
}
