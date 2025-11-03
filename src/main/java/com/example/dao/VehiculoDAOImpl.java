package com.example.dao;

import com.example.model.Vehiculo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehiculoDAOImpl extends GenericDAOImpl<Vehiculo, Integer> implements VehiculoDAO {

    private static final Logger logger = LogManager.getLogger(VehiculoDAOImpl.class);

    @Override
    public void crearTablaVehiculos() {
        String sql = """
            CREATE TABLE IF NOT EXISTS vehiculos (
                id INT PRIMARY KEY AUTO_INCREMENT,
                marca VARCHAR(50),
                modelo VARCHAR(50),
                patente VARCHAR(10),
                propietario VARCHAR(60)
            )
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.info("Tabla 'vehiculos' verificada o creada.");
        } catch (SQLException e) {
            logger.error("Error al crear la tabla 'vehiculos'.", e);
        }
    }

    @Override
    public int crear(Vehiculo vehiculo) {
        int generatedId = -1;
        String sql = "INSERT INTO vehiculos (marca, modelo, patente, propietario) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setString(3, vehiculo.getPatente());
            stmt.setString(4, vehiculo.getPropietario());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        logger.info("Vehículo creado con ID: {}", generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Error al insertar vehículo.", e);
        }

        return generatedId;
    }

    @Override
    public Optional<Vehiculo> buscarPorId(Integer id) {
        Vehiculo vehiculo = null;
        String sql = "SELECT * FROM vehiculos WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vehiculo = new Vehiculo(
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("patente"),
                            rs.getString("propietario")
                    );
                    logger.info("Vehículo encontrado con ID: {}", id);
                }
            }

        } catch (SQLException e) {
            logger.error("Error al buscar vehículo por ID: " + id, e);
        }

        return Optional.ofNullable(vehiculo);
    }

    @Override
    public List<Vehiculo> listarTodos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("patente"),
                        rs.getString("propietario")
                );
                lista.add(vehiculo);
            }
            logger.info("Listado de vehículos recuperado. Total: {}", lista.size());

        } catch (SQLException e) {
            logger.error("Error al listar vehículos.", e);
        }

        return lista;
    }

    @Override
    public boolean actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, patente = ?, propietario = ? WHERE id = ?";
        boolean actualizado = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setString(3, vehiculo.getPatente());
            stmt.setString(4, vehiculo.getPropietario());
            stmt.setInt(5, vehiculo.getId());

            actualizado = stmt.executeUpdate() > 0;
            if (actualizado) {
                logger.info("Vehículo actualizado con ID: {}", vehiculo.getId());
            } else {
                logger.warn("No se actualizó ningún vehículo para ID: {}", vehiculo.getId());
            }

        } catch (SQLException e) {
            logger.error("Error al actualizar vehículo con ID: " + vehiculo.getId(), e);
        }

        return actualizado;
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";
        boolean eliminado = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            eliminado = stmt.executeUpdate() > 0;

            if (eliminado) {
                logger.info("Vehículo eliminado con ID: {}", id);
            } else {
                logger.warn("No se eliminó ningún vehículo con ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error al eliminar vehículo con ID: " + id, e);
        }

        return eliminado;
    }
}