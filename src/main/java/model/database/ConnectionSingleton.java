package model.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de gestión de la conexión a la base de datos.
 */
public class ConnectionSingleton {

    private static final String URL = "jdbc:mariadb://localhost:3306/biblioteca";
    private static String USER = "";
    private static String PASSWORD = "";
    private static java.sql.Connection connection = null;

    /**
     * Obtiene una conexión a la base de datos. Si ya existe una conexión
     * activa, la reutiliza.
     *
     * @return Conexión a la base de datos
     */
    public static java.sql.Connection getConnection(Scanner scanner) {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    System.out.print("Introduce usuario: "); // "root"
                    USER = scanner.nextLine().trim();
                    System.out.print("Introduce contraseña: "); // ""
                    PASSWORD = scanner.nextLine().trim();

                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conectado a la base de datos.");
                } catch (SQLException e) {
                    Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, "Error al conectar con la base de datos", e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos si está activa.
     *
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                    connection = null;
                    System.out.println("Conexión terminada.");
                } catch (SQLException ex) {
                    Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
