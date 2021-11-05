package br.com.duyllyan.reservaconsultas.model.dao.impl;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.database.exception.DatabaseException;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class EnderecoDao implements IDao<Endereco> {

    private static final String SQL_UPDATE = "UPDATE clinica.enderecos " +
            "SET endereco_rua = ?, " +
            "endereco_numero = ?, " +
            "endereco_bairro = ?, " +
            "endereco_cidade = ?, " +
            "endereco_estado = ? " +
            "WHERE endereco_id = ?;";;
    private static final Logger LOG = LogManager.getLogger(EnderecoDao.class);
    private static final String SQL_INSERT_INTO = "INSERT INTO clinica.enderecos " +
            "(endereco_rua, endereco_numero, endereco_bairro, endereco_cidade, endereco_estado) " +
            "VALUES " +
            "(?, ?, ?, ?, ?)";
    private final String SQL_SELECT_BY_ID = "SELECT FROM clinica.enderecos WHERE endereco_id = ?;";
    private final String SQL_SELECT_ALL = "SELECT " +
            "p.*, " +
            "e.endereco_rua, " +
            "e.endereco_numero, " +
            "e.endereco_bairro, " +
            "e.endereco_cidade, " +
            "e.endereco_estado, " +
            "FROM clinica.pacientes AS p" +
            "INNER JOIN clinica.enderecos AS e" +
            "ON p.endereco_id = e.endereco_id" +
            "ORDER BY paciente_id;";


    private Connection connection;

    public EnderecoDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Endereco endereco) {
        PreparedStatement statement = null;
        LOG.debug("Inserindo " + endereco + " na tabela endereços");
        try {
            statement = connection.prepareStatement(SQL_INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, endereco.getRua());
            statement.setInt(2, endereco.getNumero());
            statement.setString(3, endereco.getBairro());
            statement.setString(4, endereco.getCidade());
            statement.setString(5, endereco.getEstado());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                LOG.info("Endereço " + endereco + " inserido");
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    endereco.setId(id);
                }
                Conexao.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Não foi possível inserir o endereço " + endereco + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void update(Endereco endereco) {
        PreparedStatement statement = null;
        LOG.debug("Atualizando o registro do endereço: " + endereco);
        try {
            statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, endereco.getRua());
            statement.setInt(2, endereco.getNumero());
            statement.setString(3, endereco.getBairro());
            statement.setString(4, endereco.getCidade());
            statement.setString(5, endereco.getEstado());
            LOG.info("O registro do endereço " + endereco + " foi atualizado");
        } catch (SQLException e) {
            LOG.error("Não foi possível atualizar o endereço " + endereco + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public Endereco selectByID(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LOG.debug("Buscando registro do endereço de id = " + id);
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Endereco endereco = getEndereco(resultSet);
                LOG.info("Endereço encontrado: " + endereco);
                return endereco;
            }
            return null;
        } catch (SQLException e) {
            LOG.error("Não foi possível encontrar o endereço: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeResultSet(resultSet);
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public List<Endereco> selectAll() {
        return null;
    }

    private Endereco getEndereco(ResultSet resultSet) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(resultSet.getInt("endereco_id"));
        endereco.setRua(resultSet.getString("endereco_rua"));
        endereco.setNumero(resultSet.getInt("endereco_numero"));
        endereco.setBairro(resultSet.getString("endereco_bairro"));
        endereco.setCidade(resultSet.getString("endereco_cidade"));
        endereco.setEstado(resultSet.getString("endereco_estado"));
        return endereco;
    }
}
