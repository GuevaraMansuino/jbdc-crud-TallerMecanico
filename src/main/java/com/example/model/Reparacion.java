package com.example.model;

import java.time.LocalDate;

public class Reparacion {
    private int id;
    private int vehiculoId;
    private String descripcion;
    private LocalDate fecha;
    private double costo;

    public Reparacion() {
    }

    public Reparacion(int vehiculoId, String descripcion, LocalDate fecha, double costo) {
        this.vehiculoId = vehiculoId;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.costo = costo;
    }

    public Reparacion(int id, int vehiculoId, String descripcion, LocalDate fecha, double costo) {
        this.id = id;
        this.vehiculoId = vehiculoId;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}