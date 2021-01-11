package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**<b>Theme DAO class</b>
 * This class represents Data Access Object (DAO) fot themes.
 */
public class ThemeDAO extends BasicDAO {

    /**
     * Method that creates Model from {@code ResultSet}
     * @param rs ResultSet after the execution
     * @return Theme object
     */
    private static Theme createTheme(ResultSet rs){
        Theme theme = new Theme();
        try{
            theme.setName(rs.getString("theme_name"));
            theme.setDescription(rs.getString("theme_des"));
        }
        catch (SQLException ignored) { }
        return theme;
    }

    /**
     * Method that checks if theme exists
     * @param themeName Name of the theme
     * @return True if theme exists and false if not
     * @throws SQLException Error in DB
     */
    public boolean themeExists(String themeName) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        List<Theme> themes = new ArrayList<>();

        try{
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "Select * from themes where theme_name = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter++, themeName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                themes.add(createTheme(rs));
            }
            rs.close();
            return !themes.isEmpty();

        } catch (SQLException e){
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
     * Method that returns list of themes
     * @return List of themes
     * @throws SQLException Error in DB
     */
    public List<Theme> getThemes() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        List<Theme> themes = new ArrayList<>();

        try{
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "Select * from themes ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                themes.add(createTheme(rs));
            }
            rs.close();

        } catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            if(connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }

        return themes;
    }

    /**
     * Method that adds theme to the DB
     * @param theme Theme to add
     * @throws SQLException Error in DB
     */
    public void addTheme(Theme theme) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = getDBConnection();
            String query = "insert into themes (theme_name, theme_des) values (?, ?)";
            statement = connection.prepareStatement(query);
            int counter = 1;

            statement.setString(counter++, theme.getName());
            statement.setString(counter, theme.getDescription());

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

    /**
     * Method that finds theme by ID
     * @param id Integer ID in DB
     * @return Theme that was found.
     * @throws SQLException Eror in DB
     */
    public static Theme findById(int id) throws SQLException{
        Theme result = new Theme();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            String query = "select * from themes where theme_id = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(id));

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                result = createTheme(rs);
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
     * Method that gets ID of the theme object
     * @param theme Theme object
     * @return Integer ID
     * @throws SQLException Error in DB
     */
    public static int getId(Theme theme) throws SQLException{
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            String query = "select issue_id from themes where theme_name=?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter, theme.getName());

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
