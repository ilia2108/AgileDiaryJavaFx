package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.Epic;
import fit.biepjv.agilediary.models.Initiative;
import fit.biepjv.agilediary.models.Story;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

public class InitiativeDAO extends BasicDAO {
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
    public void addInitiative(Initiative initiative) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "insert into issues(issue_name, issue_des, issue_type, due_date, priority, assignee, theme_id)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            int counter = 1;
            statement.setString(counter++, initiative.getName());
            statement.setString(counter++, initiative.getDescription());
            statement.setString(counter++, "initiative");
            statement.setString(counter++, initiative.getCalendarString());
            statement.setString(counter++, initiative.getPriority().toString());
            statement.setString(counter++, initiative.getAssignees().get(0));
            int themeId = ThemeDAO.getId(initiative.getThemes().get(0));
            statement.setString(counter++, Integer.toString(themeId));

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
    public List<Initiative> getInitiatives() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "select * from issues where issue_type=initiative";
            statement = connection.prepareStatement(query);
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

    public List<Epic> findIncludedStories(int initiativeId) throws SQLException {
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

            if(rs.next()){
                result.add(EpicDAO.createEpic(
                        EpicDAO.findIssueById(rs.getInt("issue_id")))
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

    public static int getId(Initiative initiative) throws SQLException{
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select issue_id in issues where issue_name=?";
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

    public void addEpic(Initiative initiative, Epic epic) throws SQLException {
        int initiativeId = getId(initiative);
        int epicId = EpicDAO.getId(epic);
        addRelation(initiativeId, epicId);
    }
}
