package br.com.duyllyan.reservaconsultas.database;

import br.com.duyllyan.reservaconsultas.database.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Conexao {

    private static final Logger log = LogManager.getLogger(Conexao.class);
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                log.debug("Carregando propriedades do banco de dados");
                Properties props = loadProperties();
                String url = props.getProperty("url");
                String user = props.getProperty("user");
                String password = props.getProperty("password");
                connection = DriverManager.getConnection(url, user, password);
                log.info("Propriedades do banco de dados carregadas");
            } catch (SQLException e) {
                log.error("Erro no estabelecimento da conexão com o banco de dados: " + e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                log.info("Conexão com o banco de dados encerrada");
            } catch (SQLException e) {
                log.error("Erro no encerramento da conexão do banco de dados: " + e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
                log.info("Statement encerrado");
            } catch (SQLException e) {
                log.error("Erro no encerramento do Statement: " + e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                log.info("ResultSet encerrado");
            } catch (SQLException e) {
                log.error("Erro no encerramento do ResultSet: " + e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try {
            FileInputStream fin = new FileInputStream("src/main/resources/database.properties");
            Properties props = new Properties();
            props.load(fin);
            log.info("Propriedades do banco de dados carregadas.");
            return props;
        } catch (IOException e) {
            log.error("Erro no carregamento das configurações do banco de dados: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
