package br.com.duyllyan.reservaconsultas.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClinicaDatabase {

    private static final Logger LOG = LogManager.getLogger(ClinicaDatabase.class);
    private final String SQL_CREATE_SCHEMA = "DROP SCHEMA IF EXISTS clinica CASCADE; CREATE SCHEMA clinica;";
    private final String SQL_CREATE_TABLE_PACIENTES = "DROP TABLE IF EXISTS clinica.pacientes; CREATE TABLE clinica.pacientes (" +
            "paciente_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "paciente_nome VARCHAR(100) NOT NULL, " +
            "paciente_sobrenome VARCHAR(150) NOT NULL, " +
            "paciente_rg VARCHAR(20) NOT NULL, " +
            "paciente_data_cadastro DATE NOT NULL, " +
            "endereco_id INT, " +
            "CONSTRAINT FK_endereco " +
            "FOREIGN KEY (endereco_id)" +
            "REFERENCES clinica.enderecos (endereco_id)" +
            ");";
    private final String SQL_CREATE_TABLE_ENDERECOS = "DROP TABLE IF EXISTS clinica.enderecos; CREATE TABLE clinica.enderecos (" +
            "endereco_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "endereco_rua VARCHAR(100) NOT NULL, " +
            "endereco_numero INT NOT NULL, " +
            "endereco_bairro VARCHAR(100) NOT NULL, " +
            "endereco_cidade VARCHAR(100) NOT NULL, " +
            "endereco_estado VARCHAR(100) NOT NULL" +
            ");";
    private Connection connection;
    private static final ClinicaDatabase instance = new ClinicaDatabase(Conexao.getConnection());

    private ClinicaDatabase(Connection connection) {
        this.connection = connection;
        createDataBase();
    }

    public static ClinicaDatabase getInstance() {
        return instance;
    }

    private void createDataBase() {
        createQuery(SQL_CREATE_SCHEMA, "schema clinicas");
        createQuery(SQL_CREATE_TABLE_ENDERECOS, "table enderecos");
        createQuery(SQL_CREATE_TABLE_PACIENTES, "table pacientes");
    }

    private void createQuery(String query, String name) {
        PreparedStatement statement = null;
        try {
            LOG.debug("Criando " + name);
            statement = connection.prepareStatement(query);

            int updateCount = statement.executeUpdate();
            LOG.info("Update count: " + updateCount);
        } catch (SQLException e) {
            LOG.error("Erro na criação do(a) " + name +": " + e.getMessage());
        } finally {
            Conexao.closeStatement(statement);
        }
    }
}
