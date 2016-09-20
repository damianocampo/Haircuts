package app.dmn.com.haircuts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocamp on 26/08/2016.
 */
public class ServicioAdaptador extends RecyclerView.Adapter<ServicioAdaptador.ServicioViewHolder>{

    private List<ServicioCV> servicios;
    private Context miContexto;

//Context miContexto
    public ServicioAdaptador(List<ServicioCV> servicios) {
        this.servicios = servicios;
        //  this.miContexto = miContexto;
    }

    /*
   Añade una lista completa de items
    */
    public void addAll(List<ServicioCV> lista){
        servicios.addAll(lista);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        servicios.clear();
        notifyDataSetChanged();
    }

    @Override
    public ServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_servicios,parent,false);
        return new ServicioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServicioViewHolder servicioViewHolder, int position) {
        ServicioCV servicio = servicios.get(position);
        servicioViewHolder.imgFoto.setImageResource(servicio.getFoto());
        servicioViewHolder.tvNombre.setText(servicio.getNombre());
        servicioViewHolder.setIdServicio(servicio.getIdServicio());
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ServicioViewHolder extends RecyclerView.ViewHolder{
        private Context miContexto;
        private ImageView imgFoto;
        private TextView tvNombre;
        private ImageButton addCar;
        private  String idServicio;

        public String getIdServicio() {
            return idServicio;
        }

        public void setIdServicio(String idServicio) {
            this.idServicio = idServicio;
        }


        public ServicioViewHolder(View itemView) {

            super(itemView);
            miContexto = itemView.getContext();
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombreServicio);

            addCar = (ImageButton) itemView.findViewById(R.id.addToCarro);
            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( miContexto instanceof MainActivity){
                       ((MainActivity)miContexto).listaDetalleVenta(idServicio);
                    }

                }
            });
        }
    }



}
