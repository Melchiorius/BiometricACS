package ru.boost.network.client.handler.rest;

import ru.boost.network.client.settings.Settings;
import ru.boost.network.common.businesslogic.entity.Person;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RESTPersonUpdate {

    private final static String path = "/person/update";

    public static void update(long id, String name, boolean access) {
        String url = "http://" + Settings.RESTHost + ":" + Settings.RESTPort + path; // Указываем URL для удаления персоны
        HttpURLConnection connection = null;
        String data = id+";"+name+";"+access; // Пример JSON-данных
       try {

           // Открываем соединение с сервером
           URL serverUrl = new URL(url);
           connection = (HttpURLConnection) serverUrl.openConnection();
           connection.setRequestMethod("POST");
           connection.setDoOutput(true);
           // Устанавливаем тип содержимого и длину данных
           connection.setRequestProperty("Content-Type", "application/json");
           connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes(StandardCharsets.UTF_8).length));
           // Отправляем данные на сервер
           try (OutputStream outputStream = connection.getOutputStream()) {
               outputStream.write(data.getBytes(StandardCharsets.UTF_8));
           }
           // Получаем ответ от сервера
           int responseCode = connection.getResponseCode();
           System.out.println("Response Code: " + responseCode);
       } catch (IOException e) {
           System.out.println(e);
       } finally {
           if (connection != null) {
               connection.disconnect();
           }
       }

    }
}
