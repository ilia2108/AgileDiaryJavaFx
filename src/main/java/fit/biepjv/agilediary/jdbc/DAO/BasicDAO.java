package fit.biepjv.agilediary.jdbc.DAO;

import java.sql.*;

public abstract class BasicDAO {
    public static String DB_NAME = "agileDiary";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/"+
                    DB_NAME+"?zeroDateTimeBehavior=convertToNull";
    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "secret";

    public abstract void createTable();

    protected void executeQuery(String sqlQuery){
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection
                    (DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.close();
            con.close();
        }
        catch (ClassNotFoundException | SQLException ex){ }
    }
}
