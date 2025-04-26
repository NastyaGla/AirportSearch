package org.example;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        long initStart = System.currentTimeMillis();

        ArgsParser argsParser;
        argsParser = new ArgsParser(args);

        if (argsParser.isHelp()) {
            ArgsParser.printHelp();
            return;
        }

        Path dataFilePath = Path.of(argsParser.getDataPath());
        int columnIndex = argsParser.getIndexedColumnId() - 1;
        Path inputFilePath = Path.of(argsParser.getInputFilePath());
        Path outputFilePath = Path.of(argsParser.getOutputFilePath());

        List<String> inputLines = ReaderWriter.readInput(inputFilePath);

        int prefixSize = 0;
        for (String s : inputLines) {
            if (s.length() > prefixSize) {
                prefixSize = s.length();
            }
        }

        prefixSize = Math.min(prefixSize, 7);

        CsvSearcher searcher = new CsvSearcher(dataFilePath, columnIndex, 0, prefixSize);

        List<SearchResult> results = new ArrayList<>();
        long initTime = System.currentTimeMillis() - initStart;
        for (String query : inputLines) {
            long start = System.currentTimeMillis();
            if (query.length() > prefixSize) query = query.substring(0, prefixSize);
            List<Integer> rowNumbers = searcher.search(query);
            long time = System.currentTimeMillis() - start;
            results.add(new SearchResult(query, rowNumbers, time));
        }

        ReaderWriter.writeResult(outputFilePath, initTime, results);
    }

}