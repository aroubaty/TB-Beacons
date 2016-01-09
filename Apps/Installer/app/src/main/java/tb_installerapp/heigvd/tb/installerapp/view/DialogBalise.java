package tb_installerapp.heigvd.tb.installerapp.view;




import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import tb_installerapp.heigvd.tb.installerapp.AppConfig;
import tb_installerapp.heigvd.tb.installerapp.R;
import tb_installerapp.heigvd.tb.installerapp.utils.CustomHttpRequest;
import tb_installerapp.heigvd.tb.installerapp.utils.GetAllBalise;

/**
 * Created by anthony on 09.01.2016.
 */
public class DialogBalise extends DialogFragment {
    private String key;

    private EditText idEdit;
    private EditText puiEdit;


    public static DialogBalise newInstance(String key, String id, String pui) {
        DialogBalise f = new DialogBalise();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("key", key);
        args.putString("id", id);
        args.putString("pui", pui);
        f.setArguments(args);

        return f;
    }

    public static DialogBalise newInstance() {
        DialogBalise f = new DialogBalise();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get param from buddle
        String id = getArguments().getString("id");
        String pui = getArguments().getString("pui");
        key = getArguments().getString("key");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View content = inflater.inflate(R.layout.dialog_balise, null);
        idEdit = (EditText) content.findViewById(R.id.dialog_id);
        puiEdit = (EditText) content.findViewById(R.id.dialog_puissance);

        if(id != null)
            idEdit.setText(id);


        if(pui != null)
            puiEdit.setText(pui);


        builder.setView(content)
                // Add action buttons
                .setPositiveButton("Appliquer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            //création du Json pour le stand
                            JSONObject root = new JSONObject();

                            if (key != null)
                                root.put("key", key);
                            else
                                root.put("key", "noKey");

                            root.put("nom", idEdit.getText().toString());
                            root.put("puissance", puiEdit.getText().toString());
                            root.put("standId", "free");
                            Log.w("JsonPost", root.toString());

                            new CustomHttpRequest() {
                                @Override
                                protected void onPostExecute(String result) {
                                    super.onPostExecute(result);
                                    Log.w("HTTPResult", result);

                                    new GetAllBalise().execute(AppConfig.URL_GET_ALL_BALISE, "GET");
                                    dismiss();
                                }
                            }.execute(AppConfig.URL_GET_ALL_BALISE, "POST", root.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

}
