package ru.boost.network.common.connection.handler.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.boost.network.common.businesslogic.controller.MainController;
import ru.boost.network.common.businesslogic.entity.Person;

import java.io.*;

import java.io.IOException;
import java.util.stream.Collectors;

public class RESTPersonUpdate implements HttpHandler {

    public final static String path = "/person/update";

    private static RESTHandlerInitiateFunction register;

    public static RESTHandlerInitiateFunction getRegister(){
        if(register == null){
            register = new RESTHandlerInitiateFunction<RESTPersonUpdate>(){
                @Override
                public String getPath() {
                    return path;
                }
                @Override
                public RESTPersonUpdate getHandler() {
                    return new RESTPersonUpdate();
                }
            };
        }
        return register;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Проверяем метод запроса (должен быть POST)
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Метод не поддерживается
            return;
        }

        boolean success = false;
        // Получаем тело запроса (новые данные о персоне)
        String[] requestBody = convertStreamToString(exchange.getRequestBody()).split(";");
        try {
            long id = Long.parseLong(requestBody[0]);
            String name = requestBody[1];
            boolean access = Boolean.parseBoolean(requestBody[2]);
            MainController.updateData(id,name,access);
            success = true;
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid id format: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Not enough elements in the requestBody array: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e);
        }

        // Устанавливаем код ответа (200 если обновление прошло успешно, иначе 500)
        int responseCode = success ? 200 : 500;
        exchange.sendResponseHeaders(responseCode, -1);

        // Отправляем ответ клиенту
        try (OutputStream outputStream = exchange.getResponseBody()) {
            String response = success ? "Person updated successfully" : "Failed to update person";
            outputStream.write(response.getBytes());
        }
    }

    private String convertStreamToString(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
