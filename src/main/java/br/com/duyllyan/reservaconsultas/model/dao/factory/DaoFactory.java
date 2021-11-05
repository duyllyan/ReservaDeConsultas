package br.com.duyllyan.reservaconsultas.model.dao.factory;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.EnderecoDao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.PacienteDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;

public class DaoFactory {

    public static IDao<Endereco> createEnderecoDao() {
        return new EnderecoDao(Conexao.getConnection());
    }
    public static IDao<Paciente> createPacienteDao() {
        return new PacienteDao(Conexao.getConnection());
    }
}
