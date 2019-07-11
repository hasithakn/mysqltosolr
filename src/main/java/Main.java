import com.hsenid.sqltosolr.Dao.CSVDataDao;
import com.hsenid.sqltosolr.Dao.MessageHistoryDao;
import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;
import com.hsenid.sqltosolr.Logics.EntitiyConverter;

import java.util.ArrayList;

public class Main {

    public static void main(final String[] args) throws Exception {
        MessageHistoryDao messageHistoryDao = new MessageHistoryDao();
        EntitiyConverter entitiyConverter = new EntitiyConverter();
        CSVDataDao csvDataDao = new CSVDataDao();

//        messageHistoryDao.get();

        int rows = messageHistoryDao.getSize();
        int batchSize = 250000;
        final int i = rows / batchSize;
        int r = 0;
        for (int j = 0; j < i; j++) {
            ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, batchSize);
            ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
            for (MessageHistory messageHistory : range) {
                CSVData csvData = entitiyConverter.sqlToSolr(messageHistory);
                if (csvData != null) {
                    csvDataArrayList.add(csvData);
                }

            }
            csvDataDao.indexList(csvDataArrayList, "csv_core2");
            csvDataDao.commitdata("csv_core2");
            System.out.println("get " + r + " ," + batchSize);
            r = r + batchSize;
        }
        csvDataDao.commitdata("csv_core2");
        ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, (rows % batchSize));
        ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
        for (MessageHistory messageHistory : range) {
            CSVData csvData = entitiyConverter.sqlToSolr(messageHistory);
            if (csvData != null) {
                csvDataArrayList.add(csvData);
            }
        }
        csvDataDao.indexList(csvDataArrayList, "csv_core2");


        System.out.println("get " + r + " ," + (rows % batchSize));
    }


}