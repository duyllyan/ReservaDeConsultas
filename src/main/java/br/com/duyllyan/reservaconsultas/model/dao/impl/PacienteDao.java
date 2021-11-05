package br.com.duyllyan.reservaconsultas.model.dao.impl;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.database.exception.DatabaseException;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;


public class PacienteDao implements IDao<Paciente> {

    private final Logger LOG = LogManager.getLogger(PacienteDao.class);
    private final String SQL_INSERT_INTO = "INSERT INTO clinica.pacientes " +
            "(paciente_nome, paciente_sobrenome, paciente_rg, paciente_data_cadastro, endereco_id) " +
            "VALUES " +
            "(?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE clinica.pacientes " +
            "SET paciente_nome = ?, " +
            "paciente_sobrenome = ?, " +
            "paciente_rg = ?, " +
            "paciente_data_cadastro = ?, " +
            "endereco_id = ? " +
            "WHERE paciente_id = ?;";

    private Connection connection;

    public PacienteDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Paciente paciente) {
        PreparedStatement statement = null;
        try {
            LOG.debug("Inserindo " + paciente.getNome() + "na tabela pacientes");
            statement = connection.prepareStatement(SQL_INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getSobrenome());
            statement.setString(3, paciente.getRg());
            statement.setDate(4, (Date) paciente.getDataDeCadastro());
            statement.setInt(5, paciente.getEndereco().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                LOG.info("Paciente " + paciente.getNome() + " inserido");
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    paciente.setId(id);
                }
                Conexao.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Não foi possível inserir o paciente " + paciente.getNome() + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }


    }

    @Override
    public void update(Paciente paciente) {
        PreparedStatement statement = null;
        try {
            LOG.debug("Atualizando o registro do paciente: " + paciente.getNome());
            statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getSobrenome());
            statement.setString(3, paciente.getRg());
            statement.setDate(4, (Date) paciente.getDataDeCadastro());
            statement.setInt(5, paciente.getEndereco().getId());


        } catch (SQLException e) {
            LOG.error("Não foi possível atualizar os dados do paciente " + paciente.getNome() + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public Paciente selectByID(Integer id) {
        return null;
    }

    @Override
    public List<Paciente> selectAll() {
        return null;
    }
}
