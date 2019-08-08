package com.hsenid.sqltosolr;

import com.hsenid.sqltosolr.Dao.CSVDataDao;
import com.hsenid.sqltosolr.Dao.MessageHistoryDao;
import com.hsenid.sqltosolr.Dao.SolrEntityDao;
import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;
import com.hsenid.sqltosolr.Entity.SolrEntity;
import com.hsenid.sqltosolr.Logics.EntitiyConverter;
import com.hsenid.sqltosolr.Logics.FilterLogic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TermCount {
    private static final Logger LOGGER = Logger.getLogger(TermCount.class.getName());

    public static void main(final String[] args) throws Exception {


        MessageHistoryDao messageHistoryDao = new MessageHistoryDao();

        SolrEntityDao solrEntityDao = new SolrEntityDao();
        String core = "experiment5";
        int rows = messageHistoryDao.getSize();
        int batchSize = 250000;
        final int i = rows / batchSize;
        int r = 0;
        for (int j = 0; j < i; j++) {
            ArrayList<MessageHistory> messageHistories = messageHistoryDao.getRange(r, batchSize);
            ArrayList<SolrEntity> solrEntities = getSolrDataFromSQL(messageHistories);

            solrEntityDao.indexList(solrEntities, core);
            solrEntityDao.commitdata(core);
            LOGGER.log(Level.INFO, "get " + r + " ," + batchSize);
            r = r + batchSize;
        }
        ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, (rows % batchSize));
        ArrayList<SolrEntity> solrEntities = getSolrDataFromSQL(range);

        solrEntityDao.indexList(solrEntities, core);
        solrEntityDao.commitdata(core);

        LOGGER.log(Level.INFO, "get " + r + " ," + (rows % batchSize));
    }

    private static ArrayList<SolrEntity> getSolrDataFromSQL(ArrayList<MessageHistory> messageHistories) {
        FilterLogic filterLogic = new FilterLogic();
        EntitiyConverter entitiyConverter = new EntitiyConverter();
        ArrayList<SolrEntity> solrEntities = new ArrayList<>();
        for (MessageHistory messageHistory : messageHistories) {
            SolrEntity solrEntity = entitiyConverter.sqlToSolrEntity(messageHistory);
            if (solrEntity != null) {
                //filter before add
                String sms = solrEntity.getSms();
                sms = filterLogic.removeNewline(sms);
                sms = filterLogic.removeBackSlash(sms);
                sms = filterLogic.removeBackSlashR(sms);
                sms = filterLogic.removeTab(sms);
                solrEntity.setSms(sms);
                //
                solrEntities.add(solrEntity);
            }
        }
        return solrEntities;
    }

}
