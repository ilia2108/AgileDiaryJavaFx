package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

/**<b>Initiative DAO class</b>
 * This is a Data Access Object (DAO) of Initiative entity.
 */
public class InitiativeDAO extends BasicDAO {

    /**
     * Default constructor
     */
    public InitiativeDAO(){ }

    /**
     * Method that creates an object from result of executed query.
     * @param rs ResultSet from executed query
     * @return Initiative object
     */
    private Initiative createInitiative(ResultSet rs){
        Initiative initiative = new Initiative();
        Calendar time = Calendar.getInstance();
        try {
            initiative.setPriority(rs.getInt("priority"));
            initiative.setName(rs.getString("issue_name"));
            initiative.setDescription(rs.getString("issue_des"));
            initiative.addAssignee(rs.getString("assignee"));
            initiative.addTheme(ThemeDAO.findById(rs.getInt("theme_id")));

            time.setTime(rs.getDate("due_date"));
            initiative.setDueDate(time);

            initiative.setSubIssues(findIncludedStories(rs.getInt("issue_id")));
            return initiative;
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        return initiative;
    }

    /**
     * Method that checks if such an initiative exists
     * @param initiativeName Initiative name
     * @return True if initiative exists or false if not
     * @throws SQLException Error in DB
     */
    public boolean initiativeExists(String initiativeName) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "select * from issues where issue_type=? and issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter++, "initiative");
            statement.setString(counter, initiativeName);
            ResultSet rs = statement.executeQuery();

            List<Initiative> initiatives = new ArrayList<>();
            while(rs.next()){
                initiatives.add(createInitiative(rs));
            }

            rs.close();
            return !initiatives.isEmpty();
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            if(connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }
        return false;
    }

    /**
     * Method that gets all initiatives from DB
     * @return List of all initiatives
     * @throws SQLException Error in DB
     */
    public List<Initiative> getInitiatives() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "select * from issues where issue_type=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, "initiative");

            ResultSet rs = statement.executeQuery();

            List<Initiative> initiatives = new ArrayList<>();
            while(rs.next()){
                initiatives.add(createInitiative(rs));
            }

            rs.close();
            return initiatives;
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            if(connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }
        return new ArrayList<>();
    }

    /**
     * Method that gets all epics that are included in the initiative
     * @param initiativeId Integer ID of the Initiative
     * @return List of epics
     * @throws SQLException Error in DB
     */
    public static List<Epic> findIncludedStories(int initiativeId) throws SQLException {
        List<Epic> result = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select child_issue_id from issues_relations where mother_issue_id = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(initiativeId));

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                ResultSet epicRs = BasicDAO.findIssueById(rs.getInt("child_issue_id"));
                result.add(EpicDAO.createEpic(
                        epicRs)
                );
                epicRs.close();
            }
            rs.close();
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            if(connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }

        return result;
    }

    /**
     * Method that gets ID from DB of the given object
     * @param initiative Initiative object
     * @return Integer ID from DB
     * @throws SQLException Error in DB
     */
    public static int getId(Initiative initiative) throws SQLException{
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select issue_id from issues where issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, initiative.getName());

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                result = rs.getInt("issue_id");
            }
            rs.close();
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            if(connection != null)
                connection.close();
            if(statement != null)
                statement.close();

        }
        return result;
    }

    /**
     * Method that adds epic to the initiative
     * @param initiative Initiative object
     * @param epic Epic object
     * @throws SQLException Error in DB
     */
    public void addEpic(Initiative initiative, Epic epic) throws SQLException {
        int initiativeId = getId(initiative);
        int epicId = EpicDAO.getId(epic);
        addRelation(initiativeId, epicId);
    }
}
