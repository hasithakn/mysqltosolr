package com.hsenid.sqltosolr.Dao;

import com.hsenid.sqltosolr.Entity.MessageHistory;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageHistoryDaoTest {

    @Test
    public void get() {
    }

    @Test
    public void getRange() {
        MessageHistoryDao messageHistoryDao =new MessageHistoryDao();
        ArrayList<MessageHistory> range = messageHistoryDao.getRange(100000, 1);
        MessageHistory messageHistory = range.get(0);
        Timestamp receive_date = messageHistory.getReceive_date();
        System.out.println(receive_date);
    }

    @Test
    public void getSize() {
        MessageHistoryDao messageHistoryDao =new MessageHistoryDao();
        int size = messageHistoryDao.getSize();
        System.out.println(size);
    }
}