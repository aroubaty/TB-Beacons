package tb_installerapp.heigvd.tb.installerapp.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import tb_installerapp.heigvd.tb.installerapp.R;

/**
 * Created by anthony on 28.12.2015.
 */

public class CustomHttpRequest extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        if(uri.length != 2) {
            Log.e("CustomHTTPRequest", "Wrong number of parameter");
            return "fail";
        }

        String output = "";
        try{
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                urlConnection.setRequestMethod(uri[1]);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //convert stream to string
                Scanner s = new Scanner(in).useDelimiter("\\A");
                output =  s.hasNext() ? s.next() : "";


            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.w("HTTPResult", result);
    }
}