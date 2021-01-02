package fit.biepjv.agilediary.jdbc.DAO;

import fit.biepjv.agilediary.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDAO extends BasicDAO {

    public ThemeDAO(){
        createTable();
    }

    @Override
    public void createTable() {
        String sqlQuery = "create table if not exists themes (" +
                "id int primary key," +
                "theme_name varchar(50)," +
                "theme_des varchar (200))";

        executeQuery(sqlQuery);
    }

    private Theme createTheme(ResultSet rs){
        Theme theme = new Theme();
        try{
            theme.setName(rs.getString("theme_name"));
            theme.setDescription(rs.getString("theme_des"));
        }
        catch (SQLException ignored) { }
        return theme;
    }

    public List<Theme> getThemes(){
        String sqlQuery = "Select * from themes";
        List<Theme> themes = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection
                    (DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                Theme theme = createTheme(rs);
                themes.add(theme);
            }
            rs.close();
            con.close();
        }
        catch (ClassNotFoundException | SQLException ex){ }
        return themes;
    }

    public void createTheme(Theme theme){
        String sqlQuery = "insert into themes (theme_name,theme_des) " +
                "values (" + theme.getName() + "," + theme.getDescription() + ")";

        executeQuery(sqlQuery);
    }
}
