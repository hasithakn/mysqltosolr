package com.hsenid.sqltosolr.Logics;

import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;

public class EntitiyConverter {

    public CSVData sqlToSolr(MessageHistory messageHistory) {
        
        CSVData csvData = new CSVData();
        csvData.setApp_id(messageHistory.getApp_id());
        csvData.setDatetime(messageHistory.getReceive_date().toString());
        csvData.setSms(messageHistory.getMessage());
        csvData.setTimestamp(messageHistory.getCorrelation_id());
        return csvData;

    }
}
