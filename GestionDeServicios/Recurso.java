public class Recurso {
    private int idRecurso;
    private String tipoRecurso;
    private boolean disponible;

    public Recurso(int idRecurso, String tipoRecurso) {
        this.idRecurso = idRecurso;
        this.tipoRecurso = tipoRecurso;
        this.disponible = true;  // Por defecto el recurso esta disponible
    }

    public boolean isDisponible() { return disponible; }

    public void asignarRecurso() {
        this.disponible = false;
        System.out.println("Recurso asignado.");
    }
}
