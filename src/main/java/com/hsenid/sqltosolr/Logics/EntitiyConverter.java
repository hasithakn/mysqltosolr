package com.hsenid.sqltosolr.Logics;

import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;

import java.sql.Timestamp;

public class EntitiyConverter {

    public CSVData sqlToSolr(MessageHistory messageHistory) {

        Timestamp receive_date = messageHistory.getReceive_date();
        if (receive_date != null) {

            FilterLogic filterLogic = new FilterLogic();
            CSVData csvData = new CSVData();
            csvData.setApp_id(messageHistory.getApp_id());
            csvData.setSms(messageHistory.getMessage());
            csvData.setTimestamp(messageHistory.getCorrelation_id());
            csvData.setDatetime(filterLogic.timestampToISO(receive_date));
            return csvData;
        } else {
            return null;
        }


    }
}
