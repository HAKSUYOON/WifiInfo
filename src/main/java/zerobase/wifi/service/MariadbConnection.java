package zerobase.wifi.service;

import java.sql.*;

public class MariadbConnection {

    public static Connection getConnect() {
    	
    	String url = "jdbc:mariadb://localhost:3306/wifi_db";
        String dbUserId = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        return connection;

    }
    
    public static void close(ResultSet rs, PreparedStatement preparedStatement, Connection connection) {
    	
    	try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
}
