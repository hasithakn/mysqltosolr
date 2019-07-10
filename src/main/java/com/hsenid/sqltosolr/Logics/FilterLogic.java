package com.hsenid.sqltosolr.Logics;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilterLogic {
    public FilterLogic() {
    }

    public void removePipewithQuats(String path) throws IOException {
        Path p = Paths.get(path);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(p), charset);
        content = content.replaceAll("\\|\"", "| ");
        Files.write(p, content.getBytes(charset), new OpenOption[0]);
    }

    public void removeNewLinesAndReplacebackSlashInFile(String path) throws IOException {
        Path p = Paths.get(path);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(p), charset);
        content = content.replaceAll("\\\\\\\n", " ");
        Files.write(p, content.getBytes(charset), new OpenOption[0]);
    }

    public boolean checkForPipes(String line) {
        int count = line.length() - line.replace("|", "").length();
        return count == 3;
    }

    public boolean checkForLastDigit(String line) {
        int count = line.length() - line.replaceAll("\\D$", "").length();
        return count == 0;
    }

    public String[] getFields(String line) {
        String[] split = line.split("\\|");
        return split.length == 4 ? split : null;
    }
}