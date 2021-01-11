package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.jdbc.DatabaseConnector;
import fit.biepjv.agilediary.models.Story;
import fit.biepjv.agilediary.models.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

public class StoryDAO extends BasicDAO{

    public static Story craeteStory(ResultSet rs){
        Story story = new Story();
        Calendar time = Calendar.getInstance();
        try{
            if(rs.next()) {
                story.setPriority(rs.getInt("priority"));
                story.setName(rs.getString("issue_name"));
                story.setDescription(rs.getString("issue_des"));
                story.addAssignee(rs.getString("assignee"));
                story.addTheme(ThemeDAO.findById(rs.getInt("theme_id")));

                time.setTime(rs.getDate("due_date"));
                story.setDueDate(time);
            }
        }
        catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        return story;
    }

    public boolean storyExists(String name) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select * from issues where issue_type=story and issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, name);
            ResultSet rs = statement.executeQuery();
            List<Story> result = new ArrayList<>();
            while(rs.next()){
                result.add(craeteStory(rs));
            }
            rs.close();
            return !result.isEmpty();
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
    public void addStory(Story story) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "insert into issues(issue_name, issue_des, issue_type, due_date, priority, assignee, theme)" +
                    "values (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            int counter = 1;
            statement.setString(counter++, story.getName());
            statement.setString(counter++, story.getDescription());
            statement.setString(counter++, "story");
            statement.setString(counter++, story.getDueDate().toString());
            statement.setString(counter++, story.getPriority().toString());
            statement.setString(counter++, story.getAssignees().get(0));
            statement.setString(counter, Integer.toString(
                    ThemeDAO.getId(story.getThemes().get(0)))
            );

            statement.executeUpdate();
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
    }
    public List<Story> getStories() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select * from issues where issue_type=story";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<Story> result = new ArrayList<>();
            while(rs.next()){
                result.add(craeteStory(rs));
            }
            rs.close();
            return result;
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

    public static int getId(Story story) throws SQLException{
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select issue_id from issues where issue_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, story.getName());

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
}
