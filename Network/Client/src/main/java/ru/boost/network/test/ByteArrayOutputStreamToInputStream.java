package ru.boost.network.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayOutputStreamToInputStream extends InputStream {
    private final ByteArrayOutputStream outputStream;
    private int position;

    public ByteArrayOutputStreamToInputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
        this.position = 0;
    }

    @Override
    public int read() throws IOException {
        synchronized (outputStream) {
            // Проверяем, достигнут ли конец outputStream
            if (position >= outputStream.size()) {
                return -1; // Конец потока данных
            }
            // Читаем байт из outputStream
            int data = outputStream.toByteArray()[position];
            position++;
            return data & 0xFF; // Преобразуем в unsigned byte
        }
    }
}
