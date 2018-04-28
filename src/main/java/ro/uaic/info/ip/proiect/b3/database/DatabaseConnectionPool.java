package ro.uaic.info.ip.proiect.b3.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {
    private HikariDataSource dataSource;
    private static final DatabaseConnectionPool instance = new DatabaseConnectionPool();

    private DatabaseConnectionPool() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://160.153.18.88/teodor");
        config.setUsername("proca_teodor");
        config.setPassword("proiectip");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static DatabaseConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException  {
        return dataSource.getConnection();
    }
}
