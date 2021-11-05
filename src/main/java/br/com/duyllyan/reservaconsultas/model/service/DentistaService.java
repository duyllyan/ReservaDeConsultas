package br.com.duyllyan.reservaconsultas.model.service;

import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Dentista;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;

import java.util.List;

public class DentistaService {
    private IDao<Dentista> dentistaDao;

    public DentistaService(IDao<Dentista> dentistaDao) {
        this.dentistaDao = dentistaDao;
    }

    public void insertDentista(Dentista dentista) {
        dentistaDao.insert(dentista);
    }

    public void updateDentista(Dentista dentista) {
        dentistaDao.update(dentista);
    }

    public void deleteDentistaByID(Integer id) {
        dentistaDao.deleteByID(id);
    }

    public Dentista selectDentistaByID(Integer id) {
        return dentistaDao.selectByID(id);
    }

    public List<Dentista> selectAllDentistas() {
        return dentistaDao.selectAll();
    }
}
