package com.cm6121.countWord.ui;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public class UI {

    private StringBuilder appendKeyValue(StringBuilder sb, Map m) {
        for(int i = 0; i < m.size(); i++) {
            sb.append(m.keySet().toArray()[i]);
            sb.append(',');
            sb.append(m.get(m.keySet().toArray()[i]));
            sb.append('\n');
        }
        return sb;
    }
    public void printOccurrenceMapSingleFile(Map m, String title, String year, String path) {

        try (PrintWriter writer = new PrintWriter(path + title.replaceAll(" ", "_") + "_allWords.csv")) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < m.size(); i++) {
                System.out.println("Word : " + m.keySet().toArray()[i]
                        + ", Value : " + m.get(m.keySet().toArray()[i])
                );
            }

            sb.append(title);
            sb.append(',');
            sb.append(year);
            sb.append(',');
            sb.append('\n');
            this.appendKeyValue(sb, m);
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printOccurrenceMapCorpus(Map m, String path) {

        try (PrintWriter writer = new PrintWriter(path + "CSVAllDocuments_allWords.csv")) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < 20; i++) {
                System.out.println("Word : " + m.keySet().toArray()[i]
                        + ", Value : " + m.get(m.keySet().toArray()[i])
                );
            }
            sb.append("Word");
            sb.append(',');
            sb.append("Number of occurrences");
            sb.append(',');
            sb.append('\n');
            this.appendKeyValue(sb, m);
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
