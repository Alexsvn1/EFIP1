import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/BDD-SEMINARIO"; // URL de la base de datos
    private static final String USUARIO = "root";  // Usuario de MySQL
    private static final String CONTRASENA = "admin123";   // Contraseña de MySQL

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }
}
