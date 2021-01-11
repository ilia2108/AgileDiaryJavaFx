package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

/** <b>Epic DAO</b>
 * This class represents tha Data Access Object (DAO) of Epic entity
 */
public class EpicDAO extends BasicDAO{

    /**
     * Method that creates Epic object from ResultSet (result of query)
     * @param rs ResultSet of executed query
     * @return Epic object
     */
    public static Epic createEpic(ResultSet rs){
        Epic epic = new Epic();
        Calendar time = Calendar.getInstance();
        try {
            if(rs.next()) {
                epic.setPriority(rs.getInt("priority"));
                epic.setName(rs.getString("issue_name"));
                epic.setDescription(rs.getString("issue_des"));
                epic.addAssignee(rs.getString("assignee"));
                epic.addTheme(ThemeDAO.findById(rs.getInt("theme_id")));

                time.setTime(rs.getDate("due_date"));
                epic.setDueDate(time);

                epic.setSubIssues(findIncludedStories(rs.getInt("issue_id")));

            }
            return epic;
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        return new Epic();
    }

    /**
     * Method that checks if epicExists
     * @param epicName Name of epic
     * @return True if epic exists or false if not
     * @throws SQLException Error in DB
     */
    public boolean epicExists(String epicName) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "select * from issues where issue_type=epic and issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, epicName);
            ResultSet rs = statement.executeQuery();

            List<Epic> epics = new ArrayList<>();
            while(rs.next()){
                epics.add(createEpic(rs));
            }

            rs.close();
            return !epics.isEmpty();
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
     * Method that gets all epics from DB
     * @return List of epics
     * @throws SQLException Error in DB
     */
    public List<Epic> getEpics() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "select * from issues where issue_type=epic";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            List<Epic> epics = new ArrayList<>();
            while(rs.next()){
                epics.add(createEpic(rs));
            }

            rs.close();
            return epics;
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
     * Method that gets ID of Epic object
     * @param epic Epic object
     * @return Integer ID of the given object
     * @throws SQLException Error in DB
     */
    public static int getId(Epic epic) throws SQLException{
        int result = -1;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select issue_id from issues where issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, epic.getName());

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
     * Method that finds all included stories in the epic
     * @param epicId Interger ID if the epic in DB
     * @return List of Story
     * @throws SQLException Error in DB
     */
    public static List<Story> findIncludedStories(int epicId) throws SQLException {
        List<Story> result = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            String query = "select child_issue_id from issues_relations where mother_issue_id = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(epicId));

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                result.add(StoryDAO.craeteStory(
                        BasicDAO.findIssueById(rs.getInt("child_issue_id")))
                );
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
     * Method that adds story to the epic
     * @param epic Epic object
     * @param story Story object
     * @throws SQLException Error in DB
     */
    public void addStory(Epic epic, Story story) throws SQLException {
        int storyId = StoryDAO.getId(story);
        int epicId = getId(epic);
        addRelation(epicId, storyId);
    }
}
