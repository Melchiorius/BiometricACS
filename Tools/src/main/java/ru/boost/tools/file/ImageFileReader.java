package ru.boost.tools.file;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ImageFileReader {
    public static ImageIcon loadImage(String imagePath) {
        try {
            URL url = ImageFileReader.class.getClassLoader().getResource(imagePath);
            if (url == null) {
                System.out.println("Изображение не найдено в ресурсах: " + imagePath);
                return null;
            }
            return new ImageIcon(ImageIO.read(url));
        } catch (IOException e) {
            // Обработка ошибок при чтении изображения
            e.printStackTrace();
            return null;
        }
    }
}
