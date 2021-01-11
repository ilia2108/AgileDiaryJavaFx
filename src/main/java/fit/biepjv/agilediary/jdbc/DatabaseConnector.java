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

/** <b>Database Connection class</b>
 * This class describes connection to MySQL database.
 */
public class DatabaseConnector {
    /**
     * Error logger
     */
    protected static final Logger logger = Logger.getLogger(BasicDAO.class.getName());
    /**
     * DAO to access themes in DB
     */
    ThemeDAO themeDAO;
    /**
     * DAO to access initiatives in DB
     */
    InitiativeDAO initiativeDAO;
    /**
     * DAO to access epics in DB
     */
    EpicDAO epicDAO;
    /**
     * DAO to access stories in DB
     */
    StoryDAO storyDAO;

    /**
     * Default constructor
     */
    public DatabaseConnector(){
        themeDAO = new ThemeDAO();
        initiativeDAO =  new InitiativeDAO();
        epicDAO = new EpicDAO();
        storyDAO = new StoryDAO();
    }

    /**
     * Method that gets all themes that are in DB
     * @return List of Themes that are wrapped into controllers
     * @throws SQLException Error in DB
     */
    public List<ThemeController> getThemeControllers() throws SQLException {
        List<ThemeController> result = new ArrayList<>();
        for(Theme theme: themeDAO.getThemes()){
            result.add(new ThemeController(theme));
        }
        return result;
    }

    /**
     * Method that gets all initiatives that are in DB
     * @return List of initiatives wrapped into controllers
     * @throws SQLException Error in DB
     */
    public List<InitiativeController> getInitiativeControllers() throws SQLException{
        List<InitiativeController> result = new ArrayList<>();
        for(Initiative initiative: initiativeDAO.getInitiatives()){
            result.add(new InitiativeController(initiative));
        }
        return result;
    }

    /**
     * Method that adds relation between two entities.
     * <b>The first issue (mother) will include second one (child)</b>
     * @param mother Controller of mother issue
     * @param child Controller of child issue
     * @throws SQLException Error in DB
     * @throws InvalidArgumentException Project doesn't support such a relations
     */
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

    /**
     * Method that decides if such a theme exists
     * @param name Name of theme
     * @return True if theme exists and false if not
     * @throws SQLException Error in DB
     */
    public boolean themeExists(String name) throws SQLException{
        return themeDAO.themeExists(name);
    }

    /**
     * Method that decides if such an initiative exists
     * @param name Name of initiative
     * @return True if initiative exists and false if not
     * @throws SQLException Error in DB
     */
    public boolean initiativeExists(String name) throws SQLException{
        return initiativeDAO.initiativeExists(name);
    }

    /**
     * Method that decides if such an epic exists
     * @param name Name of initiative
     * @return True if initiative exists and false if not
     * @throws SQLException Error in DB
     */
    public boolean epicExists(String name) throws SQLException{
        return epicDAO.epicExists(name);
    }

    /**
     * Method that decides if such an story exists
     * @param name Name of initiative
     * @return True if initiative exists and false if not
     * @throws SQLException Error in DB
     */
    public boolean storyExists(String name) throws SQLException{
        return storyDAO.storyExists(name);
    }


    /**
     * Method that adds theme to DB
     * @param theme Theme to be added
     * @throws SQLException Error in DB
     */
    public void addTheme(Theme theme) throws SQLException{
        themeDAO.addTheme(theme);
    }

    /**
     * Method that adds initiative to DB
     * @param initiativeController Controller of initiative
     * @throws SQLException Error in DB
     */
    public void addInitiative(InitiativeController initiativeController) throws SQLException{
        addIssue(initiativeController, "initiative");
    }

    /**
     * Method that adds epic to DB
     * @param epicController Controller of epic
     * @throws SQLException Error in DB
     */
    public void addEpic(EpicController epicController) throws SQLException{
        addIssue(epicController, "epic");
    }

    /**
     * Method that adds story to DB
     * @param storyController Controller of story
     * @throws SQLException Error in DB
     */
    public void addStory(StoryController storyController) throws SQLException{
        addIssue(storyController, "story");
    }

    /**
     * Common method to add an issue using the issue itself and its type
     * @param issue Issue instance
     * @param type Issue type
     * @throws SQLException Error in DB
     */
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
