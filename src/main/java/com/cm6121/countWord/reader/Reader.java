package com.cm6121.countWord.reader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private List<File> fileList = new ArrayList<>();

    private List<String[]> fileInfo = new ArrayList<>();

    private String fileContent = new String();

    private String allFileContent = new String();

    public List<File> getFileList() {
        return this.fileList;
    }

    public String getAllFileContent() { return this.allFileContent; };

    public List<String[]> getFileInfo() {
        return this.fileInfo;
    }

    public String getFileContent() { return this.fileContent; }

    private List<String[]> iterativeReadByLine(List<String[]> varToRead, File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            CSVReader reader = new CSVReader(new FileReader(f));
            while (br.readLine() != null) {
                varToRead.add(reader.readNext());
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return varToRead;
    }

    private void readFileInfo(File f) {
        this.fileInfo = new ArrayList<>();
        this.fileInfo = this.iterativeReadByLine(this.fileInfo, f);
    }

    private void readFileContent(File f) {
        List<String[]> tempContent = new ArrayList<>();
        tempContent = this.iterativeReadByLine(tempContent, f);
        this.fileContent = tempContent.get(1)[1];
    }

    public void concatContent(String content) {
        try {
            allFileContent = allFileContent.concat(" ").concat(content);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void readFileNames(File folder) {
        File[] list;
        list = folder.listFiles();
        for(File f: list) {
            if(f.isDirectory()) {
                readFileNames(f);
            } else {
                fileList.add(f);
            }
        }
    }

    public void processFile(File f) {
        this.readFileInfo(f);
        this.readFileContent(f);
    }
}
