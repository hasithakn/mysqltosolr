package com.hsenid.sqltosolr.FileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileWrite {
    public FileWrite() {
    }

    public boolean writeFile(String path, ArrayList<String> strings) {
        File fileW = new File(path);
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileW);
            BufferedWriter out = new BufferedWriter(fw);
            out.write("app_id|sms|timestamp|datetime\n");
            Iterator var6 = strings.iterator();

            while(var6.hasNext()) {
                String s = (String)var6.next();
                out.write(s + '\n');
            }

            out.flush();
            out.close();
            return true;
        } catch (IOException var8) {
            var8.printStackTrace();
            return false;
        }
    }
}
