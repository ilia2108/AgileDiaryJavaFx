package fit.biepjv.agilediary.jdbc.DAO;

import com.sun.rowset.CachedRowSetImpl;
import fit.biepjv.agilediary.models.IssueAbstract;

import javax.script.ScriptEngine;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BasicDAO {
    protected static final Logger logger = Logger.getLogger(BasicDAO.class.getName());
    public static String DB_NAME = "agileDiary";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME;
    //    public static final String DRIVER =
//            "com.mysql.jdbc.Driver";
    public static final String DRIVER =
            "com.mysql.cj.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "somesecret";
    //public static final String PASS = "password";

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

    public static void addRelation(int motherId, int childId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            //connection.setAutoCommit(false);
            String query = "insert into issues_relations(mother_issue_id, child_issue_id) values(?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(motherId));
            statement.setString(2, Integer.toString(childId));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
        }
    }

    public static ResultSet findIssueById(int issueId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        IssueAbstract issue;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select * from issues where issue_id = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(issueId));
            ResultSet rs = statement.executeQuery();
            //connection.close();
            //statement.close();
            return rs;
        } catch (SQLException e) {
        }

//        } finally {
//            if (connection != null)
//                connection.close();
//            if (statement != null)
//                statement.close();
//        }
        return null;
    }

}
