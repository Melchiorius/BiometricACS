package ru.boost.network.test;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;



public class VideoPlayer {

    private static final String address = "localhost";
    private static final int port = 12345;
    private static final String size = "1920×1080";
    private static final Dimension dimension = new Dimension(800,600);

    private static final int BUFFER_SIZE = 6_220_800;
    private static final byte[] BUFFER = new byte[BUFFER_SIZE];
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    public static void main(String[] args) throws IOException {
        processData();
    }

    private static void processData(){
        // Создаём окно для просмотра изображения.
        JFrame window = new JFrame("Window:");
        // Создаём контейнер для изображения.
        JLabel screen = new JLabel();
        // Установлимаем операцию закрытия по умолчанию.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Делаем окно отображения контента видимым.
        window.setVisible(true);

        try (Socket socket = new Socket("localhost", 12345); DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()))){
            while(true) {
                int dataLength = inputStream.readInt();
                // Если изображение существует, то
                if (dataLength > 0) {
                    /* принимаем изображение в виде массива байтов (пикселей). "последовательность байтов" */
                    byte[] imageData = new byte[dataLength];
                    inputStream.readFully(imageData, 0, dataLength);
                    /* Преобразуем массив пикселей в ImageIcon, изображение которое будет отображатся. */
                    ImageIcon ic = new ImageIcon(imageData);
                    // Привязываем изображение к контейнеру.
                    screen.setIcon(ic);
                    // Привязываем контейнер к окну отображения.
                    window.setContentPane(screen);
                    window.pack();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    };
}
