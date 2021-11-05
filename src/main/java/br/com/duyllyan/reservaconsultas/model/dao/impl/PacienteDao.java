package br.com.duyllyan.reservaconsultas.model.dao.impl;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.database.exception.DatabaseException;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private final String SQL_DELETE = "DELETE FROM clinica.pacientes WHERE paciente_id = ?;";
    private final String SQL_SELECT_BY_ID = "SELECT " +
            "p.*, " +
            "e.endereco_rua, " +
            "e.endereco_numero, " +
            "e.endereco_bairro, " +
            "e.endereco_cidade, " +
            "e.endereco_estado, " +
            "FROM clinica.pacientes AS p " +
            "INNER JOIN clinica.enderecos AS e " +
            "ON p.endereco_id = e.endereco_id " +
            "WHERE paciente_id = ? " +
            "ORDER BY paciente_id;";
    private final String SQL_SELECT_ALL = "SELECT " +
            "p.*, " +
            "e.endereco_rua, " +
            "e.endereco_numero, " +
            "e.endereco_bairro, " +
            "e.endereco_cidade, " +
            "e.endereco_estado, " +
            "FROM clinica.pacientes AS p " +
            "INNER JOIN clinica.enderecos AS e " +
            "ON p.endereco_id = e.endereco_id " +
            "ORDER BY paciente_id;";

    private Connection connection;

    public PacienteDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Paciente paciente) {
        PreparedStatement statement = null;
        LOG.debug("Inserindo " + paciente.getNome() + " na tabela pacientes");
        try {
            statement = connection.prepareStatement(SQL_INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getSobrenome());
            statement.setString(3, paciente.getRg());
            statement.setDate(4, new Date(paciente.getDataDeCadastro().getTime()));
            statement.setInt(5, paciente.getEndereco().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                LOG.info("Paciente " + paciente.getNome() + " inserido");
                ResultSet resultSet = statement.getGeneratedKeys();
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
        LOG.debug("Atualizando o registro do paciente: " + paciente.getNome());
        try {
            statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getSobrenome());
            statement.setString(3, paciente.getRg());
            statement.setDate(4, new Date(paciente.getDataDeCadastro().getTime()));
            statement.setInt(5, paciente.getEndereco().getId());
            statement.setInt(6, paciente.getId());

            statement.executeUpdate();

            LOG.info("O registro do paciente " + paciente.getNome() + " foi atualizado");
        } catch (SQLException e) {
            LOG.error("Não foi possível atualizar os dados do paciente " + paciente.getNome() + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement statement = null;
        LOG.debug("Excluindo registro do paciente de id = " + id);
        try {
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            LOG.info("Registro excluído");
        } catch (SQLException e) {
            LOG.error("Não foi possível excluir o registro do paciente: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public Paciente selectByID(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LOG.debug("Buscando registro do  paciente de id = " + id);
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Endereco endereco = getEndereco(resultSet);
                Paciente paciente = getPaciente(resultSet, endereco);
                LOG.info("Paciente encontrado: " + paciente);
                return paciente;
            }
            return null;
        } catch (SQLException e) {
            LOG.error("Não foi possível encontrar o registro do paciente: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeResultSet(resultSet);
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public List<Paciente> selectAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LOG.debug("Listando pacientes");
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();

            List<Paciente> pacientes = new ArrayList<>();
            Map<Integer, Endereco> enderecos = new HashMap();

            while (resultSet.next()) {
                int id = resultSet.getInt("endereco_id");
                Endereco endereco = enderecos.get(id);
                if (endereco == null) {
                    endereco = getEndereco(resultSet);
                    enderecos.put(id, endereco);
                }
                Paciente paciente = getPaciente(resultSet, endereco);
                pacientes.add(paciente);
            }
            LOG.info("Foram listados " + pacientes.size() + " pacientes");
            return pacientes;
        } catch (SQLException e) {
            LOG.error("Erro ao listar todos os registros dos pacientes: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeResultSet(resultSet);
            Conexao.closeStatement(statement);
        }
    }

    private Paciente getPaciente(ResultSet resultSet, Endereco endereco) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setId(resultSet.getInt("paciente_id"));
        paciente.setNome(resultSet.getString("paciente_nome"));
        paciente.setSobrenome(resultSet.getString("paciente_sobrenome"));
        paciente.setRg(resultSet.getString("paciente_rg"));
        paciente.setDataDeCadastro(resultSet.getDate("paciente_data_cadastro"));
        paciente.setEndereco(endereco);
        return paciente;
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
