package br.com.duyllyan.reservaconsultas;

import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.dao.factory.DaoFactory;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ClinicaDatabase.getInstance();
        IDao enderecoDao = DaoFactory.createEnderecoDao();
        IDao pacienteDao = DaoFactory.createPacienteDao();

        Endereco endereco = new Endereco("Rua 02", 25, "Bairro Sul", "Araguaína", "Tocantins");
        Paciente paciente = new Paciente("Duyllyan", "Almeida", "12345", new Date(), endereco);

        enderecoDao.insert(endereco);
        pacienteDao.insert(paciente);

        System.out.println(pacienteDao.selectByID(1));


    }
}
