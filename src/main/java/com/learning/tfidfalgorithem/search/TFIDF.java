package com.learning.tfidfalgorithem.search;

import com.learning.tfidfalgorithem.model.DocumentData;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Stream;

public class TFIDF {

    public static double calculateTermFrequency(List<String> books, String term){
        long count =0;

        for(String book: books){
            if(term.equalsIgnoreCase(book)){
                count++;
            }
        }

        double termFreq = (double)count/books.size();
        return termFreq;
<<<<<<< HEAD
        //test
=======
>>>>>>> main
    }

    public static DocumentData createDocumentData(List<String> words, List<String> terms){
        DocumentData documentData = new DocumentData();

        for(String term: terms){
           double termFreq = calculateTermFrequency(words, term);
           documentData.putTermFreq(term, termFreq);
        }

        return documentData;
    }

    private static double getInverseDocFreq(String term, Map<String, DocumentData> documentResults){
        double nt = 0;

        for(String document: documentResults.keySet()){
            DocumentData documentData = documentResults.get(document);
            double termFreq = documentData.getTermFreq(term);

            if(termFreq > 0.0){
                nt++;
            }
        }

        return nt == 0 ? 0: Math.log10(documentResults.size()/nt);
    }

    private static Map<String, Double> getTermToInverseDocumentFreqMap(List<String> terms,
                                                                    Map<String, DocumentData> documentResults){
        Map<String, Double> termToIDF = new HashMap<>();
        for(String term: terms){
            double idf = getInverseDocFreq(term, documentResults);
            termToIDF.put(term, idf);
        }

        return termToIDF;

    }

    private static double calculateDocumentScore(List<String> terms,
                                                 DocumentData documentData,
                                                 Map<String, Double> termToInverseDocumentFreq){
        double score = 0;

        for(String term: terms){
            double termFrequency = documentData.getTermFreq(term);
            double inverseTermFreq = termToInverseDocumentFreq.get(term);
            score += termFrequency + inverseTermFreq;
        }

        return score;
    }

    public static Map<Double, List<String>> getDocumentsSortedByScore(List<String> terms,
                                                                      Map<String, DocumentData> documentResults){

        TreeMap<Double, List<String>> scoreToDcouments = new TreeMap<>();

        Map<String, Double> termToIinverseDocFreq = getTermToInverseDocumentFreqMap(terms, documentResults);

        for(String document: documentResults.keySet()){
            DocumentData documentData = documentResults.get(document);
            double score = calculateDocumentScore(terms, documentData, termToIinverseDocFreq);
            addDocumenttScoreToTreeMap(scoreToDcouments, score, document);
        }

        return scoreToDcouments.descendingMap();

    }


    private static void addDocumenttScoreToTreeMap(TreeMap<Double, List<String>> scoreToDoc, double score, String document){
            List<String> documentsWithCurrentScore = scoreToDoc.get(score);
            if(documentsWithCurrentScore == null){
                documentsWithCurrentScore = new ArrayList<>();
            }

            documentsWithCurrentScore.add(document);
            scoreToDoc.put(score, documentsWithCurrentScore);
    }

    public static List<String> getWordsFromLines(List<String> lines){
        List<String> words = new ArrayList<>();
        for(String line: lines){
            words.addAll(getWordsFromLine(line));
        }

        return words;
    }

<<<<<<< HEAD
    public static List<String> getWordsFromLine(String line) {
=======
    private static List<String> getWordsFromLine(String line) {
>>>>>>> main
        return Arrays.asList(line.split("(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)"));
    }
}
