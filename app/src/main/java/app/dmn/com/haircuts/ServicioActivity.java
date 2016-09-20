package app.dmn.com.haircuts;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.UUID;

public class ServicioActivity extends AppCompatActivity {

    EditText NombreServicio;
    EditText Descripcion;
    EditText Precio;
    String idServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NombreServicio = ((EditText) findViewById(R.id.txtServicio));
        Descripcion = ((EditText) findViewById(R.id.txtDescripcion));
        Precio = (EditText) findViewById(R.id.txtPrecio);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
             idServicio = extras.getString("idServicio");
            EditarServicio(idServicio);
        }else{

            NuevoServicio();
        }



    }

    private void NuevoServicio(){
        Button btnGuardarServicio = (Button) findViewById(R.id.btnGuardaServicio);
        btnGuardarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String nombre = NombreServicio.getText().toString();
                final String descripcion = Descripcion.getText().toString();
                final String pre = Precio.getText().toString();
                if( !(nombre.equals("") && descripcion.equals("") && pre.equals(""))  ){

                    final double precio = Double.parseDouble ( pre );
                    Servicio s = new Servicio();
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString = uuid.toString();
                    s.setIdServicio(randomUUIDString);
                    s.setNombre(nombre);
                    s.setDescripcion(descripcion);
                    s.setPrecio(precio);
                    s.setIdNegocio("2b9bec8f-8558-4ae7-a0f3-b0a3740da220");
                    Gson gson = new Gson();
                    String jsonServicio = gson.toJson(s);

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try{
                        String url ="http://62.151.183.107:8080/Haircuts/hc/WSbarber/GuardarServicio?Servicio="+ URLEncoder.encode(jsonServicio,"UTF-8");
                        String result;
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpResponse response = httpclient.execute(new HttpGet(url));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                        result = reader.readLine();
                        if(result.equals("success")){
                            Toast.makeText(getApplicationContext(),"Se guardo correctamente",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                        }

                        NombreServicio.setText("");
                        Descripcion.setText("");
                        Precio.setText("");

                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();

                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Hay un campo vacio !!",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void EditarServicio(String idServicio){


        Servicio service = ObtenerServicio(idServicio);
        NombreServicio.setText(service.getNombre());
        Descripcion.setText(service.getDescripcion());
        Precio.setText(String.valueOf(service.getPrecio()));


        Button btnGuardarServicio = (Button) findViewById(R.id.btnGuardaServicio);
        btnGuardarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Accion no programada aun .. aguanta xD !!",Toast.LENGTH_SHORT).show();
            }
        });




    }

    private String EliminarServicio(String idServicio){

        String mensaje ="";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            String url ="http://62.151.183.107:8080/Haircuts/hc/WSbarber/EliminaServicio?Servicio="+ URLEncoder.encode(idServicio,"UTF-8");
            String result;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            result = reader.readLine();
            if(result.equals("success")){
                mensaje = "Se borro el servicio con exito";
            }else{
                mensaje = "Ocurrio un error";
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();

        }
        return mensaje;

    }

    private Servicio ObtenerServicio(String idServicio){
        Servicio se = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            String url ="http://62.151.183.107:8080/Haircuts/hc/WSbarber/ObtenerServicio?idServicio="+ URLEncoder.encode(idServicio,"UTF-8");
            String result;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            result = reader.readLine();


            Gson gson = new Gson();
             se = gson.fromJson(result, Servicio.class);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();

        }
        return se;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option_principal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Borrar) {
            String mensaje = EliminarServicio(idServicio);
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
