import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Datos de la conexión
    private static final String URL = "jdbc:mysql://localhost:3306/proyectofinalbd?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "2003";
    
    // Método para establecer la conexión
    public static Connection obtenerConexion() {
        Connection conexion = null;

        try {
            // Registrar el driver de MySQL (no necesario en versiones recientes de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return conexion;
    }

    public static void main(String[] args) {
        // Probar la conexión
        Connection conexion = obtenerConexion();

        // Si la conexión es exitosa, cerrarla
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
