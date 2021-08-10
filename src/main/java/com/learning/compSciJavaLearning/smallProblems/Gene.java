package com.learning.compSciJavaLearning.smallProblems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gene {

    public enum Nucleotide {
        A, C, G, T
    }

    private ArrayList<Codon> codons = new ArrayList<>();

    public Gene(String geneStr){
        for (int i = 0; i <= geneStr.length()-3 ; i+=3){
            codons.add(new Codon(geneStr.substring(i, i+3)));
        }
    }

    public boolean linearContains(Codon key){
        for (Codon code: codons){
            if (code.compareTo(key) == 0){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Codon acg = new Codon("ACG");
        Codon gat = new Codon("GAT");

        boolean result_acg = myGene.linearContains(acg);
        boolean result_b_acg = myGene.binaryContains(acg);
        System.out.println(result_acg);
        System.out.println(result_b_acg);
    }

    public boolean binaryContains(Codon key){
        ArrayList<Codon> sortedCodon = new ArrayList<>(codons);
        Collections.sort(sortedCodon);

        int low = 0;
        int high = sortedCodon.size()-1;
        while (low <= high){
            int middle = (low+high)/2;

            int comparison = sortedCodon.get(middle).compareTo(key);

            if (comparison < 0){
                low = middle + 1;
            } else if(comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }

        }
        return false;
    }

    public static class Codon implements Comparable<Codon>{

        public final Nucleotide first, second, third;
        private final Comparator<Codon> comparator = Comparator.comparing((Codon c) -> c.first)
                .thenComparing((Codon c) -> c.second)
                .thenComparing((Codon c) -> c.third);

        public Codon(String codonStr) {
            first = Nucleotide.valueOf(codonStr.substring(0, 1));
            second = Nucleotide.valueOf(codonStr.substring(1, 2));
            third = Nucleotide.valueOf(codonStr.substring(2, 3));

        }

        @Override
        public int compareTo(Codon other) {
            return comparator.compare(this, other);
        }
    }
}
