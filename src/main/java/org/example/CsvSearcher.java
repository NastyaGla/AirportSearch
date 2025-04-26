package org.example;

import org.example.trie.Trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvSearcher {
    private final Trie trie = new Trie();

    public CsvSearcher(Path csvPath, int searchColumnId, int indexColumnId, int prefixSize) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath.toFile()), 8192);) {
            String line;

            while ((line = reader.readLine()) != null) {
                String prefix = getColumn(line, searchColumnId);
                if (!prefix.isEmpty()) {
                    prefix = preparePrefix(prefix);
                    int indexValue = Integer.parseInt(getColumn(line, indexColumnId).trim());
                    if (prefix.length() > prefixSize) prefix = prefix.substring(0, prefixSize);
                    trie.insert(prefix, indexValue);
                } else {
                    throw new IllegalArgumentException("Incorrect column number");
                }

            }

        }
    }


    public List<Integer> search(String prefix) {
        return trie.searchByPrefix(preparePrefix(prefix));
    }

    private String getColumn(String line, int columnIndex) {
        int currentIndex = 0;
        int start = 0;

        for (int i = 0; i <= line.length(); i++) {
            if (i == line.length() || line.charAt(i) == ',') {
                if (currentIndex == columnIndex) {
                    return line.substring(start, i);
                }
                start = i + 1;
                currentIndex++;
            }
        }

        return "";
    }


    private String preparePrefix(String prefix) {

        StringBuilder prefixBuilder = new StringBuilder(prefix.length());
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (c == '"') {
                continue;
            }
            prefixBuilder.append(Character.toLowerCase(c));
        }
        return prefixBuilder.toString();
    }
}
