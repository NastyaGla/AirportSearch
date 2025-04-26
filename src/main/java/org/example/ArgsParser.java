package org.example;


import java.util.Arrays;

public class ArgsParser {

    private String dataPath;
    private int indexedColumnId;
    private String inputFilePath;
    private String outputFilePath;
    private boolean help;

    public ArgsParser(String[] args) {

        this.help = false;


        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "--data":
                    if (i + 1 < args.length) {
                        this.dataPath = args[++i];
                    } else {
                        throw new IllegalArgumentException("Missing value for --data");
                    }
                    break;

                case "--indexed-column-id":
                    if (i + 1 < args.length) {
                        try {
                            this.indexedColumnId = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid value for --indexed-column-id. It should be an integer.");
                        }
                    } else {
                        throw new IllegalArgumentException("Missing value for --indexed-column-id");
                    }
                    break;

                case "--input-file":
                    if (i + 1 < args.length) {
                        this.inputFilePath = args[++i];
                    } else {
                        throw new IllegalArgumentException("Missing value for --input-file");
                    }
                    break;

                case "--output-file":
                    if (i + 1 < args.length) {
                        this.outputFilePath = args[++i];
                    } else {
                        throw new IllegalArgumentException("Missing value for --output-file");
                    }
                    break;

                case "--help":
                case "-h":
                    this.help = true;
                    break;

                default:
                    throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        if ((dataPath == null || inputFilePath == null || outputFilePath == null) && !help) {
            throw new IllegalArgumentException("Missing required arguments.");
        }
    }
    public String getDataPath() {
        return dataPath;
    }

    public int getIndexedColumnId() {
        return indexedColumnId;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public boolean isHelp() {
        return help;
    }

    public static void printHelp() {
        System.out.println("Usage:");
        System.out.println("--data <path>                Path to CSV file");
        System.out.println("--indexed-column-id <id>     Column ID by which the search is performed");
        System.out.println("--input-file <path>          Path to the file with input search strings");
        System.out.println("--output-file <path>         Path to the file(json) with search results");
        System.out.println("--help, -h                   Display help");
    }
}
