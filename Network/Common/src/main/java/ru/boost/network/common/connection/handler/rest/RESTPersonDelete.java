package ru.boost.network.common.connection.handler.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.boost.network.common.businesslogic.controller.MainController;

import java.io.IOException;
import java.io.OutputStream;

public class RESTPersonDelete  implements HttpHandler {

    public final static String path = "/person/delete";

    private static RESTHandlerInitiateFunction register;

    public static RESTHandlerInitiateFunction getRegister(){
        if(register == null){
            register = new RESTHandlerInitiateFunction<RESTPersonDelete>(){
                @Override
                public String getPath() {
                    return path;
                }
                @Override
                public RESTPersonDelete getHandler() {
                    return new RESTPersonDelete();
                }
            };
        }
        return register;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
            exchange.sendResponseHeaders(405, -1); // Метод не поддерживается
            return;
        }

        // Получаем id персоны для удаления из параметров запроса
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("=");
        long personId = Long.parseLong(params[1]); // Предполагается, что параметр имеет вид "id=XXX"

        MainController.deleteData(personId);

        // Отправляем ответ об успешном удалении
        String response = "Person with id " + personId + " has been deleted";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
