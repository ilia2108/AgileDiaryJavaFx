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

public class EpicDAO extends BasicDAO{

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

    public void addStory(Epic epic, Story story) throws SQLException {
        int storyId = StoryDAO.getId(story);
        int epicId = getId(epic);
        addRelation(epicId, storyId);
    }
}
