package ru.boost.database.settings;

import ru.boost.database.settings.properties.PIProperties;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class Settings implements PIProperties {
    protected Settings(){
        load();
    }
    private Properties props = new Properties();

    private File getFile(boolean create){
        File file = new File(Paths.get("").toAbsolutePath()+"/"+getSettingsFile());
        if(!file.exists()){
            File dir = file.getParentFile();
            if(!create || dir == null || (!dir.exists() && !dir.mkdirs())){
                return null;
            }
        }
        return file;
    }
    public void load(){
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

    public void save(){
        saveProperties();
        File file = getFile(true);
        if(file == null){ return; }
        try (OutputStream os = new FileOutputStream(file)) {
            props.store(os,"Settings:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    protected void setProperty(String key, String value){
        props.setProperty(key,value);
    }

    protected String getProperty(String key){
        return props.getProperty(key);
    }

    protected abstract void saveProperties();
    protected abstract void fillProperties();
    protected abstract void fillDefault();
    protected abstract String getSettingsFile();

}
