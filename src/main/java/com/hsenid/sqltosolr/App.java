package com.hsenid.sqltosolr;

import java.io.File;
import java.io.IOException;

public class App {
    public static final String CORE = "csv_backup";
    public static final String PATH = "/var/lib/mysql-files/new/";

    public App() {
    }

    public static void main(String[] args) throws IOException {
        File folder = new File("/var/lib/mysql-files/new/");
        File[] listOfFiles = folder.listFiles();
        File[] var3 = listOfFiles;
        int var4 = listOfFiles.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File a = var3[var5];
            String pathA = "/var/lib/mysql-files/new/" + a.getName();
            System.out.println(pathA);
        }

    }
}