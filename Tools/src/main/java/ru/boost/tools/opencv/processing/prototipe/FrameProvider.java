package ru.boost.tools.opencv.processing.prototipe;

public interface FrameProvider {

    byte[] getNextFrame();
    boolean isAlive();
}
