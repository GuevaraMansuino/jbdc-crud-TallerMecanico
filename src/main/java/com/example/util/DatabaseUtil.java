package com.example.util;

import javax.swing.plaf.PanelUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:h2:file:C:/Users/geron/data/crud-TallerMecanico;AUTO_SERVER=TRUE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

    }

    public static void closeResources(Connection conn, Statement stmt) {
        try{
            if (stmt != null) stmt.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void initDatabase() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();

            String tablaVehiculo = "CREATE TABLE IF NOT EXISTS vehiculos(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "marca VARCHAR (100)," +
                    "modelo VARCHAR (100)," +
                    "patente VARCHAR (10)," +
                    "propietario VARCHAR(40));";


            String tablaReparacion = "CREATE TABLE IF NOT EXISTS reparacion (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "vehiculo_id INT," +
                    "descripcion VARCHAR(355)," +
                    "fecha DATE," +
                    "costo DOUBLE," +
                    "FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id));";

            stmt.execute(tablaVehiculo);
            stmt.execute(tablaReparacion);

            System.out.println("Base de datos inicializada Correctamente");
        } catch (SQLException e) {
            System.out.println("ERROR al inicializar la base de datos: "+ e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn,stmt);
        }
    }
}