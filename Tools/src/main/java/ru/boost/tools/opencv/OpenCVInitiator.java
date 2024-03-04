package ru.boost.tools.opencv;

import org.opencv.core.Core;

public class OpenCVInitiator {

    private static OpenCVInitiator instance;

    public static OpenCVInitiator getInstance(){
        if(instance == null){
            instance = new OpenCVInitiator();
        }
        return instance;
    }

    public OpenCVInitiator(){
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadLocally();
        System.out.println("OpenCV initialized");
    }
}
