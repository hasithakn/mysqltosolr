package com.hsenid.sqltosolr.Dao;

import com.hsenid.sqltosolr.DBConnection.MYSQLConnecter;
import com.hsenid.sqltosolr.Entity.MessageHistory;

import java.sql.*;
import java.util.ArrayList;

public class MessageHistoryDao {

    public ArrayList<MessageHistory> get() {
        Connection con = MYSQLConnecter.getConnection();
        PreparedStatement statement = null;

        String queryStatement = "SELECT * FROM analysis1 limit ";
        String noOfRows = "SELECT count(*) as a FROM message_history";

        try {
            statement = con.prepareStatement(noOfRows);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int rows = Integer.parseInt(rs.getObject("a").toString());

            int batchSize = 5000000;

            final int i = rows / batchSize;
            int r = 0;

            for (int j = 0; j < i; j++) {
                String query = queryStatement + r + "," + (batchSize);
                System.out.println(query);
                processRows(con, query);
                r = r + batchSize;
            }
            String query = queryStatement + r + "," + (rows % batchSize);
            System.out.println(query);
            ArrayList<MessageHistory> messageHistories = processRows(con, query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MessageHistory> getRange(int from, int noOfRows) {
        Connection con = MYSQLConnecter.getConnection();
        PreparedStatement statement = null;
        ArrayList<MessageHistory> messageHistories = new ArrayList<>();
        String queryStatement = "SELECT * FROM message_history limit " + from + "," + noOfRows;
        try {
            System.out.println(queryStatement);
            messageHistories = processRows(con, queryStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageHistories;
    }

    public int getSize() {
        Connection con = MYSQLConnecter.getConnection();
        PreparedStatement statement = null;
        String noOfRows = "SELECT count(*) as a FROM message_history";

        int rows = 0;
        try {
            statement = con.prepareStatement(noOfRows);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rows = Integer.parseInt(rs.getObject("a").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rows;
    }


    private ArrayList<MessageHistory> processRows(Connection con, String query) throws SQLException {
        PreparedStatement statement;
        ResultSet rs;
        statement = con.prepareStatement(query);
        statement.setFetchSize(Integer.MIN_VALUE);
        rs = statement.executeQuery();
        ArrayList<MessageHistory> messageHistories = new ArrayList<MessageHistory>();
        while (rs.next()) {
            String app_id = rs.getString("app_id");
            String message = rs.getString("message");
            String correlation_id = rs.getString("correlation_id");
            Timestamp receive_date = rs.getTimestamp("receive_date");
            MessageHistory messageHistory = new MessageHistory();
            messageHistory.setApp_id(app_id);
            messageHistory.setCorrelation_id(correlation_id);
            messageHistory.setMessage(message);
            messageHistory.setReceive_date(receive_date);
            messageHistories.add(messageHistory);
        }
        return messageHistories;
    }
}
