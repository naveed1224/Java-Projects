package com.learning.webserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServer {
    private static final String TASK_ENDPOINT = "/tasks";
    private static final String STATUS_ENDPOINT = "/status";

    private final int port;
    private HttpServer httpServer;

    public WebServer(int port){
        this.port = port;
    }

    public static void main(String[] args) {
        int serverPort = 8082;

        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        System.out.println("Server is listening on PORT: #"+serverPort);
    }

    public void startServer(){
        //backlog is the server queue if not enough resources to process request, 0 is default
        try{
            this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        HttpContext statusContext = httpServer.createContext(STATUS_ENDPOINT);
        HttpContext tasksContext = httpServer.createContext(TASK_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        tasksContext.setHandler(this::handleTaskEndpoint);

        httpServer.setExecutor(Executors.newFixedThreadPool(8));
        httpServer.start();

    }

    private void handleTaskEndpoint(HttpExchange httpExchange) throws IOException {
        if(!httpExchange.getRequestMethod().equalsIgnoreCase("post")){
            httpExchange.close();
            return;
        }

        Headers headers = httpExchange.getRequestHeaders();

        if(headers.containsKey("X-TEST") && headers.get("X-TEST").get(0).equalsIgnoreCase("true")){
            String dummyResponse = "TEST-RESPONSE-SUCCESS\n";
            sendResponse(dummyResponse.getBytes(), httpExchange);
        }

        boolean isDebug = false;
        if(headers.containsKey("X-DEBUG") && headers.get("X-DEBUG").get(0).equalsIgnoreCase("true")){
            isDebug = true;
        }

        long startTime = System.nanoTime();
        byte[] requestBytes = new byte[0];
        try {
            requestBytes = httpExchange.getRequestBody().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] responseBytes = calculateResponse(requestBytes);
        long finishTime = System.nanoTime();

        if (isDebug){
            String debugMessage = String.format("Operation took %d ns\n", finishTime-startTime);
            try{
                httpExchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        try {
            sendResponse(responseBytes, httpExchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] calculateResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] numbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;

        for(String number : numbers){
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("Result of the calculation is : %s\n", result).getBytes();
    }

    private void handleStatusCheckRequest(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("get")){
            httpExchange.close();
            return;
        }

        String responseMessage = "SERVER IS ALIVE";
        sendResponse(responseMessage.getBytes(), httpExchange);
    }

    private void sendResponse(byte[] responseBytes, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
    }

}
