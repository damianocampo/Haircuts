package app.dmn.com.haircuts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by ocamp on 08/09/2016.
 */
public class DialogoConfirmacion extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Mensaje de Confirmacion")
                .setTitle("Titulo del confirmacion")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Se selecciono aceptar",Toast.LENGTH_SHORT).show();

                        //tv_texto = (TextView) getActivity().findViewById()
                    }
                })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Se selecciono cancelar",Toast.LENGTH_SHORT).show();
            }
        });
        return  builder.create();
    }




}
