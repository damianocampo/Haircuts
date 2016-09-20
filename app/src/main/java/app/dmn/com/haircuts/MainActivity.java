package app.dmn.com.haircuts;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CajaFragment.OnFragmentInteractionListener, CarritoFragment.OnFragmentInteractionListener {




    public List<DetalleVenta> detalleVenta;
    CestaAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detalleVenta = new ArrayList<DetalleVenta>();
        CargaAdaptadorCarrito();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cargaBarra();

    }

    public void cargaBarra(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    public void CargaAdaptadorCarrito(){
        adaptador = new CestaAdaptador(this, detalleVenta);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adaptador adapter = new Adaptador(getSupportFragmentManager());
        adapter.addFragment(new CajaFragment(), "Caja");
        adapter.addFragment(new CarritoFragment(), "Carrito "+ "( "+detalleVenta.size()+ " )");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    static class Adaptador extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adaptador(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_exit) {
            Toast.makeText(getApplicationContext(),"Aqui la aplicacion se cerrara",Toast.LENGTH_SHORT).show();
           // return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Toast.makeText(getApplicationContext(),"Perfil", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_servicios) {
            //Toast.makeText(getApplicationContext(),"Servicios",Toast.LENGTH_SHORT).show();
            Intent actividadServicios = new Intent(this,ServiciosActivity.class);
            startActivity(actividadServicios);

        } else if (id == R.id.nav_barberos) {
            Toast.makeText(getApplicationContext(),"Barberos",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_ventas) {
            Toast.makeText(getApplicationContext(),"Ventas",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_feedback) {
            Toast.makeText(getApplicationContext(),"Feedback",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void listaDetalleVenta(String idServicio){
        DetalleVenta dv = new DetalleVenta();
        Servicio se = ObtenerServicio(idServicio);

        dv.setIdServicio(idServicio);
        dv.setCantidad(1);
        dv.setPrecioUnitario(se.getPrecio());
        dv.setDecripcion(se.getNombre());
        dv.setTotalPrecioDetalleVenta(dv.getCantidad() * dv.getPrecioUnitario());

        total = total + dv.getTotalPrecioDetalleVenta();

        detalleVenta.add(dv);
        cargaBarra();
        ListView listaDVenta = (ListView) findViewById(R.id.listDetalleVenta);
        listaDVenta.setAdapter(adaptador);


        TotalPagar = String.valueOf(total);
        TextView tot = (TextView) findViewById(R.id.lblTotal);
        tot.setText(TotalPagar);
    }
    String TotalPagar;
    double total = 0;
    public void SumaTotalPagar(){
        total = 0;
        for(DetalleVenta detail : detalleVenta){
         total = total + detail.getTotalPrecioDetalleVenta();
        }

        TotalPagar = String.valueOf(total);
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

//    public void addToCar(View v){
//
//        Toast.makeText(getApplicationContext(),"que es ¿?",Toast.LENGTH_SHORT).show();
//
//    }

String Cambio ;
    public void PagarServicio(String cantidad){

         double pago = Double.parseDouble(cantidad);
        double toPagar = Double.parseDouble(TotalPagar);
        if(pago > toPagar){
            double cambi = pago - toPagar;
            Cambio = String.valueOf(cambi);
//            FragmentManager fg = this.getSupportFragmentManager();
//                DialogoAlerta dialogo = new DialogoAlerta();
//                    dialogo.setTitulo("Su cambio ");
//                dialogo.setMensaje(Cambio);
//              dialogo.show(fg,"alerta");
            String json = CreaDetalleVentaJSON(detalleVenta);

            FragmentManager fg2 = this.getSupportFragmentManager();
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.setTitulo("Alerta");
            dialogo.setMensaje(json);
            dialogo.show(fg2,"alerta");


        }else{
            FragmentManager fg = this.getSupportFragmentManager();
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.setTitulo("Alerta");
            dialogo.setMensaje("Le falta dinero para pagar el total ");
            dialogo.show(fg,"alerta");
        }



    }


    public void EliminaElementoDetalleVenta(String idServicio){

    List<DetalleVenta> lista = detalleVenta;
        for(int i = 0 ; i < detalleVenta.size(); i++){
            if(detalleVenta.get(i).getIdServicio() == idServicio) {
                detalleVenta.remove(i);
            }
        }

        Toast.makeText(getApplicationContext(),idServicio,Toast.LENGTH_SHORT).show();

    }


    public String CreaDetalleVentaJSON(List<DetalleVenta> detalleV){

        Gson gson = new Gson();
        String jsonLista = gson.toJson(detalleV);

        return  jsonLista;
    }

}
