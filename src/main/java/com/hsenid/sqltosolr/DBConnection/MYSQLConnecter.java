package com.hsenid.sqltosolr.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MYSQLConnecter {

    private static Connection connection = null;

    private MYSQLConnecter() {
    }

    public static Connection getConnection()  {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kite?zeroDateTimeBehavior=convertToNull", "root", "beyondm");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
