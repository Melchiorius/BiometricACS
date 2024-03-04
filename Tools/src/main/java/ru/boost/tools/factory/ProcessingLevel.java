package ru.boost.tools.factory;

import ru.boost.tools.opencv.processing.FaceDetection;
import ru.boost.tools.opencv.processing.Recognition;
import ru.boost.tools.opencv.processing.VideoCapture;
import ru.boost.tools.opencv.processing.prototipe.FrameProvider;

import java.io.File;

public enum ProcessingLevel {
    TRANSLATION(VideoCapture::new),
    DETECTION(FaceDetection::new),
    RECOGNITION((file, fps) -> new Recognition(file,fps));

    private final ProcessorInitializer<? extends FrameProvider, File, Integer> initializer;

    ProcessingLevel(ProcessorInitializer<? extends FrameProvider, File, Integer>  initializer) {
        this.initializer = initializer;
    }

    public ProcessorInitializer<? extends FrameProvider, File, Integer> getProvider() {
        return initializer;
    }
}
