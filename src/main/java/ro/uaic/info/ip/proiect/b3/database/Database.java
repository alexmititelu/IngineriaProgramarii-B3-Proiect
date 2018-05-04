package ro.uaic.info.ip.proiect.b3.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Aceasta clasa este un singleton care poate produce o conexiune catre baza de date.
 * Se foloseste o librarie numita Hikari pentru gestionarea conexiunilor cu ajutorul connection poolurilor.
 * Documentatia pentru Hikari - https://github.com/brettwooldridge/HikariCP
 */

public class Database {
    /**
     * Un DataSource contine informatii despre baza de date la care se face conectarea si diverse configuratii.
     */
    private HikariDataSource dataSource;

    /**
     * Instanta bazei de date initializata la runtime.
     */
    private static final Database instance = new Database();

    /**
     * Configurarea sursei de date in functie de serverului de baze de date folosit.
     */
    private Database() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://160.153.18.88/teodor");
        config.setUsername("proca_teodor");
        config.setPassword("proiectip");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Database getInstance() {
        return instance;
    }

    /**
     * Aceasta metoda returneaza o conexiune catre baza de date.
     * Deoarece este folosit un connection pool este important sa inchideti conexiunea dupa terminarea query-urilor catre baza de date.
     * @return o conexiune la baza de date
     * @throws SQLException in cazul in care nu se poate crea conexiunea catre baza de date
     */
    public Connection getConnection() throws SQLException  {
        return dataSource.getConnection();
    }
}
