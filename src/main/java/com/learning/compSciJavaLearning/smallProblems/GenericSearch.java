package com.learning.compSciJavaLearning.smallProblems;

import java.util.List;

public class GenericSearch {

    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key){
        for(T item : list){
            if (item.compareTo(key) == 0){
                return true;
            }
        }
        return false;
    }


    public static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key){
        int low = 0;
        int high = list.size()-1;

        while(low <= high ){
            int middle = (low+high)/2;

            int comparison = list.get(middle).compareTo(key);

            if( comparison < 0){
                low = middle + 1;
            } else if(comparison > 0){
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(linearContains(List.of(1, 5, 15, 15, 15, 15, 20),
                5));
        System.out.println(binaryContains(List.of("a", "d", "e", "f", "z"),
                "f")); // true
        System.out.println(binaryContains(List.of("john", "mark", "ronald",
                "sarah"), "john")); // false
    }

    public static class Node<T> implements Comparable<Node<T>>{

        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        Node(T state, Node<T> parent){
            this.state = state;
            this.parent = parent;
        }

        @Override
        public int compareTo(GenericSearch.Node<T> o) {
            return 0;
        }
    }
}


