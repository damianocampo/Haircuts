package app.dmn.com.haircuts;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CajaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CajaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView listaServicios;
    private List<ServicioCV> serviciosCV;
    private SwipeRefreshLayout refreshLayout;
    private TextView MensajeEmpty;
    public CajaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.caja_vista, null);
        listaServicios = (RecyclerView) v.findViewById(R.id.rvServicios);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaServicios.setLayoutManager(llm);
        inicializarDatos();

        if(serviciosCV.size() > 0){
            iniciarAdaptador();

        }else {

//            MensajeEmpty = (TextView) v.findViewById(R.id.mensajeEmpty);
//            MensajeEmpty.setText("Aún no haz agregado los servicios que ofreces, agregalos en la sección de 'Servicios' del menu. ");
        }

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);

        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );




        return v;

    }

    public void inicializarDatos(){
        serviciosCV = new ArrayList<>();
//        serviciosCV.add(new ServicioCV("Corte de cabello",R.drawable.barber));
//        serviciosCV.add(new ServicioCV("Corte de barba",R.drawable.barber));
//        serviciosCV.add(new ServicioCV("Corte de ceja",R.drawable.barber));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            String url ="http://62.151.183.107:8080/Haircuts/hc/WSbarber/ListarServicios?idNegocio="+ URLEncoder.encode("2b9bec8f-8558-4ae7-a0f3-b0a3740da220","UTF-8");
            String result;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            result = reader.readLine();


            List<Servicio> Servicios = new ArrayList<Servicio>();
            Gson gson = new Gson();
            Servicios = gson.fromJson(result, new TypeToken<List<Servicio>>(){}.getType());

if(Servicios != null){
    for (Servicio ser: Servicios){
        ServicioCV serCV = new ServicioCV();
        serCV.setIdServicio(ser.getIdServicio());
        serCV.setFoto(R.drawable.barber);
        serCV.setNombre(ser.getNombre());
        serviciosCV.add(serCV);


    }

}else{


}

            //if(Servicios != null){


//            if(result.equals("success")){
//                Toast.makeText(getApplicationContext(),"Se guardo correctamente",Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
//            }


        }catch(Exception e){
            e.printStackTrace();
           // Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();

        }



    }
    public ServicioAdaptador adaptador;
    private void iniciarAdaptador(){
        adaptador = new ServicioAdaptador(serviciosCV);
        listaServicios.setAdapter(adaptador);
    }

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



    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<ServicioCV>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List doInBackground(Void... params) {
            // Simulación de la carga de items
            try {

                inicializarDatos();
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return serviciosCV;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adaptador.clear();

            // Añadir elementos nuevos
            adaptador.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }





}
