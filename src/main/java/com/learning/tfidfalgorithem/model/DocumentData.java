package com.learning.tfidfalgorithem.model;

import java.util.HashMap;
import java.util.Map;

public class DocumentData {

    private Map<String, Double>  termToFrq = new HashMap<>();

    public void putTermFreq(String term, Double freq){
        termToFrq.put(term, freq);
    }

    public double getTermFreq(String term){
        return termToFrq.get(term);
    }
}
