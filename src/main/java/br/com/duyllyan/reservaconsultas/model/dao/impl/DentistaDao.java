package br.com.duyllyan.reservaconsultas.model.dao.impl;

import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.database.exception.DatabaseException;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.entities.Dentista;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDao implements IDao<Dentista> {

    private static final Logger LOG = LogManager.getLogger(DentistaDao.class);
    private static final String SQL_INSERT_INTO = "INSERT INTO clinica.dentistas " +
            "(dentista_matricula, dentista_nome, dentista_sobrenome) " +
            "VALUES " +
            "(?, ?, ? );";
    private static final String SQL_UPDATE = "UPDATE clinica.dentistas " +
            "SET dentista_matricula = ?, " +
            "dentista_nome = ?," +
            "dentista_sobrenome = ? " +
            "WHERE dentista_id = ?;";
    private static final String SQL_DELETE = "DELETE FROM clinica.dentistas WHERE dentista_id = ?;";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM clinica.dentistas WHERE dentista_id = ?;";
    private static final String SQL_SELECT_ALL = "SELECT * FROM clinica.dentistas;";

    private final Connection connection;

    public DentistaDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Dentista dentista) {
        PreparedStatement statement = null;
        LOG.debug("Inserindo :" + dentista.getNome() + " na tabela dentistas");
        try {
            statement = connection.prepareStatement(SQL_INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, dentista.getNumeroMatricula());
            statement.setString(2, dentista.getNome());
            statement.setString(3, dentista.getSobrenome());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                LOG.info("Dentista " + dentista.getNome() + " inserido");
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    dentista.setId(id);
                }
                Conexao.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Não foi possível adicionar o dentista: " + dentista.getNome() + " na tabela");
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void update(Dentista dentista) {
        PreparedStatement statement = null;
        LOG.debug("Inserindo :" + dentista.getNome() + " na tabela dentistas");
        try {
            statement = connection.prepareStatement(SQL_INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, dentista.getNumeroMatricula());
            statement.setString(2, dentista.getNome());
            statement.setString(3, dentista.getSobrenome());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                LOG.info("Dentista " + dentista.getNome() + " inserido");
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    dentista.setId(id);
                }
                Conexao.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Não foi possível adicionar o dentista: " + dentista.getNome() + " na tabela");
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement statement = null;
        LOG.debug("Excluindo dentista de id: " +id);
        try {
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            LOG.error("Dentista excluído");
        } catch (SQLException e) {
            LOG.error("Erro ao excluir dentista :" + id + ": " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public Dentista selectByID(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LOG.debug("Buscando por dentista de id " + id);
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_ID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Dentista dentista = getDentista(resultSet);
                LOG.info("Dentista encontrado: " + dentista);
                return dentista;
            }
            return null;
        } catch (SQLException e) {
            LOG.error("Erro ao buscar dentista na tabela");
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeResultSet(resultSet);
            Conexao.closeStatement(statement);
        }
    }

    @Override
    public List<Dentista> selectAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LOG.debug("Listando dentistas");
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.executeQuery();

            List<Dentista> dentistas = new ArrayList<>();

            while (resultSet.next()) {
                Dentista dentista = getDentista(resultSet);
                dentistas.add(dentista);
            }
            LOG.info("Foram encontrados: " + dentistas.size() + " dentistas");
            return dentistas;
        } catch (SQLException e) {
            LOG.error("Erro ao buscar dentista na tabela");
            throw new DatabaseException(e.getMessage());
        } finally {
            Conexao.closeResultSet(resultSet);
            Conexao.closeStatement(statement);
        }
    }

    private Dentista getDentista(ResultSet resultSet) throws SQLException {
        Dentista dentista = new Dentista();
        dentista.setId(resultSet.getInt("dentista_id"));
        dentista.setNumeroMatricula(resultSet.getInt("dentista_matricula"));
        dentista.setNome(resultSet.getString("dentista_nome"));
        dentista.setSobrenome(resultSet.getString("dentista_sobrenome"));
        return dentista;
    }
}
