package com.example.dao;

import com.example.model.Reparacion;

import java.util.List;

public interface ReparacionDAO {

    public void crearTablaReparacion();

    public List<Reparacion> listarPorVehiculoId(int vehiculoId);

}
