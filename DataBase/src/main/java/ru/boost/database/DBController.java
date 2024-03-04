/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.boost.database;

import java.sql.*;

/**
 *
 * @author borisost
 */
public class DBController {

    private static DBController instance;

    public static DBController getInstance(){
        if(instance == null){
            instance = new DBController();
        }
        return instance;
    }

    private DBController(){
        Connection connection = getConnection();
        if(connection == null){
            try {
                createDB();
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private DBConfig config(){
        return DBConfig.getInstance();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(  config().getURL() + config().getDBName(),
                                                    config().getDBUser(),
                                                    config().getDBPassword());
        }
        catch (SQLException e){
            System.out.println("DBController:getDBConnection():SQLException");
            return null;
        }
    }

    public void createDB() throws SQLException{
        try {
            Connection conn = DriverManager.getConnection(  config().getURL(),
                                                            config().getDBUser(),
                                                            config().getDBPassword());
            Statement stmt = conn.createStatement();
            String createDatabaseSql = "CREATE DATABASE "+DBConfig.getInstance().getDBName();
            stmt.executeUpdate(createDatabaseSql);
        }
        catch (SQLException e){
            System.out.println("DBController:createDB():SQLException: can't find database management system!");
            throw e;
        }
    }

    public boolean isTableExists(String tableName){
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String checkTableSql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = '" + tableName + "')";
            System.out.println(checkTableSql);
            ResultSet rs = stmt.executeQuery(checkTableSql);
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException e) {
            System.err.println(e);
        }
        System.out.println("DBController:isTableExists(): FALSE");
        return false;
    }

    public void createTable(String createTableSql) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(config().getURL() + config().getDBName(),
                    config().getDBUser(),
                    config().getDBPassword());
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableSql);
        } catch (SQLException e) {
            System.out.println("DBController:createTable():SQLException");
            throw e;
        }
    }

    public static void main(String[] args) {
        DBController.getInstance().getConnection();
    }
}
