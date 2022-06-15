package com.cm6121.countWord.counter;

import java.util.*;

public class Counter {
    private HashMap<String, Integer> wordCountArray = new HashMap<>();
    private Set<String> uniqueWords = new HashSet<>();
    private String processedContent;

    public HashMap<String, Integer> getWordCountArray() {
        return wordCountArray;
    }

    private Set<String> findUniqueWords(String content) {
        Set<String> uniqueWords = new HashSet<>();
        String[] words = content.trim().split("\\W+");
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 1) {
                uniqueWords.add(words[i]);
            }
        }
        return uniqueWords;
    }

    private HashMap<String, Integer> countOccurrence(String content, Set<String> uniqueWords) {
        HashMap<String, Integer> wordCountArray = new HashMap<>();
        String[] trimmedContent = content.trim().split("\\W+");
        for (String word: uniqueWords) {
            int occurrence = 0;
            for (int i=0; i < trimmedContent.length; i++) {
                if (trimmedContent[i].equals(word)) {
                    occurrence++;
                }
            }
            wordCountArray.put(word.trim(), occurrence);
        }
        return wordCountArray;
    }

    public void searchForAWord(String word, String title, Map sortedWordCountSingleFile) {
        if(this.uniqueWords.contains(word)) {
            System.out.println("The number of times the word " + word +
                    " appears in the " + title + " is " + sortedWordCountSingleFile.get(word));
        } else {
            System.out.println("The word does not exist in " + title);
        }
    }

    public void countWordsForContent(String content) {
        this.processedContent = content.toLowerCase();
        this.uniqueWords = this.findUniqueWords(this.processedContent);
        this.wordCountArray = this.countOccurrence(this.processedContent, this.uniqueWords);
    }
}