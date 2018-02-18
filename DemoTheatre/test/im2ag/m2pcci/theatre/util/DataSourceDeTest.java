package im2ag.m2pcci.theatre.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Mock Object pour simuler la DataSource qui dans l'applciation web est gérée
 * par Tomcat.
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class DataSourceDeTest implements DataSource {

    private static String jdbcDriver = "org.postgresql.Driver"; // pilote Postgres
    // "oracle.jdbc.OracleDriver";  //pilote Oracle
    // "com.mysql.jdbc.Driver"; // pilote my_sql

    private static String dbURL = "jdbc:postgresql://localhost:5433/testTheatre"; // url postgres, 
    // attention le numérode port pour postgres n'est pas standard, par défaut c'est 5432
    //"jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:ufrima"; // url oracle
    // "jdbc:mysql://localhost/notes";  //url mysql

    private String user = "postgres"; //VOTRE_LOGIN;

    private String passwd = "postgres"; //VOTRE_MOT_DE_PASSE;

    /**
     * Creates a new instance of TestDataSource
     *
     * @param user
     * @param passwd
     */
    public DataSourceDeTest(String user, String passwd) {
        try {
            Class.forName(jdbcDriver);
            this.user = user;
            this.passwd = passwd;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé");
            System.exit(0);
        }
    }

    public DataSourceDeTest() {
        try {
            Properties options = new Properties();
            InputStream in = getClass().getResourceAsStream("jdbc.properties");
            options.load(in);
            jdbcDriver = options.getProperty("jdbcDriver");
            dbURL = options.getProperty("dataBaseUrl");
            user = options.getProperty("userName");
            passwd = options.getProperty("passwd");
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouvé");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Problème chargement du fichier jdbc.properties");
            System.exit(0);
        }
    }

    /**
     * @return une connexion "normale " (pas gérée avec un pool de connexions")
     * @throws java.sql.SQLException
     */
    @Override
    public Connection getConnection() throws java.sql.SQLException {
        return DriverManager.getConnection(dbURL, user, passwd);
    }

    // les autre méthodes de l'interface javax.sql.DataSource ne font rien
    // à part lancer une exception. Ne sont jamais invoquées dans les tests.
    @Override
    public void setLoginTimeout(int seconds) throws java.sql.SQLException {
        throw new SQLException("NYI");
    }

    @Override
    public void setLogWriter(java.io.PrintWriter out) throws java.sql.SQLException {
        throw new SQLException("NYI");
    }

    @Override
    public int getLoginTimeout() throws java.sql.SQLException {

        throw new SQLException("NYI");
    }

    @Override
    public java.io.PrintWriter getLogWriter() throws java.sql.SQLException {

        throw new SQLException("NYI");
    }

    @Override
    public java.sql.Connection getConnection(String username, String password) throws java.sql.SQLException {
        throw new SQLException("NYI");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("NYI");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("NYI");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * un petit programme pour tester si la datasource marche. Ouvre et ferme
     * une connexion.
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        DataSource ds = new DataSourceDeTest();
        System.out.println("DataSource OK");
        Connection con = ds.getConnection();
        System.out.println("connexion OK");
        con.close();
        System.out.println("connexion fermée");
    }

}
