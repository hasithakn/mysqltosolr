package com.hsenid.sqltosolr.FileIO;


import com.hsenid.sqltosolr.Dao.CSVDataDao;
import com.hsenid.sqltosolr.Entity.CSVData;
import com.hsenid.sqltosolr.Logics.FilterLogic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.solr.client.solrj.SolrServerException;

public class FileRead {
    public FileRead() {
    }

    public ArrayList<String> readAndFilter(String path) {
        ArrayList<String> strings = new ArrayList();
        FilterLogic filterLogic = new FilterLogic();

        try {
            BufferedReader br = this.read(path);

            while(true) {
                String line;
                while((line = br.readLine()) != null) {
                    boolean pipesCheck = filterLogic.checkForPipes(line);
                    boolean lastDigitCheck = filterLogic.checkForLastDigit(line);
                    if (pipesCheck && lastDigitCheck) {
                        strings.add(line);
                    } else {
                        System.out.println(line);
                    }
                }

                return strings;
            }
        } catch (IOException var8) {
            var8.printStackTrace();
            return strings;
        }
    }

    private BufferedReader read(String path) throws FileNotFoundException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        return new BufferedReader(fr);
    }

    public void readAndIndexFile(String path, String core) throws IOException, SolrServerException {
        FilterLogic filterLogic = new FilterLogic();
        CSVDataDao csvDataDao = new CSVDataDao();
        ArrayList<CSVData> csvDataArrayList = new ArrayList();
        BufferedReader br = this.read(path);
        int count = 0;

        String line;
        while((line = br.readLine()) != null) {
            boolean pipesCheck = filterLogic.checkForPipes(line);
            boolean lastDigitCheck = filterLogic.checkForLastDigit(line);
            if (pipesCheck && lastDigitCheck) {
                String[] fields = filterLogic.getFields(line);
                if (!fields[0].equals("app_id") && !fields[2].equals("timestamp")) {
                    CSVData csvData1 = new CSVData();
                    csvData1.setApp_id(fields[0]);
                    csvData1.setSms(fields[1]);
                    csvData1.setTimestamp(fields[2]);
                    csvData1.setDatetime(fields[3]);
                    csvDataArrayList.add(csvData1);
                    ++count;
                }
            }
        }

        csvDataDao.indexList(csvDataArrayList, core);
        System.out.println(count + " indexed");
    }
}
