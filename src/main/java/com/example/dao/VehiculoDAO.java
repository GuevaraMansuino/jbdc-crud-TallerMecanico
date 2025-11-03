package com.example.dao;

import com.example.model.Vehiculo;

public interface VehiculoDAO extends GenericDAO<Vehiculo, Integer> {

    public void crearTablaVehiculos();


}