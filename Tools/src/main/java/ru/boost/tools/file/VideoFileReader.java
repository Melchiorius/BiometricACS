package ru.boost.tools.file;


import ru.boost.tools.factory.ProcessingLevel;
import ru.boost.tools.opencv.processing.prototipe.FrameProvider;

import java.io.File;
import java.io.IOException;

public class VideoFileReader implements FrameProvider {

    private final File file;
    private final FrameProvider provider;



    public VideoFileReader(String filePath, int fps, ProcessingLevel level) throws IOException {
        file = checkFilePath(filePath);
        if(file == null){
            throw new IOException("Can't read file "+filePath);
        }
        provider = level.getProvider().init(file, fps);
    }

    private File checkFilePath(String filePath){
        File file = new File(filePath);
        if(!file.exists() || file.isDirectory()){
            return null;
        }
        return file;
    }

    @Override
    public byte[] getNextFrame() {
        return provider.getNextFrame();
    }

    @Override
    public boolean isAlive() {
        return provider.isAlive();
    }
}
