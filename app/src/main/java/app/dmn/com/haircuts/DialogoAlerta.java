package app.dmn.com.haircuts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ocamp on 07/09/2016.
 */
public class DialogoAlerta extends DialogFragment {

    TextView tv_texto;
    private  String Mensaje;

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    private  String Titulo;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getMensaje())
                .setTitle(getTitulo())
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Se selecciono aceptar",Toast.LENGTH_SHORT).show();

                        //tv_texto = (TextView) getActivity().findViewById()
                    }
                });
        return  builder.create();
    }
}
