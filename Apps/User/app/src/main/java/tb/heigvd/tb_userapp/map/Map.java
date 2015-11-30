package tb.heigvd.tb_userapp.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import tb.heigvd.tb_userapp.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by anthony on 30.11.2015.
 */
public class Map {
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;

    public Map(ImageView mapImage, Resources resources){
        // Mon imageView
        mImageView = mapImage;

        //On récupère l'image de la mémoire
        Bitmap mapNonMutable = BitmapFactory.decodeResource(resources,
                R.drawable.map);

        //on fait une copie que l'on peu modifer
        Bitmap mapMuntable = mapNonMutable.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(mapMuntable);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(10);

        //on dessine une ligne
        c.drawLine(0, 0, 1000, 1000, p);

        //on crée une image
        Drawable d = new BitmapDrawable(resources, mapMuntable);

        //on l'insère dans la vue
        mImageView.setImageDrawable(d);

        //on active le zoom
        mAttacher = new PhotoViewAttacher(mImageView);
    }
}
