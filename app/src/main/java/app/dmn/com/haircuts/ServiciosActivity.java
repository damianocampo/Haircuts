package app.dmn.com.haircuts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

public class ServiciosActivity extends AppCompatActivity {
    ServiciosAdaptador adapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        colocarFAB();
        ListView lista = (ListView) findViewById(R.id.listaServicios);
        List<Servicio> Servicios = CargaServicios();
        adapter = new ServiciosAdaptador(this, Servicios);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshServicios);
        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // selected item
                    Servicio service = (Servicio)parent.getItemAtPosition(position);
//                    String selected = ((TextView) view.findViewById(R.id.txtNombreServicio)).getText().toString();
//                    Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
//                    toast.show();
                    Intent actividadServicio = new Intent(getApplicationContext(), ServicioActivity.class);
                    String idServicio = service.getIdServicio();
                    actividadServicio.putExtra("idServicio",idServicio);
                    startActivity(actividadServicio);
                }
            });




    }


    private List<Servicio> CargaServicios(){

        List<Servicio> Servicios = new ArrayList<Servicio>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            String url ="http://62.151.183.107:8080/Haircuts/hc/WSbarber/ListarServicios?idNegocio="+ URLEncoder.encode("2b9bec8f-8558-4ae7-a0f3-b0a3740da220","UTF-8");
            String result;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            result = reader.readLine();
            Gson gson = new Gson();
            Servicios = gson.fromJson(result, new TypeToken<List<Servicio>>(){}.getType());
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                return null;
        }
        return Servicios;

    }


    public void colocarFAB(){

        FloatingActionButton miFAB = (FloatingActionButton)findViewById(R.id.miFAB);
        miFAB.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent actividadServicio = new Intent(v.getContext(), ServicioActivity.class);
                startActivity(actividadServicio);
            }
        });
    }



    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<ServicioCV>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List doInBackground(Void... params) {
            // Simulación de la carga de items
            List<Servicio> servicios = null;
            try {

               servicios = CargaServicios();
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return servicios;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.clear();

            // Añadir elementos nuevos
            adapter.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }


}
