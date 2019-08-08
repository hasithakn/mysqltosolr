import com.hsenid.sqltosolr.Dao.CSVDataDao;
import com.hsenid.sqltosolr.Dao.MessageHistoryDao;
import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Entity.MessageHistory;
import com.hsenid.sqltosolr.Logics.EntitiyConverter;
import com.hsenid.sqltosolr.Logics.FilterLogic;

import java.util.ArrayList;

public class Main {

    public static void main(final String[] args) throws Exception {
        MessageHistoryDao messageHistoryDao = new MessageHistoryDao();

        CSVDataDao csvDataDao = new CSVDataDao();
        String core = "experiment3";
//        messageHistoryDao.get();

        int rows = messageHistoryDao.getSize();
        int batchSize = 250000;
        final int i = rows / batchSize;
        int r = 0;
        for (int j = 0; j < i; j++) {
            ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, batchSize);

            ArrayList<CSVData> csvDataArrayList = getCsvDataFromSQL(range);
            csvDataDao.indexList(csvDataArrayList, core);
            csvDataDao.commitdata(core);

            System.out.println("get " + r + " ," + batchSize);
            r = r + batchSize;
        }
        ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, (rows % batchSize));

        ArrayList<CSVData> csvDataArrayList = getCsvDataFromSQL(range);
        csvDataDao.indexList(csvDataArrayList, core);
        csvDataDao.commitdata(core);

        System.out.println("get " + r + " ," + (rows % batchSize));
    }

    private static ArrayList<CSVData> getCsvDataFromSQL(ArrayList<MessageHistory> range) {
        FilterLogic filterLogic = new FilterLogic();
        EntitiyConverter entitiyConverter = new EntitiyConverter();
        ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
        for (MessageHistory messageHistory : range) {
            CSVData csvData = entitiyConverter.sqlToSolr(messageHistory);
            if (csvData != null) {
                //filter before add
                String sms = csvData.getSms();
                sms = filterLogic.removeNewline(sms);
                sms = filterLogic.removeBackSlash(sms);
                sms = filterLogic.removeBackSlashR(sms);
                sms = filterLogic.removeTab(sms);
                csvData.setSms(sms);
                //
                csvDataArrayList.add(csvData);
            }
        }
        return csvDataArrayList;
    }


}