package ru.boost.database.settings;

import java.io.*;
import java.util.HashMap;

public abstract class Config {

    protected Config(){
        load();
    }

    private HashMap<String,String> properties;

    protected abstract String getConfigFile();
    protected abstract void fillProperties();
    protected abstract void saveProperties();
    protected abstract void fillDefault();

    protected final void load(){
        readConfig();
        fillProperties();
    }

    protected final void save(){
        properties.clear();
        saveProperties();
        writeConfig();
    }

    protected final String getProperty(String key){
        return properties.get(key);
    }

    protected final void setProperty(String key, String value){
        properties.put(key,value);
    }

    protected final boolean addProperty(String key, String value){
        if(!properties.containsKey(key)){
            properties.put(key,value);
            return true;
        }
        return false;
    }

    private void readConfig(){
        if(properties == null){
            properties = new HashMap<>();
        }
        properties.clear();
        File file = new File(getConfigFile());
        if(file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] LINE = line.split("=");
                    if(LINE.length == 2){
                        String key = LINE[0];
                        String value = LINE[1];
                        properties.put(key,value);
                    }
                }
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                System.out.println("readConfig("+getConfigFile()+"): An error occurred: " + e.getMessage());
            }
        }
        else{
            System.out.println("Config file not exist!");
            fillDefault();
        }
    }

    private void writeConfig(){
        try {
            File file = new File(getConfigFile());
            if(!file.exists()){
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    if (!parentDir.mkdirs()) {
                        throw new IOException("Can't create folder: " + parentDir);
                    }
                }
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true);
            for(String key : properties.keySet()){
                String value = properties.get(key);
                writer.write(key+"="+value+System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("writeConfig("+getConfigFile()+"): An error occurred: " + e.getMessage());
        }
    }
}
