package fit.biepjv.agilediary.jdbc.DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BasicDAO {
    protected static final Logger logger = Logger.getLogger(BasicDAO.class.getName());
    public static String DB_NAME = "agileDiary";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/"+DB_NAME;
    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "somesecret";

    public static Connection getDBConnection() throws SQLException {
        Connection connection = null;

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }

        return connection;
    }
}
