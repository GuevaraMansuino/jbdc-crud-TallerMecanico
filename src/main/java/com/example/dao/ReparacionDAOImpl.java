package com.example.dao;

import com.example.model.Reparacion;
import com.example.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReparacionDAOImpl extends GenericDAOImpl<Reparacion, Integer> implements ReparacionDAO{

    public void crearTablaReparacion() {
        String sql = """
    CREATE TABLE IF NOT EXISTS reparacion (
        id INT PRIMARY KEY AUTO_INCREMENT,
        vehiculoId INT,
        descripcion VARCHAR(1000),
        fecha DATE,
        costo DOUBLE,
        FOREIGN KEY (vehiculoId) REFERENCES vehiculos(id)
    )
    """;

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int crear (Reparacion reparacion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int generatedid = -1;

        try {
            conn = DatabaseUtil.getConnection();
            String crearReparacion = "INSERT INTO reparacion (vehiculoId, descripcion, fecha, costo) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(crearReparacion, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1,reparacion.getVehiculoId());
            stmt.setString(2,reparacion.getDescripcion());
            stmt.setDate(3, Date.valueOf(reparacion.getFecha()));
            stmt.setDouble(4,reparacion.getCosto());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedid = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseUtil.closeResources(conn,stmt);

        }
        return generatedid;
    }

    public Reparacion buscarPorIdReparacion( int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Reparacion reparacion = null;

        try {
            conn = DatabaseUtil.getConnection();
            String buscarPorIdReparacion = "SELECT * FROM reparacion WHERE id = ?";
            stmt = conn.prepareStatement(buscarPorIdReparacion);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if(rs.next()){
                reparacion = new Reparacion(
                        rs.getInt("id"),
                        rs.getInt("vehiculoId"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("costo")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e ) {
                    e.printStackTrace();
                }
            }
            DatabaseUtil.closeResources(conn,stmt);
        }
        return reparacion;
    }

    @Override
    public List<Reparacion> listarTodos(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Reparacion> reparaciones = new ArrayList<>();

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement();
            String listTodReparacion = "SELECT * FROM reparacion";

            rs = stmt.executeQuery(listTodReparacion);

            while (rs.next()) {
                Reparacion reparacion = new Reparacion(
                        rs.getInt("id"),
                        rs.getInt("vehiculoId"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("costo")
                );

                reparaciones.add(reparacion);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseUtil.closeResources(conn,stmt);

        }

        return reparaciones;

    }

    @Override
    public boolean actualizar(Reparacion reparacion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;

        try {
            conn = DatabaseUtil.getConnection();
            String actualizarReparacion = "UPDATE reparacion SET descripcion = ?, fecha = ?, costo = ? WHERE id = ?";

            stmt = conn.prepareStatement(actualizarReparacion);

            stmt.setString(1, reparacion.getDescripcion());
            stmt.setDate(2, Date.valueOf(reparacion.getFecha()));
            stmt.setDouble(3, reparacion.getCosto());
            stmt.setInt(4, reparacion.getId());

            int affectedRows = stmt.executeUpdate();
            actualizado = (affectedRows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeResources(conn, stmt);
        }
        return actualizado;
    }


    @Override
    public boolean eliminar (Integer id) {

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean reparacionEliminada = false;

        try {
            conn = DatabaseUtil.getConnection();
            String eliminarReparacion = "DELETE FROM reparacion WHERE id = ?";
            stmt = conn.prepareStatement(eliminarReparacion);

            stmt.setInt(1,id);
            int affectedRows = stmt.executeUpdate();

            reparacionEliminada = (affectedRows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeResources(conn, stmt);
        }

        return reparacionEliminada;

    }

    @Override
    public List<Reparacion> listarPorVehiculoId(int vehiculoId) {
        List<Reparacion> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM reparacion WHERE vehiculoId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehiculoId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Reparacion r = new Reparacion(
                        rs.getInt("id"),
                        rs.getInt("vehiculoId"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("costo")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            DatabaseUtil.closeResources(conn, stmt);
        }

        return lista;
    }


}