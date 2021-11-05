package br.com.duyllyan.reservaconsultas.model.service.factory;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.DentistaDao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.EnderecoDao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.PacienteDao;
import br.com.duyllyan.reservaconsultas.model.service.DentistaService;
import br.com.duyllyan.reservaconsultas.model.service.EnderecoService;
import br.com.duyllyan.reservaconsultas.model.service.PacienteService;

public class ServiceFactory {
    public static EnderecoService createEnderecoService() {
        return new EnderecoService(new EnderecoDao(Conexao.getConnection()));
    }

    public static PacienteService createPacienteService() {
        return new PacienteService(new PacienteDao(Conexao.getConnection()));
    }

    public static DentistaService createDentistaService() {
        return new DentistaService(new DentistaDao(Conexao.getConnection()));
    }
}
