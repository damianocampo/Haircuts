package app.dmn.com.haircuts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocamp on 02/09/2016.
 */
public class ServiciosAdaptador extends BaseAdapter {

    protected Activity activity;

    protected List<Servicio> items;

    public ServiciosAdaptador(Activity activity, List<Servicio> items){
        this.activity = activity;
        this.items = items;
    }

    /*
 Añade una lista completa de items
  */
    public void addAll(List<Servicio> lista){
        items.addAll(lista);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.itemservicios, null);
        }
        // Creamos un objeto de la clase Datos
        Servicio s = items.get(position);
        TextView nombreServicio = (TextView) v.findViewById(R.id.txtNombreServicio);
        nombreServicio.setText(s.getNombre());
        TextView precioServicio = (TextView) v.findViewById(R.id.txtPrecio);
        precioServicio.setText(String.valueOf("$"+s.getPrecio()));
        return v;
    }





}
