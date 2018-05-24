package ro.uaic.info.ip.proiect.b3.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.uaic.info.ip.proiect.b3.configurations.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Aceasta clasa este un singleton care poate produce o conexiune catre baza de date.
 * Se foloseste o librarie numita Hikari pentru gestionarea conexiunilor cu ajutorul connection poolurilor.
 * Documentatia pentru Hikari - https://github.com/brettwooldridge/HikariCP
 */

@Service
public class Database {
    /**
     * Instanta bazei de date initializata la runtime.
     */
    private static final Database instance = new Database();
    /**
     * Un DataSource ce contine informatii despre baza de date la care se face conectarea si diverse configuratii.
     */
    private HikariDataSource dataSource;


    /**
     * Configurarea sursei de date in functie de serverului de baze de date folosit.
     */
    @Autowired
    private Database() {
        HikariConfig config = new HikariConfig();
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

        config.setJdbcUrl(databaseConfiguration.getConnectionUrl());
        config.setUsername(databaseConfiguration.getUsername());
        config.setPassword(databaseConfiguration.getPassword());
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionStat", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);
        config.setLeakDetectionThreshold(60 * 1000);
        config.setMaximumPoolSize(20);
        config.setIdleTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static Database getInstance() {
        return instance;
    }

    /**
     * Aceasta metoda returneaza o conexiune catre baza de date.
     * Deoarece este folosit un connection pool este important sa inchideti conexiunea dupa terminarea query-urilor catre baza de date.
     *
     * @return o conexiune la baza de date
     * @throws SQLException in cazul in care nu se poate crea conexiunea catre baza de date
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public ResultSet selectQuery(Connection connection, String query, String... parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 1; i <= parameters.length; ++i) {
            preparedStatement.setString(i, parameters[i - 1]);
        }

        return preparedStatement.executeQuery();
    }


    public int updateOperation(String query, String... parameters) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 1; i <= parameters.length; ++i) {
            preparedStatement.setString(i, parameters[i - 1]);
        }

        int rowsModified = preparedStatement.executeUpdate();
        connection.close();

        return rowsModified;
    }
}
