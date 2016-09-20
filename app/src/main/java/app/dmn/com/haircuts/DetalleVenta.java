package app.dmn.com.haircuts;

/**
 * Created by ocamp on 06/09/2016.
 */
public class DetalleVenta {

    private String idDetalleVenta;
    private Integer cantidad;
    private double precioUnitario;
    private double totalPrecioDetalleVenta;
    private String idServicio;
    private String decripcion;

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }



    public DetalleVenta() {
    }

    public String getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(String idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotalPrecioDetalleVenta() {
        return totalPrecioDetalleVenta;
    }

    public void setTotalPrecioDetalleVenta(double totalPrecioDetalleVenta) {
        this.totalPrecioDetalleVenta = totalPrecioDetalleVenta;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }
}
