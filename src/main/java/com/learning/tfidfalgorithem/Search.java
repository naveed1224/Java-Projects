package com.learning.tfidfalgorithem;

import com.learning.tfidfalgorithem.model.DocumentData;
import com.learning.tfidfalgorithem.search.TFIDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Search {
    public static final String BOOKS_DIR = "./resources/books";
    public static final String SEARCH_QUERY_1 = "The best detective that catches many criminals using his deductive methods";


    public static void main(String[] args) throws FileNotFoundException {
        File documentsDir = new File(BOOKS_DIR);

        List<String> documents = Arrays.asList(documentsDir.list())
                .stream()
                .map(documentName -> BOOKS_DIR+"/"+documentName)
                .collect(Collectors.toList());

        List<String> terms = TFIDF.getWordsFromLine(SEARCH_QUERY_1);

        findMostRelevantDocuments(documents, terms);

    }

    private static void findMostRelevantDocuments(List<String> documents, List<String> terms) throws FileNotFoundException {
        Map<String, DocumentData> documentResults = new HashMap<>();

        for(String document: documents){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(document));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            List<String> words = TFIDF.getWordsFromLines(lines);
            DocumentData documentData = TFIDF.createDocumentData(words, terms);
            documentResults.put(document, documentData);
        }

        Map<Double, List<String>> documentsByScore = TFIDF.getDocumentsSortedByScore(terms, documentResults);
        printResults(documentsByScore);
    }

    private static void printResults(Map<Double, List<String>> documentsByScore) {
        for(Map.Entry<Double, List<String>> docScorePair: documentsByScore.entrySet()){
            double score = docScorePair.getKey();
            for(String document: docScorePair.getValue()){
                System.out.println(String.format("BOOK: %s - SCORE: %f", document.split("/")[3], score));
            }
        }
    }

}
