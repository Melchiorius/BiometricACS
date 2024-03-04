package ru.boost.network.common.connection.handler.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class TestRESTHandler implements HttpHandler {

    public final static String path = "/test";

    private static RESTHandlerInitiateFunction register;

    public static RESTHandlerInitiateFunction getRegister(){
        if(register == null){
            register = new RESTHandlerInitiateFunction<TestRESTHandler>(){
                @Override
                public String getPath() {
                    return path;
                }
                @Override
                public TestRESTHandler getHandler() {
                    return new TestRESTHandler();
                }
            };
        }
        return register;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Чтение информации о запросе
        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();
        // Другие операции с объектом HttpExchange для получения информации о запросе

        // Отправка ответа клиенту
        String response = requestMethod+"\t"+requestURI;
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
