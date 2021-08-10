package com.learning.compSciJavaLearning.smallProblems;

import java.util.BitSet;
import java.util.Locale;

public class CompressedGene {
    private BitSet bitSet;
    private int length;

    public CompressedGene(String gene){
        compress(gene);
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public void setBitSet(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void compress(String gene){
        length = gene.length();
        bitSet = new BitSet(length*2);
        final String upperGene = gene.toUpperCase();

        for (int i = 0; i < length; i++){
            final int firstLocation = 2 * i;
            final int secondLocation = 2 *i +1;

            switch (upperGene.charAt(i)){
                case 'A':
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, false);
                    break;
                case 'C':
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, true);
                    break;
                case 'G':
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, false);
                    break;
                case 'T':
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, true);
                    break;
                default:
                    throw new IllegalArgumentException("The provided gene String contains characters other than ACGT");
            }
        }
    }

    public String decompress(){
        if(bitSet == null){
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(length);

        for(int i=0; i < (length*2); i+=2){
            final int firstBit = (bitSet.get(i) ? 1 : 0);
            final int secondBit = (bitSet.get(i + 1) ? 1 : 0);

            final int lastBits = firstBit << 1 | secondBit;

            switch (lastBits){
                case 0b00: // 00 is 'A'
                    stringBuilder.append('A');
                    break;
                case 0b01: // 01 is 'C'
                    stringBuilder.append('C');
                    break;
                case 0b10: // 10 is 'G'
                    stringBuilder.append('G');
                    break;
                case 0b11: // 11 is 'T'
                    stringBuilder.append('T');
                    break;
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        final String original = "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATAA";

        CompressedGene compressedGene = new CompressedGene(original);
        String reverted = compressedGene.decompress();
        System.out.println(reverted);

    }


}
