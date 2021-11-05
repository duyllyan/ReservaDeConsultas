package br.com.duyllyan.reservaconsultas.model.service;

import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;

import java.util.List;

public class EnderecoService {
    private IDao<Endereco> enderecoDao;

    public EnderecoService(IDao<Endereco> enderecoDao) {
        this.enderecoDao = enderecoDao;
    }

    public void insertEndereco(Endereco endereco) {
        enderecoDao.insert(endereco);
    }

    public void updateEndereco(Endereco endereco) {
        enderecoDao.update(endereco);
    }

    public void deleteEnderecoByID(Integer id) {
        enderecoDao.deleteByID(id);
    }

    public Endereco selectEnderecoByID(Integer id) {
        return enderecoDao.selectByID(id);
    }

    public List<Endereco> selectAllEndereco() {
        return enderecoDao.selectAll();
    };
}
