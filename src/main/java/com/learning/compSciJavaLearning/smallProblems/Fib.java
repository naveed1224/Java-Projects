package com.learning.compSciJavaLearning.smallProblems;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Fib {
    //for memoization
    static Map<Integer, Integer> memo = new HashMap<>(Map.of(0,0,1,1));

    private static int fib2(int n){
        if(!memo.containsKey(n)){
            memo.put(n, fib2(n-1)+fib2(n-2));
        }
        return memo.get(n);
    }

    private static int fib1(int n){
        if(n < 2) return n;
        return fib1(n-1) + fib1(n-1);
    }

    private static int fib3(int n){
        int last = 0;
        int next = 1;

        for (int i = 0; i < n; i++){
            int oldLast = last;
            last = next;
            next = oldLast + next;
        }

        return last;
    }

    private int last = 0, next = 1;

    public IntStream stream(){
        return IntStream.generate(() -> {
            int oldLast = last;
            last = next;
            next = oldLast + next;
            return oldLast;
        });
    }

    public static void main(String[] args) {
        Fib fib = new Fib();
        fib.stream().limit(41).forEachOrdered(System.out::println);

    }
}
