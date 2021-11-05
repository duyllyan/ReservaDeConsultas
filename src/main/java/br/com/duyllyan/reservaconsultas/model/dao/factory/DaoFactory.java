package br.com.duyllyan.reservaconsultas.model.dao.factory;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.dao.impl.EnderecoDao;

public class DaoFactory {

    public static IDao createEnderecoDao() {
        return new EnderecoDao(Conexao.getConnection());
    }
}
