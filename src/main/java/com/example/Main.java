package com.example;

import com.example.dao.ReparacionDAOImpl;
import com.example.dao.VehiculoDAOImpl;
import com.example.model.Reparacion;
import com.example.model.Vehiculo;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VehiculoDAOImpl vehiculoDAO = new VehiculoDAOImpl();
        ReparacionDAOImpl reparacionDAO = new ReparacionDAOImpl();

        vehiculoDAO.crearTablaVehiculos();
        reparacionDAO.crearTablaReparacion();


        //Mostrar MENU
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    "GESTIÓN DE TALLER MECÁNICO\n" +
                            "1. Cargar nuevo vehículo y su reparación\n" +
                            "2. Agregar reparación a un vehículo existente\n" +
                            "3. Mostrar todos los vehículos y reparaciones\n" +
                            "4. Eliminar vehiculo o reparacion\n" +
                            "5. Actualizar vehiculo o reparacion \n" +
                            "6.SALIR\n"+
                            "Seleccione una opción:"
            );
            //verifica salida
            if (opcion == null || opcion.equals("6")) {
                JOptionPane.showMessageDialog(null, "Saliendo...");
                break;
            }
            //validacion de opciones
            switch (opcion) {
                case "1":
                    String marca = JOptionPane.showInputDialog("Marca:");
                    String modelo = JOptionPane.showInputDialog("Modelo:");
                    String patente = JOptionPane.showInputDialog("Patente:");
                    String propietario = JOptionPane.showInputDialog("Propietario:");

                    Vehiculo vehiculo = new Vehiculo(marca, modelo, patente, propietario);
                    int vehiculoId = vehiculoDAO.crear(vehiculo);
                    vehiculo.setId(vehiculoId);

                    Reparacion reparacion = cargarReparacionDesdeDialogo(vehiculoId);
                    reparacionDAO.crear(reparacion);
                    break;

                case "2":
                    int idVehiculo = Integer.parseInt(JOptionPane.showInputDialog("ID del vehículo:"));
                    Reparacion nuevaReparacion = cargarReparacionDesdeDialogo(idVehiculo);
                    reparacionDAO.crear(nuevaReparacion);
                    break;

                case "3":
                    List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
                    StringBuilder sb = new StringBuilder();
                    for (Vehiculo v : vehiculos) {
                        sb.append("Vehículo ID: ").append(v.getId())
                                .append(" | Marca: ").append(v.getMarca())
                                .append(" | Modelo: ").append(v.getModelo())
                                .append(" | Patente: ").append(v.getPatente())
                                .append(" | Propietario: ").append(v.getPropietario()).append("\n");

                        List<Reparacion> reparaciones = reparacionDAO.listarPorVehiculoId(v.getId());

                        for (Reparacion r : reparaciones) {
                            sb.append("   -> Reparación: ").append(r.getDescripcion())
                                    .append(" | ID: ").append(r.getId())
                                    .append(" | Fecha: ").append(r.getFecha())
                                    .append(" | Costo: $").append(r.getCosto()).append("\n");
                        }
                    }

                    JOptionPane.showMessageDialog(null, sb.toString());
                    break;

                case "4":
                    String eliminar = JOptionPane.showInputDialog(
                            "¿Qué desea eliminar?\n" +
                                    "1. Vehículo por ID\n" +
                                    "2. Reparación por ID\n" +
                                    "3. Ambos por ID"
                    );
                    int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID:"));

                    switch (eliminar) {
                        case "1":
                            boolean vehiculoEliminado = vehiculoDAO.eliminar(idEliminar);
                            JOptionPane.showMessageDialog(null, vehiculoEliminado ?
                                    "Vehículo eliminado correctamente." : "No se encontró el vehículo.");
                            break;
                        case "2":
                            boolean reparacionEliminada = reparacionDAO.eliminar(idEliminar);
                            JOptionPane.showMessageDialog(null, reparacionEliminada ?
                                    "Reparación eliminada correctamente." : "No se encontró la reparación.");
                            break;
                        case "3":
                            reparacionDAO.eliminar(idEliminar);
                            vehiculoDAO.eliminar(idEliminar);
                            JOptionPane.showMessageDialog(null, "Vehículo y reparaciones eliminadas (si existían).");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción inválida.");
                    }
                    break;

                case "5":
                    String actualizar = JOptionPane.showInputDialog(
                            "¿Qué desea actualizar?\n" +
                                    "1. Vehículo por ID\n" +
                                    "2. Reparación por ID"
                    );

                    int idActualizar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID:"));

                    switch (actualizar) {
                        case "1":
                            String nuevaMarca = JOptionPane.showInputDialog("Nueva marca:");
                            String nuevoModelo = JOptionPane.showInputDialog("Nuevo modelo:");
                            String nuevaPatente = JOptionPane.showInputDialog("Nueva patente:");
                            String nuevoPropietario = JOptionPane.showInputDialog("Nuevo propietario:");
                            Vehiculo vehiculoActualizado = new Vehiculo(idActualizar, nuevaMarca, nuevoModelo, nuevaPatente, nuevoPropietario);
                            boolean actualizado = vehiculoDAO.actualizar(vehiculoActualizado);
                            JOptionPane.showMessageDialog(null, actualizado ?
                                    "Vehículo actualizado correctamente." : "No se encontró el vehículo.");
                            break;

                        case "2":
                            Reparacion reparacionExistente = reparacionDAO.buscarPorIdReparacion(idActualizar);

                            if (reparacionExistente != null) {
                                String nuevaDesc = JOptionPane.showInputDialog("Nueva descripcion: ");
                                reparacionExistente.setDescripcion(nuevaDesc);
                                LocalDate nuevaFecha = LocalDate.parse(JOptionPane.showInputDialog("Nueva fecha: "));
                                reparacionExistente.setFecha(nuevaFecha);
                                double nuevoCosto = Double.parseDouble(JOptionPane.showInputDialog("Nuevo costo: "));
                                reparacionExistente.setCosto(nuevoCosto);

                                boolean repActualizada = reparacionDAO.actualizar(reparacionExistente);
                                JOptionPane.showMessageDialog(null, repActualizada ?
                                        "Reparación actualizada correctamente." : "No se encontró la reparación.");
                            } else {
                                JOptionPane.showMessageDialog(null, "No se encontró la reparación.");
                            }


                        default:
                            JOptionPane.showMessageDialog(null, "Opción inválida.");
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
                    break;
            }

        }

    }



    private static Reparacion cargarReparacionDesdeDialogo(int vehiculoId) {
        String descripcion = JOptionPane.showInputDialog("Descripción de la reparación:");
        String fechaStr = JOptionPane.showInputDialog("Fecha (AAAA-MM-DD):");
        LocalDate fecha = LocalDate.parse(fechaStr);
        double costo = Double.parseDouble(JOptionPane.showInputDialog("Costo:"));

        return new Reparacion(vehiculoId, descripcion, fecha, costo);
    }
}
