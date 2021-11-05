package br.com.duyllyan.reservaconsultas.model.dao;

import java.util.List;

public interface IDao<T> {
    void insert(T obj);
    void update(T obj);
    void deleteByID(Integer id);
    T selectByID(Integer id);
    List<T> selectAll();
}
