import java.util.ArrayList;
import java.util.List;

public class Servicio {
    // Arreglo de servicios
    private static String[] servicios = {
            "Recolección de Residuos",
            "Alumbrado Público",
            "Poda de Árboles",
            "Reciclaje"
    };

    public static void listarServicios() {
        System.out.println("Servicios disponibles:");
        for (int i = 0; i < servicios.length; i++) {
            System.out.println((i + 1) + ". " + servicios[i]);
        }
    }

    public static String obtenerServicioPorIndice(int indice) {
        if (indice >= 0 && indice < servicios.length) {
            return servicios[indice];
        }
        return "Servicio no válido";
    }
}

