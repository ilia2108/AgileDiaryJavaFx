package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ThemeDAO extends BasicDAO {

    private Theme createTheme(ResultSet rs){
        Theme theme = new Theme();
        try{
            theme.setName(rs.getString("theme_name"));
            theme.setDescription(rs.getString("theme_des"));
        }
        catch (SQLException ignored) { }
        return theme;
    }

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
}
