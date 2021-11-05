package br.com.duyllyan.reservaconsultas.model.service;

import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;

import java.util.List;

public class PacienteService {
    private IDao<Paciente> pacienteDao;

    public PacienteService(IDao<Paciente> pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    public void insertPaciente(Paciente paciente) {
        pacienteDao.insert(paciente);
    }

    public void updatePaciente(Paciente paciente) {
        pacienteDao.update(paciente);
    }

    public void deletePacienteByID(Integer id) {
        pacienteDao.deleteByID(id);
    }

    public Paciente selectPacienteByID(Integer id) {
        return pacienteDao.selectByID(id);
    }

    public List<Paciente> selectAllPaciente() {
        return pacienteDao.selectAll();
    }
}
