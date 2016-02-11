package tb.heigvd.tb_userapp.network;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by anthony on 28.12.2015.
 */

public class CustomHttpRequest extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        String output = "";
        try{
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                urlConnection.setRequestMethod(uri[1]);


                if(uri.length == 3){
                    //on ajoute un body
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    BufferedOutputStream printout = new BufferedOutputStream(urlConnection.getOutputStream());
                    printout.write(uri[2].getBytes());
                    printout.flush ();
                    printout.close();
                }

                urlConnection.connect();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //convert stream to string
                Scanner s = new Scanner(in).useDelimiter("\\A");
                output =  s.hasNext() ? s.next() : "";
                in.close();



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