import java.util.ArrayList;
import java.util.List;

public class Servicio {
    private int idServicio;
    private String tipoServicio;
    private double costo;

    public Servicio(int idServicio, String tipoServicio, double costo) {
        this.idServicio = idServicio;
        this.tipoServicio = tipoServicio;
        this.costo = costo;
    }

    public static List<Servicio> obtenerServicios() {
        List<Servicio> servicios = new ArrayList<>();
        servicios.add(new Servicio(1, "Recolección de Residuos", 50.0));
        servicios.add(new Servicio(2, "Alumbrado Público", 100.0));
        servicios.add(new Servicio(3, "Poda de Árboles", 75.0));
        servicios.add(new Servicio(4, "Reciclaje", 60.0));
        return servicios;
    }
}
