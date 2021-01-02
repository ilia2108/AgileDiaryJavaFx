package fit.biepjv.agilediary.jdbc;

import fit.biepjv.agilediary.controllers.ThemeController;
import fit.biepjv.agilediary.jdbc.DAO.BasicDAO;
import fit.biepjv.agilediary.jdbc.DAO.InitiativeDAO;
import fit.biepjv.agilediary.jdbc.DAO.ThemeDAO;
import fit.biepjv.agilediary.models.Theme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    ThemeDAO themeDAO;
    InitiativeDAO initiativeDAO;

    public DatabaseConnector(){
        themeDAO = new ThemeDAO();
        initiativeDAO =  new InitiativeDAO();
    }

    public List<ThemeController> getThemeControllers() throws SQLException {
        List<ThemeController> result = new ArrayList<>();
        for(Theme theme: themeDAO.getThemes()){
            result.add(new ThemeController(theme));
        }
        return result;
    }

    public ThemeDAO getThemeDAO(){
        return themeDAO;
    }
    public InitiativeDAO getInitiativeDAO(){
        return initiativeDAO;
    }
}
