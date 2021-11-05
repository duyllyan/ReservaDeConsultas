package br.com.duyllyan.reservaconsultas.model.service.factory;

import br.com.duyllyan.reservaconsultas.model.dao.factory.DaoFactory;
import br.com.duyllyan.reservaconsultas.model.service.DentistaService;
import br.com.duyllyan.reservaconsultas.model.service.EnderecoService;
import br.com.duyllyan.reservaconsultas.model.service.PacienteService;

public class ServiceFactory {
    public static EnderecoService createEnderecoService() {
        return new EnderecoService(DaoFactory.createEnderecoDao());
    }

    public static PacienteService createPacienteService() {
        return new PacienteService(DaoFactory.createPacienteDao());
    }

    public static DentistaService createDentistaService() {
        return new DentistaService(DaoFactory.createDentistaDao());
    }
}
