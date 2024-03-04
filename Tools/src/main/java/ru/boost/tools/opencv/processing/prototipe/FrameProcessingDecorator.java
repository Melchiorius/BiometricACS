package ru.boost.tools.opencv.processing.prototipe;

import org.opencv.core.Mat;

public abstract class FrameProcessingDecorator extends FrameProcessor{

    protected FrameProcessingDecorator parent;

    public FrameProcessingDecorator(FrameProcessingDecorator frameProvider) {
        this.parent = frameProvider;
    }

    public abstract Mat getCurrentFrame();

    @Override
    public boolean isAlive() {
        return parent.isAlive();
    }
}
