package com.hsenid.sqltosolr.DBConnection;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;

public class SOLRDBConnecter {
    static final String URL = "http://localhost:8983/solr/";
    private static SolrClient solr;

    private SOLRDBConnecter() {
    }

    public static SolrClient getSolrClient() {
        if (solr == null) {
            solr = ((Builder)((Builder)(new Builder("http://localhost:8983/solr/")).withConnectionTimeout(10000)).withSocketTimeout(60000)).build();
        }

        return solr;
    }
}
