package app.dmn.com.haircuts;

/**
 * Created by ocamp on 30/08/2016.
 */
public class Servicio {

    private String IdServicio;
    private String Nombre;
    private String Descripcion;
    private double Precio;
    private String IdNegocio;

    public Servicio() {
    }

    public Servicio(String idServicio, String nombre, String descripcion, double precio, String idNegocio) {
        IdServicio = idServicio;
        Nombre = nombre;
        Descripcion = descripcion;
        Precio = precio;
        IdNegocio = idNegocio;
    }


    public String getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(String idServicio) {
        IdServicio = idServicio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public String getIdNegocio() {
        return IdNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        IdNegocio = idNegocio;
    }
}
