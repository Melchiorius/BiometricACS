package ru.boost.network.common.connection.handler.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.boost.network.common.businesslogic.controller.MainController;

import java.io.IOException;
import java.io.OutputStream;

public class RESTPersonAll implements HttpHandler {

    public final static String path = "/person/all";

    private static RESTHandlerInitiateFunction register;

    public static RESTHandlerInitiateFunction getRegister(){
        if(register == null){
            register = new RESTHandlerInitiateFunction<RESTPersonAll>(){
                @Override
                public String getPath() {
                    return path;
                }
                @Override
                public RESTPersonAll getHandler() {
                    return new RESTPersonAll();
                }
            };
        }
        return register;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Проверяем метод запроса
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1); // Метод не поддерживается
            return;
        }

        String jsonPersons = MainController.getAllData();
        //byte[] persons = MainController.getAllData();

        // Устанавливаем заголовки
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonPersons.getBytes().length);

        // Отправляем JSON в качестве ответа
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(jsonPersons.getBytes());
        }
    }
}
