import com.hsenid.sqltosolr.Dao.MessageHistoryDao;
import com.hsenid.sqltosolr.Entity.MessageHistory;

import java.util.ArrayList;

public class Main {

    public static void main(final String[] args) throws Exception {
        MessageHistoryDao messageHistoryDao = new MessageHistoryDao();
//        messageHistoryDao.get();

        int rows = messageHistoryDao.getSize();
        int batchSize = 1000000;
        final int i = rows / batchSize;
        int r = 0;
        for (int j = 0; j < i; j++) {
            ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, batchSize);
            //todo filter and index
            System.out.println("get " + r + " ," + batchSize);
            r = r + batchSize;
        }
        ArrayList<MessageHistory> range = messageHistoryDao.getRange(r, (rows % batchSize));
        //todo filter and index
        System.out.println("get " + r + " ," + (rows % batchSize));
    }


}