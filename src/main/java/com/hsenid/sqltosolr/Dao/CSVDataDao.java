package com.hsenid.sqltosolr.Dao;


import com.hsenid.sqltosolr.DBConnection.SOLRDBConnecter;
import com.hsenid.sqltosolr.Entity.CSVData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

public class CSVDataDao {
    final SolrClient client = SOLRDBConnecter.getSolrClient();

    public CSVDataDao() {
    }

    public ArrayList<CSVData> query() throws IOException, SolrServerException {
        ArrayList<CSVData> result = new ArrayList();
        Map<String, String> queryParamMap = new HashMap();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("fl", "*");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
        QueryResponse response = this.client.query("csv_core", queryParams);
        SolrDocumentList documents = response.getResults();
        Iterator var6 = documents.iterator();

        while(var6.hasNext()) {
            SolrDocument a = (SolrDocument)var6.next();
            CSVData dataObject = new CSVData();
            dataObject.setApp_id(a.getFieldValue("app_id").toString());
            dataObject.setSms(a.getFieldValue("sms").toString());
            dataObject.setDatetime(a.getFieldValue("datetime").toString());
            dataObject.setId(a.getFieldValue("id").toString());
            dataObject.set_version_(a.getFieldValue("_version_").toString());
            dataObject.setTimestamp(a.getFieldValue("timestamp").toString());
            result.add(dataObject);
        }

        return result;
    }

    public void indexList(ArrayList<CSVData> csvDataArrayList, String core) throws IOException, SolrServerException {
        ArrayList<SolrInputDocument> solrInputDocuments = new ArrayList();
        Iterator var4 = csvDataArrayList.iterator();

        while(var4.hasNext()) {
            CSVData csvData = (CSVData)var4.next();
            SolrInputDocument doc = this.getSolrInputFields(csvData);
            solrInputDocuments.add(doc);
        }

        this.client.add(core, solrInputDocuments);
    }

    public void index(CSVData csvData, String core) throws IOException, SolrServerException {
        SolrInputDocument doc = this.getSolrInputFields(csvData);
        this.client.add(core, doc, 100000);
    }

    public void commitdata(String core) throws IOException, SolrServerException {
        this.client.commit(core);
    }

    private SolrInputDocument getSolrInputFields(CSVData csvData) {
        SolrInputDocument doc = new SolrInputDocument(new String[0]);
        doc.addField("app_id", csvData.getApp_id());
        doc.addField("sms", csvData.getSms());
        doc.addField("datetime", csvData.getDatetime());
        doc.addField("timestamp", csvData.getTimestamp());
        return doc;
    }
}
