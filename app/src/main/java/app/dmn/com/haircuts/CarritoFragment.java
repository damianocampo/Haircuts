package app.dmn.com.haircuts;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarritoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CarritoFragment extends Fragment {


    private SwipeRefreshLayout refreshLayout;
    List<DetalleVenta> lista;
    ListView listaDetalleVenta;
    private OnFragmentInteractionListener mListener;

    public CarritoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.carrito_vista, null);

         listaDetalleVenta = (ListView) v.findViewById(R.id.listDetalleVenta);

        Button btnCobrar = (Button) v.findViewById(R.id.btnCobrar);
        btnCobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager()
//                FragmentManager fg = getFragmentManager();
//                DialogoAlerta dialogo = new DialogoAlerta();
//                dialogo.show(fg,"alerta");

//                FragmentManager fg = getFragmentManager();
//                DialogoConfirmacion dialogo = new DialogoConfirmacion();
//               dialogo.show(fg,"alerta");

                FragmentManager fg = getFragmentManager();
                InputDialogo inputDialog = new InputDialogo();
                inputDialog.show(fg,"alerta");


            }
        });

//                    Toast.makeText(getContext(),"jaja " + dv.getPrecioUnitario(),Toast.LENGTH_SHORT).show();



        return v ;

    }


    public CestaAdaptador adaptador;
    private void iniciarAdaptador(){
        adaptador = new CestaAdaptador(getActivity(),lista);
        listaDetalleVenta.setAdapter(adaptador);

    }

    public void Alerta(View v){

    }

    public void Cobrar(){}

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
