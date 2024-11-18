import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de la conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyectofinalbd?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "2003.";

    // Método para obtener la conexión a la base de datos
    public static Connection obtenerConexion() {
        Connection conn = null;
        try {
            // Registrar el driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }

    // Método para cerrar la conexión a la base de datos
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
