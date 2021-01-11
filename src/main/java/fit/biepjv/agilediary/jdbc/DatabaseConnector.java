package fit.biepjv.agilediary.jdbc;

import com.sun.javaws.exceptions.InvalidArgumentException;
import fit.biepjv.agilediary.controllers.*;
import fit.biepjv.agilediary.jdbc.DAO.*;
import fit.biepjv.agilediary.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {
    protected static final Logger logger = Logger.getLogger(BasicDAO.class.getName());
    ThemeDAO themeDAO;
    InitiativeDAO initiativeDAO;
    EpicDAO epicDAO;
    StoryDAO storyDAO;

    public DatabaseConnector(){
        themeDAO = new ThemeDAO();
        initiativeDAO =  new InitiativeDAO();
        epicDAO = new EpicDAO();
        storyDAO = new StoryDAO();
    }

    public List<ThemeController> getThemeControllers() throws SQLException {
        List<ThemeController> result = new ArrayList<>();
        for(Theme theme: themeDAO.getThemes()){
            result.add(new ThemeController(theme));
        }
        return result;
    }

    public List<InitiativeController> getInitiativeControllers() throws SQLException{
        List<InitiativeController> result = new ArrayList<>();
        for(Initiative initiative: initiativeDAO.getInitiatives()){
            result.add(new InitiativeController(initiative));
        }
        return result;
    }

    public void addRelation(IssueControllerAbstract mother, IssueControllerAbstract child)
            throws SQLException, InvalidArgumentException{
        if(mother instanceof InitiativeController && child instanceof EpicController)
            initiativeDAO.addEpic((Initiative) mother.getIssue(), (Epic) child.getIssue());
        else if (mother instanceof EpicController && child instanceof StoryController)
            epicDAO.addStory((Epic) mother.getIssue(), (Story) child.getIssue());
        else throw new InvalidArgumentException(new String[]{
                "no such relation could be added between " + mother.getClass().toString() +" and "
                    + child.getClass().toString()
        });
    }
    public boolean themeExists(String name) throws SQLException{
        return themeDAO.themeExists(name);
    }
    public boolean initiativeExists(String name) throws SQLException{
        return initiativeDAO.initiativeExists(name);
    }
    public boolean epicExists(String name) throws SQLException{
        return epicDAO.epicExists(name);
    }
    public boolean storyExists(String name) throws SQLException{
        return storyDAO.storyExists(name);
    }



    public void addTheme(Theme theme) throws SQLException{
        themeDAO.addTheme(theme);
    }
    public void addInitiative(InitiativeController initiativeController) throws SQLException{
        addIssue(initiativeController, "initiative");
    }
    public void addEpic(EpicController epicController) throws SQLException{
        addIssue(epicController, "epic");
    }
    public void addStory(StoryController storyController) throws SQLException{
        addIssue(storyController, "story");
    }

    public void addIssue(IssueControllerAbstract issue, String type) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = BasicDAO.getDBConnection();
            String query = "insert into issues(issue_name, issue_des, issue_type, due_date, priority, assignee, theme_id)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            int counter = 1;
            statement.setString(counter++, issue.getName());
            statement.setString(counter++, issue.getDescription());
            statement.setString(counter++, type);
            statement.setString(counter++, issue.getCalendarString());
            statement.setString(counter++, issue.getPriority().toString());
            statement.setString(counter++, issue.getAssignees().get(0));
            statement.setString(counter, Integer.toString(
                    ThemeDAO.getId(issue.getThemes().get(0)))
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
}
