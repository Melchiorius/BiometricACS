package ru.boost.network.client.handler.rest;

import ru.boost.network.client.settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RESTPersonDelete {

    private final static String path = "/person/delete?id=";

    public static void delete(long id) {
        String url = "http://" + Settings.RESTHost + ":" + Settings.RESTPort + path + id; // Указываем URL для удаления персоны

        HttpURLConnection connection = null;

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            System.out.println("Status code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response: " + response.toString());
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
