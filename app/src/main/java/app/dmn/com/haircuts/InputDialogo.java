package app.dmn.com.haircuts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ocamp on 08/09/2016.
 */
public class InputDialogo extends DialogFragment {

    private String m_Text = "";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setMessage("Mensaje de Confirmación")
                .setTitle("Confirma")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if( getActivity() instanceof MainActivity){
                            ((MainActivity)getActivity()).PagarServicio(m_Text);
                        }
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
