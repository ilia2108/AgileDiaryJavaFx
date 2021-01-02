package fit.biepjv.agilediary.jdbc;

import fit.biepjv.agilediary.jdbc.DAO.InitiativeDAO;
import fit.biepjv.agilediary.jdbc.DAO.ThemeDAO;

public class DatabaseConnector {
    ThemeDAO themeDAO;
    InitiativeDAO initiativeDAO;

    public DatabaseConnector(){
        themeDAO = new ThemeDAO();
        initiativeDAO =  new InitiativeDAO();
    }

    public ThemeDAO getThemeDAO(){
        return themeDAO;
    }
    public InitiativeDAO getInitiativeDAO(){
        return initiativeDAO;
    }
}
