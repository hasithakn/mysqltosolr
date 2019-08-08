package com.hsenid.sqltosolr.Logics;

import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;
import com.hsenid.sqltosolr.Entity.SolrEntity;

import java.sql.Timestamp;
import java.util.StringTokenizer;

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

    public SolrEntity sqlToSolrEntity(MessageHistory messageHistory) {
        Timestamp receive_date = messageHistory.getReceive_date();
        if (receive_date != null) {
            FilterLogic filterLogic = new FilterLogic();
            SolrEntity solrEntity = new SolrEntity();
            solrEntity.setApp_id(messageHistory.getApp_id());
            solrEntity.setSms(messageHistory.getMessage());
            solrEntity.setTimestamp(messageHistory.getCorrelation_id());
            solrEntity.setDatetime(filterLogic.timestampToISO(receive_date));
            solrEntity.setTermCount(getTermsCount(messageHistory.getMessage()));
            return solrEntity;
        } else {
            return null;
        }
    }

    private int getTermsCount(String message) {
        return new StringTokenizer(message, " ").countTokens();
    }
}
