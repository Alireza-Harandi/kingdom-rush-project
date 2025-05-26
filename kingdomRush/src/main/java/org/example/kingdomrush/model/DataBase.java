package org.example.kingdomrush.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {
    private static DataBase dataBase;

    private DataBase(){}

    public static DataBase getDataBase(){
        if(dataBase==null)
            dataBase = new DataBase();
        return dataBase;
    }

    private final String url = "jdbc:mysql://localhost/kingdomrushdb";
    private final String userName = "root";
    private final String password = "alireza1383";

    public boolean executeSQL(String sqlCmd){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,userName,password);
            Statement statement = connection.prepareStatement(sqlCmd);
            statement.execute(sqlCmd);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public ResultSet executeQuery(String sqlcmd){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,userName,password);
            Statement statement = connection.prepareStatement(sqlcmd);
            ResultSet rs = statement.executeQuery(sqlcmd);
            return rs;
        }catch (Exception ex){
            return null;
        }
    }
}



