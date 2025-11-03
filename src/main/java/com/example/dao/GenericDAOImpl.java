package com.example.dao;

import com.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    protected Connection getConnection() throws SQLException {
        return DatabaseUtil.getConnection();
    }

    @Override
    public int crear(T entidad) {
        throw new UnsupportedOperationException("Debe ser implementado por la subclase");
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        throw new UnsupportedOperationException("Debe ser implementado por la subclase");
    }

    @Override
    public List<T> listarTodos() {
        throw new UnsupportedOperationException("Debe ser implementado por la subclase");
    }

    @Override
    public boolean actualizar(T entidad) {
        throw new UnsupportedOperationException("Debe ser implementado por la subclase");
    }

    @Override
    public boolean eliminar(ID id) {
        throw new UnsupportedOperationException("Debe ser implementado por la subclase");
    }
}