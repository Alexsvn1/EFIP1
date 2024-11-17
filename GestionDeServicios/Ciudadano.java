public class Ciudadano {
    private int idCiudadano;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;

    public Ciudadano(int idCiudadano, String nombre, String apellido, String email, String contrasena) {
        this.idCiudadano = idCiudadano;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
    }

    public int getIdCiudadano() { return idCiudadano; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }

    public void registrarSolicitud(Solicitud solicitud) {
        System.out.println("Solicitud registrada por: " + this.nombre);
    }

    public void consultarEstadoSolicitud(Solicitud solicitud) {
        System.out.println("Estado de la solicitud: " + solicitud.getEstado());
    }
}
