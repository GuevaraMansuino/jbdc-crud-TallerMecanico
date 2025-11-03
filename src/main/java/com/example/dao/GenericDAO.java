package com.example.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    // crear
    int crear(T entidad);
    // buscarPorId
    Optional<T> buscarPorId(ID id);
    // ver todos
    List<T> listarTodos();
    // actualizar datos
    boolean actualizar(T entidad);
    // eliminar datos
    boolean eliminar(ID id);
}