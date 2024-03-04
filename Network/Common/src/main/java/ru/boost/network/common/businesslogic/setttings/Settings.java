package ru.boost.network.common.businesslogic.setttings;

import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.tools.file.ImageFileReader;

public class Settings {

    public static final int RESTPort = 7654;
    public static final int VideoPort = 8765;
    public static final String RESTHost = "localhost";
    public static final String VideoFilePath = "C:/Projects/test.mp4";

    public static final boolean debug = true;

    public static Person[] getDebugData(){
        Person[] data = new Person[5];
        data[0] = new Person(0,"Петя", ImageFileReader.loadImage("ru/boost/ui/icon/petia.jpg"));
        data[1] = new Person(1,"Маша", ImageFileReader.loadImage("ru/boost/ui/icon/masha.jpg"));
        data[2] = new Person(2,"Даша", ImageFileReader.loadImage("ru/boost/ui/icon/dasha.jpg"));
        data[3] = new Person(3,"Миша", ImageFileReader.loadImage("ru/boost/ui/icon/misha.jpg"));
        data[4] = new Person(4,"Кеша", ImageFileReader.loadImage("ru/boost/ui/icon/kesha.jpg"));
        return data;
    }

}
