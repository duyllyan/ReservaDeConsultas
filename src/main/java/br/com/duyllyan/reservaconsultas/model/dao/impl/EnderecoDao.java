package br.com.duyllyan.reservaconsultas.model.dao.impl;

import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;

import java.sql.Connection;
import java.util.List;

public class EnderecoDao implements IDao<Endereco> {

    private Connection connection;

    public EnderecoDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Endereco endereco) {

    }

    @Override
    public void update(Endereco endereco) {

    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public Endereco selectByID(Integer id) {
        return null;
    }

    @Override
    public List<Endereco> selectAll() {
        return null;
    }
}
