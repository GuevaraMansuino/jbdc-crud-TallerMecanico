package com.example.model;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private String patente;
    private String propietario;

    public Vehiculo() {
    }

    public Vehiculo(String marca, String modelo, String patente, String propietario) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.propietario = propietario;
    }

    public Vehiculo(int id, String marca, String modelo, String patente, String propietario) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.propietario = propietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "===Vehiculo===\n" +
                "id = "+ id + "\n" +
                "marca = "+ marca + "\n" +
                "modelo = " + modelo + "\n" +
                "patente = "+ patente + "\n" +
                "propietario = "+ propietario + "\n";
    }

}