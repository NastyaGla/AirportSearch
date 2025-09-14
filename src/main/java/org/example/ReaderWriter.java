package org.example;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderWriter {

    public static List<String> readInput(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public static void writeResult(Path outputFile, long initTime, List<SearchResult> results) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            writer.write("{");
            writer.write("\"initTime\": " + initTime + ", ");
            writer.write("\"result\": [\n");

            for (int i = 0; i < results.size(); i++) {
                SearchResult r = results.get(i);
                writer.write("  {\"search\": \"" + r.getSearch() + "\", \"result\": [");

                List<Integer> values = r.getResult();
                for (int j = 0; j < values.size(); j++) {
                    writer.write(String.valueOf(values.get(j)));
                    if (j != values.size() - 1) writer.write(", ");
                }
                writer.write("],");

                long time = r.getTime();
                writer.write(" \"time\": " + time + "}");

                if (i != results.size() - 1) writer.write(",");
                writer.write("\n");
            }

            writer.write("]}");
        }
    }
}
