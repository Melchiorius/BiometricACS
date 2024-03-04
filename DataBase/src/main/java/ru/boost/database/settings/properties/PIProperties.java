package ru.boost.database.settings.properties;

import java.util.Properties;
public interface PIProperties {
     Properties getProperties();

     default void set(String key, String property){
          getProperties().setProperty(key,property);
     }

     default String get(String key){
          return getProperties().getProperty(key);
     }
}
