package app.dmn.com.haircuts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ocamp on 07/09/2016.
 */
public class CestaAdaptador extends BaseAdapter {

    protected Activity activity;


    protected List<DetalleVenta> items;

    public CestaAdaptador(Activity activity,List<DetalleVenta> items){

        this.activity = activity;
        this.items = items;
    }

    public void addAll(List<DetalleVenta> items){
        items.addAll(items);
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
            v = inf.inflate(R.layout.item_cesta, null);
        }
        // Creamos un objeto de la clase Datos
        DetalleVenta dv = items.get(position);
        final String idServicio = dv.getIdServicio();
        TextView desc_cesta = (TextView) v.findViewById(R.id.descripcionCesta);
        desc_cesta.setText(dv.getDecripcion());
        TextView precio_Cesta = (TextView) v.findViewById(R.id.precioCesta);
        String pre = String.valueOf(dv.getTotalPrecioDetalleVenta());
        precio_Cesta.setText(pre);
        ImageButton deleteService = (ImageButton) v.findViewById(R.id.imagenTacha);

        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( activity instanceof MainActivity){
                    ((MainActivity)activity).EliminaElementoDetalleVenta(idServicio);
                }
            }
        });

        return v;
    }








}
