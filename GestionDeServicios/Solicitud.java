public class Solicitud {
    // Atributos privados (encapsulados)
    private int idSolicitud;
    private String descripcion;
    private String estado;
    private Ciudadano ciudadano;
    private Servicio servicio;

    public Solicitud(int idSolicitud, String descripcion, Ciudadano ciudadano, Servicio servicio) {
        this.idSolicitud = idSolicitud;
        this.descripcion = descripcion;
        this.ciudadano = ciudadano;
        this.servicio = servicio;
        this.estado = "Pendiente";  // Estado inicial
    }

    public int getIdSolicitud() { return idSolicitud; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }

    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void cerrarSolicitud() {
        this.estado = "Cerrada";
        System.out.println("Solicitud cerrada.");
    }
}
