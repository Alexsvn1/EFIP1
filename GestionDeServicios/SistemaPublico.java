import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SistemaPublico {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("---- Sistema de Gestión de Servicios Públicos ----");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Menu Principal

            switch (opcion) {
                case 1:
                    iniciarSesion(scanner);
                    break;
                case 2:
                    registrarUsuario(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    // Metodo para iniciar sesión desde la base de datos
    public static void iniciarSesion(Scanner scanner) {
        System.out.println("---- Iniciar Sesión ----");

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        boolean loginExitoso = false;

        // Conexión con la base de datos
        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        // Verificar si el usuario es un administrador
        String sqlAdmin = "SELECT * FROM Administrador WHERE email = ? AND contrasena = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sqlAdmin)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("Inicio de sesión exitoso como Administrador, bienvenido " + rs.getString("nombre"));
                loginExitoso = true;
                mostrarMenuAdmin(scanner);  // Llamar al menú de Administrador
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar iniciar sesión como Administrador.");
            e.printStackTrace();
        }

        // Verificar si el usuario es un ciudadano
        if (!loginExitoso) {
            String sqlCiudadano = "SELECT * FROM Ciudadano WHERE email = ? AND contrasena = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sqlCiudadano)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    int idCiudadano = rs.getInt("idCiudadano");  // Aca se obtiene el ID del ciudadano
                    System.out.println("Inicio de sesión exitoso como Ciudadano, bienvenido " + rs.getString("nombre"));
                    loginExitoso = true;
                    mostrarMenuCiudadano(scanner, idCiudadano);  // Pasar el ID para registrar solicitudes
                }
            } catch (SQLException e) {
                System.out.println("Error al intentar iniciar sesión como Ciudadano.");
                e.printStackTrace();
            }
        }

        // Mensaje de error si las credenciales son incorrectas
        if (!loginExitoso) {
            System.out.println("Credenciales incorrectas. Intente de nuevo.");
        }

        // Cerrar la sesión
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo para registrar un nuevo usuario en la base de datos
    public static void registrarUsuario(Scanner scanner) {
        System.out.println("---- Registrar Usuario ----");

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Seleccione el tipo de usuario (1 = Ciudadano, 2 = Administrador): ");
        int tipoUsuario = scanner.nextInt();
        scanner.nextLine();

        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        try {
            String sql;
            if (tipoUsuario == 1) {
                sql = "INSERT INTO Ciudadano (nombre, email, contrasena) VALUES (?, ?, ?)";
            } else if (tipoUsuario == 2) {
                sql = "INSERT INTO Administrador (nombre, email, contrasena) VALUES (?, ?, ?)";
            } else {
                System.out.println("Tipo de usuario no válido.");
                return;
            }

            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, email);
                statement.setString(3, contrasena);
                statement.executeUpdate();
                System.out.println("Usuario registrado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Menu para Administradores
    public static void mostrarMenuAdmin(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n---- Menú del Administrador ----");
            System.out.println("1. Asignar Recursos a Solicitud");
            System.out.println("2. Generar Reportes");
            System.out.println("3. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    asignarRecursoASolicitud(scanner);  // llamamos al metodo
                    break;
                case 2:
                    generarReportes();  // llamamos al metodo para generar los reportes
                    break;
                case 3:
                    System.out.println("Cerrando sesión de Administrador...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);  // Salir al seleccionar "Cerrar sesión"
    }

    public static void asignarRecursoASolicitud(Scanner scanner) {
        System.out.println("---- Asignar Recurso a Solicitud ----");

        // Pedir el ID de la solicitud a la que se va a asignar el recurso
        System.out.print("Ingrese el ID de la solicitud: ");
        int idSolicitud = scanner.nextInt();
        scanner.nextLine();

        // Pedir el ID del recurso a asignar
        System.out.print("Ingrese el ID del recurso que desea asignar: ");
        int idRecurso = scanner.nextInt();
        scanner.nextLine();

        // Conexión a la base de datos
        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        // Actualizar la solicitud para asignarle un recurso
        String sql = "UPDATE Solicitud SET idRecurso = ? WHERE idSolicitud = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idRecurso);
            statement.setInt(2, idSolicitud);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Recurso asignado correctamente a la solicitud con ID: " + idSolicitud);
            } else {
                System.out.println("No se encontró una solicitud con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al asignar recurso a la solicitud.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generarReportes() {
        System.out.println("---- Generar Reportes ----");

        // Conexión a la base de datos
        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        // Consulta SQL para contar el número de solicitudes en cada estado
        String sql = "SELECT estado, COUNT(*) AS cantidad FROM Solicitud GROUP BY estado";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            // Mostrar el reporte
            System.out.println("\n---- Reporte de Solicitudes ----");
            while (rs.next()) {
                String estado = rs.getString("estado");
                int cantidad = rs.getInt("cantidad");
                System.out.println("Estado: " + estado + " | Cantidad: " + cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar reportes.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Mensaje que indique que el reporte fue generado
        System.out.println("Reporte generado con éxito.");
    }


    // Menú para el Ciudadano
    public static void mostrarMenuCiudadano(Scanner scanner, int idCiudadano) {
        int opcion;
        do {
            System.out.println("\n---- Menú del Ciudadano ----");
            System.out.println("1. Registrar Solicitud");
            System.out.println("2. Consultar Estado de Solicitud");
            System.out.println("3. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarSolicitud(scanner, idCiudadano);  // Pasar idCiudadano para poder asociar la solicitud
                    break;
                case 2:
                    consultarEstadoSolicitud(scanner);
                    break;
                case 3:
                    System.out.println("Cerrando sesión de Ciudadano...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);  // Salir al seleccionar "Cerrar sesión"
    }

    // Metodo para registrar una solicitud
    public static void registrarSolicitud(Scanner scanner, int idCiudadano) {
        System.out.println("---- Registrar Solicitud ----");

        // Submenú de servicios
        System.out.println("Seleccione el tipo de servicio:");
        System.out.println("1. Recolección de Residuos");
        System.out.println("2. Alumbrado Público");
        System.out.println("3. Poda de Árboles");
        System.out.println("4. Reciclaje");
        System.out.print("Seleccione una opción: ");
        int tipoServicioOpcion = scanner.nextInt();
        scanner.nextLine();

        String tipoServicio;
        switch (tipoServicioOpcion) {
            case 1:
                tipoServicio = "Recolección de Residuos";
                break;
            case 2:
                tipoServicio = "Alumbrado Público";
                break;
            case 3:
                tipoServicio = "Poda de Árboles";
                break;
            case 4:
                tipoServicio = "Reciclaje";
                break;
            default:
                System.out.println("Opción no válida. Solicitud no registrada.");
                return;
        }

        // Descripción de la solicitud
        System.out.print("Ingrese una descripción para la solicitud: ");
        String descripcion = scanner.nextLine();

        // Conexión a la base de datos para almacenar la solicitud
        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        String sql = "INSERT INTO Solicitud (idCiudadano, tipoServicio, descripcion, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, idCiudadano);
            statement.setString(2, tipoServicio);
            statement.setString(3, descripcion);
            statement.setString(4, "Pendiente");

            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idSolicitud = generatedKeys.getInt(1);
                    System.out.println("Solicitud registrada exitosamente con ID: " + idSolicitud);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar la solicitud.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo para consultar el estado de una solicitud
    public static void consultarEstadoSolicitud(Scanner scanner) {
        System.out.println("---- Consultar Estado de Solicitud ----");

        // Pedir el ID de la solicitud
        System.out.print("Ingrese el ID de la solicitud que desea consultar: ");
        int idSolicitud = scanner.nextInt();
        scanner.nextLine();

        // Conexión a la base de datos
        Connection conexion = ConexionBD.conectar();
        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        // Consulta SQL para obtener el estado de la solicitud
        String sql = "SELECT estado FROM Solicitud WHERE idSolicitud = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idSolicitud);  // Pasar el ID de la solicitud como parámetro
            ResultSet rs = statement.executeQuery();

            // Si se encuentra la solicitud, mostrar el estado
            if (rs.next()) {
                String estado = rs.getString("estado");
                System.out.println("El estado de la solicitud con ID " + idSolicitud + " es: " + estado);
            } else {
                System.out.println("No se encontró una solicitud con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el estado de la solicitud.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
