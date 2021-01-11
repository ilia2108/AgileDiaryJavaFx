package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.IssueAbstract;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**<b>Basic DAO class</b>
 * This class represents the basic Data Access Object (DAO).
 * It contains all information that is used by its children
 */
public abstract class BasicDAO {
    /**
     * Logger
     */
    protected static final Logger logger = Logger.getLogger(BasicDAO.class.getName());

    /**
     * Name of the database
     */
    static String DB_NAME = "agileDiary";

    /**
     * URL to the database
     */
    static final String DB_URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME;


    /**
     * Name of the JDBC driver class
     */
    static final String DRIVER =
            "com.mysql.cj.jdbc.Driver";


    /**
     * User login
     */
    static final String USER = "root";

    /**
     * Password to access DB
     */
    static final String PASS = "somesecret";

    /**
     * Method that gets basic connection to MySQL database
     * @return
     * @throws SQLException Error in DB
     */
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

    /**
     * Method that adds relation to the database
     * @param motherId ID integer of the mother issue
     * @param childId ID integer of the child issue
     * @throws SQLException Error in DB
     */
    public static void addRelation(int motherId, int childId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
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

    /**
     * Method that finds issue by id
     * @param issueId id is the issue
     * @return ResultSet of the query
     * @throws SQLException Error in DB
     */
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
