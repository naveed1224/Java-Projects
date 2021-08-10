package com.learning.webclient;

import java.util.Arrays;
import java.util.List;

public class ServerClientApplication {
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/tasks";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8081/tasks";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator();
        String task1 = "10,200";
        String task2 = "234234234234,10000000000000,723674823678462";

        List<String> results = aggregator.sendTaskToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2),
                Arrays.asList(task1, task2));

        for (String result : results){
            System.out.println(result);
        }

    }
}
