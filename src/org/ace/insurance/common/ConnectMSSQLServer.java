package org.ace.insurance.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class ConnectMSSQLServer {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection =
                DriverManager.getConnection("jdbc:sqlserver://acehawksvr:1433;databaseName=ggip_20140715Mirror", "sa", "sa");

            System.out.println("DATABASE NAME IS:" + connection.getMetaData().getDatabaseProductName());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT NAME FROM ADDON");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
