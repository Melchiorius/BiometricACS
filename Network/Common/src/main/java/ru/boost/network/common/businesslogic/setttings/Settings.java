package ru.boost.network.common.businesslogic.setttings;

import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.tools.file.ImageFileReader;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {

    private static Properties props = new Properties();;

    public static int RESTPort = 7654;
    public static int VideoPort = 8765;
    public static String RESTHost = "localhost";
    public static String VideoFilePath = "C:/Projects/test.mp4";

    public static boolean debug = true;

    public static Person[] getDebugData(){
        Person[] data = new Person[5];
        data[0] = new Person(0,"Петя", ImageFileReader.loadImage("ru/boost/ui/icon/petia.jpg"));
        data[1] = new Person(1,"Маша", ImageFileReader.loadImage("ru/boost/ui/icon/masha.jpg"));
        data[2] = new Person(2,"Даша", ImageFileReader.loadImage("ru/boost/ui/icon/dasha.jpg"));
        data[3] = new Person(3,"Миша", ImageFileReader.loadImage("ru/boost/ui/icon/misha.jpg"));
        data[4] = new Person(4,"Кеша", ImageFileReader.loadImage("ru/boost/ui/icon/kesha.jpg"));
        return data;
    }



    private static File getFile(boolean create){
        File file = new File(Paths.get("").toAbsolutePath()+"/"+"mainSettings.properties");
        if(!file.exists()){
            File dir = file.getParentFile();
            if(!create || dir == null || (!dir.exists() && !dir.mkdirs())){
                return null;
            }
        }
        return file;
    }

    public static void load(){
        File file = getFile(false);
        if(file == null){
            fillDefault();
            return;
        }
        try (InputStream is = new FileInputStream(file)) {
            props.load(is);
            fillProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillDefault(){
        props.setProperty("RESTHost",RESTHost);
        props.setProperty("RESTPort",RESTPort+"");
        props.setProperty("VideoPort",VideoPort+"");
        props.setProperty("VideoFilePath",VideoFilePath);
        props.setProperty("debug",debug+"");
        save();
    }

    private static void fillProperties(){
        RESTHost = props.getProperty("RESTHost",RESTHost);
        RESTPort = Integer.parseInt(props.getProperty("RESTPort",RESTPort+""));
        VideoPort = Integer.parseInt(props.getProperty("VideoPort",VideoPort+""));
        VideoFilePath = props.getProperty("VideoFilePath",VideoFilePath);
        debug = Boolean.parseBoolean(props.getProperty("debug",debug+""));
    }

    private static void saveProperties(){
        props.setProperty("RESTHost",RESTHost);
        props.setProperty("RESTPort",RESTPort+"");
        props.setProperty("VideoPort",VideoPort+"");
        props.setProperty("VideoFilePath",VideoFilePath);
        props.setProperty("debug",debug+"");
    }


    public static void save(){
        saveProperties();
        File file = getFile(true);
        if(file == null){ return; }
        try (OutputStream os = new FileOutputStream(file)) {
            props.store(os,"Settings:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
