package tb.heigvd.tb_userapp.map;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import tb.heigvd.tb_userapp.AppConfig;
import tb.heigvd.tb_userapp.MainActivity;
import tb.heigvd.tb_userapp.R;
import tb.heigvd.tb_userapp.entity.Stand;
import tb.heigvd.tb_userapp.entity.StandManager;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by anthony on 30.11.2015.
 */
public class Map {
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Resources resources;
    private Bitmap mapNonMutable;
    private Activity mainActivity;

    //Manager
    public StandManager standManager;

    public Map(ImageView mapImage, Activity activity){
        mImageView = mapImage;
        this.resources = activity.getResources();
        mainActivity = activity;

        //On récupère l'image de la mémoire
        mapNonMutable = BitmapFactory.decodeResource(resources,
                R.drawable.map);


        standManager = new StandManager(mapNonMutable.getWidth(), mapNonMutable.getHeight());
        update();
    }

    public void update(){
        update("noDestination");
    }

    public void update(String destination){
        //on fait une copie que l'on peu modifer
        Bitmap mapMuntable = mapNonMutable.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(mapMuntable);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);

        //on dessine les différents éléments (stand, etc..)
        for(Stand s : standManager.getStands()){

            if(s.name.equals(destination))
                p.setColor(Color.RED);
            else
                p.setColor(Color.WHITE);

            c.drawCircle(s.posX, s.posY, AppConfig.CIRCLE_STAND_RADIUS, p);
        }


        //on crée une image
        Drawable d = new BitmapDrawable(resources, mapMuntable);

        //on l'insère dans la vue
        mImageView.setImageDrawable(d);

        //on active le zoom
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnPhotoTapListener(new MapTapListener());
    }

    private class MapTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {

            String nameTouch = standManager.touch(x, y);

            if(!nameTouch.equals("null"))
                Toast.makeText(mainActivity.getApplicationContext(), "Touch : " + nameTouch, Toast.LENGTH_SHORT).show();


        }
    }
}
