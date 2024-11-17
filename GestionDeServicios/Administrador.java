public class Administrador {
    private int idAdministrador;
    private String nombre;
    private String email;
    private String contrasena;

    public Administrador(int idAdministrador, String nombre, String email, String contrasena) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }
// Asignacion de recursos
    public void asignarRecurso(Solicitud solicitud, Recurso recurso) {
        if (recurso.isDisponible()) {
            recurso.asignarRecurso();
            solicitud.actualizarEstado("En Progreso");
            System.out.println("Recurso asignado a la solicitud.");
        } else {
            System.out.println("No hay recursos disponibles.");
        }
    }

    public void generarReportes() {
        System.out.println("Generando reportes...");
    }
}
