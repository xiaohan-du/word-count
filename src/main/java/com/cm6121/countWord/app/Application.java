package com.cm6121.countWord.app;


import com.cm6121.countWord.app.utilityFile.WriterCSV;
import com.cm6121.countWord.counter.Counter;
import com.cm6121.countWord.reader.Reader;
import com.cm6121.countWord.ui.UI;
import com.cm6121.countWord.utils.MapUtil;

import java.io.File;
import java.util.*;

public class Application {
    public static void main (String[] args) {
        String documentToRead = ClassLoader.getSystemClassLoader().getResource("FolderDocumentsToRead").getPath();
        System.out.println("The counting words application");
        Reader reader = new Reader();
        MapUtil mu = new MapUtil();
        Scanner sc = new Scanner(System.in);
        UI ui = new UI();
        WriterCSV ws = new WriterCSV();
        boolean isSearchAgain = true;
        reader.readFileNames(new File(documentToRead));
        List<File> files = reader.getFileList();
        List<String[]> info;
        HashMap<String, Integer> wordCountSingleFile;
        HashMap<String, Integer> wordCountCorpus;
        HashMap<String, Integer> wordCountSearchSingleFile;
        String content;
        String allContent = new String();
        String filePath;
        String fileName;
        String title;
        String creationDate;
        String yesNo;
        filePath = ws.createDirectory();
        System.out.println("The number of documents in the folder is " + files.size());
        for (File f: files) {
            Map sortedWordCountSingleFile;
            Counter counterIndividualFiles = new Counter();
            reader.processFile(f);
            info = reader.getFileInfo();
            fileName = f.getName().trim();
            title = info.get(1)[0].trim();
            creationDate = info.get(1)[2].trim();
            System.out.println("The file name is " + fileName + ", " +
            "the title is " + title + ", " +
            "the creation date is " + creationDate);
            content = reader.getFileContent();
            reader.concatContent(content);
            allContent = reader.getAllFileContent();
            counterIndividualFiles.countWordsForContent(content);
            wordCountSingleFile = counterIndividualFiles.getWordCountArray();
            sortedWordCountSingleFile = mu.sortByValue(wordCountSingleFile, true);
            ui.printOccurrenceMapSingleFile(sortedWordCountSingleFile, title, creationDate, filePath);
        }
        Map sortedWordCountCorpus;
        Counter counterCorpus = new Counter();
        counterCorpus.countWordsForContent(allContent);
        wordCountCorpus = counterCorpus.getWordCountArray();
        sortedWordCountCorpus = mu.sortByValue(wordCountCorpus, false);
        System.out.println("20 words which have most occurrences: ");
        ui.printOccurrenceMapCorpus(sortedWordCountCorpus, filePath);

        while (isSearchAgain) {
            System.out.println("What is the word you'd like to search?");
            String inp;
            inp = sc.nextLine();
            for (File f: files) {
                Map sortedWordCountSearchSingleFile;
                Counter counterWordSearchSingleFile = new Counter();
                reader.processFile(f);
                title = reader.getFileInfo().get(1)[0].trim();
                content = reader.getFileContent();
                reader.concatContent(content);
                counterWordSearchSingleFile.countWordsForContent(content);
                wordCountSearchSingleFile = counterWordSearchSingleFile.getWordCountArray();
                sortedWordCountSearchSingleFile = mu.sortByValue(wordCountSearchSingleFile, true);
                counterWordSearchSingleFile.searchForAWord(inp, "text " + title, sortedWordCountSearchSingleFile);
            }
            Counter counterWordSearchCorpus = new Counter();
            counterWordSearchCorpus.countWordsForContent(allContent);
            wordCountCorpus = counterWordSearchCorpus.getWordCountArray();
            sortedWordCountCorpus = mu.sortByValue(wordCountCorpus, false);
            counterWordSearchCorpus.searchForAWord(inp, "whole corpus", sortedWordCountCorpus);
            System.out.println("Do you want to search again? y/n");
            do {
                yesNo = sc.nextLine();
                if (yesNo.equals("y")) {
                    isSearchAgain = true;
                } else if (yesNo.equals("n")) {
                    isSearchAgain = false;
                }
                if (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n"))) {
                    System.out.println("Please enter valid input 'y' or 'n'");
                }
            } while (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n")));
        }
    }
}
