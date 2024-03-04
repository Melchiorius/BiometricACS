package ru.boost.network.client.handler.rest;

import ru.boost.network.client.settings.Settings;
import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.network.common.businesslogic.utils.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RESTPersonAll {

    private final static String path = "/person/all";


    public static List<Person> get() {
        String url = "http://" + Settings.RESTHost + ":" + Settings.RESTPort + path;
        HttpURLConnection connection = null;
        List<Person> result = new ArrayList<>();

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Получаем входной поток данных
            InputStream inputStream = connection.getInputStream();

//            // Преобразуем входной поток в массив байтов
//            byte[] byteArray = toByteArray(inputStream);
//
//            result = ObjectMapper.Bytes2Persons(byteArray);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            result = ObjectMapper.Json2Persons(response.toString());

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    // Метод для преобразования InputStream в массив байтов
    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        // Читаем данные из InputStream и записываем их в ByteArrayOutputStream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        // Преобразуем ByteArrayOutputStream в массив байтов
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Закрываем потоки
        inputStream.close();
        byteArrayOutputStream.close();

        return byteArray;
    }


}
