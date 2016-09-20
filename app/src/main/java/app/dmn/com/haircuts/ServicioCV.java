package app.dmn.com.haircuts;

/**
 * Created by ocamp on 26/08/2016.
 */
public class ServicioCV {

    private String IdServicio;
    private int foto;
    private String nombre;

    public ServicioCV(){}

    public ServicioCV(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    public String getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(String idServicio) {
        IdServicio = idServicio;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
