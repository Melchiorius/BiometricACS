package ru.boost.database.settings.properties;


public interface PIDataBase extends PIProperties{
    default String getDBName(){
        String value = getProperties().getProperty(getDBNameKey());
        return value == null ? getDBNameDefaultValue() : value;
    }
    default String getDBUser(){
        String value = getProperties().getProperty(getDBUserKey());
        return value == null ? getDBUserDefaultValue() : value;
    }
    default String getDBPassword(){
        String value = getProperties().getProperty(getDBPasswordKey());
        return value == null ? getDBPasswordDefaultValue() : value;
    }
    default String getDBHost(){
        String value = getProperties().getProperty(getDBHostKey());
        return value == null ? getDBHostDefaultValue() : value;
    }
    default Integer getDBPort(){
        Integer value = null;
        try {
            value = Integer.parseInt(getProperties().getProperty(getDBPortKey()));
        }
        catch (NumberFormatException e){
            System.out.println(getClass().getName()+".getDBPort(): "+e);
        }
        return value == null ? getDBPortDefaultValue() : value;
    }
    default String getDBDriver(){
        String value = getProperties().getProperty(getDBDriverKey());
        return value == null ? getDBDriverDefaultValue() : value;
    }

    default String getDBEnvUser(){
        String value = getProperties().getProperty(getDBEnvUserKey());
        return value == null ? getDBEnvUserDefaultValue() : value;
    }

    default String getDBEnvPassword(){
        String value = getProperties().getProperty(getDBEnvPasswordKey());
        return value == null ? getDBEnvPasswordDefaultValue() : value;
    }

    default String getURL(){
        return getDBDriver() + getDBHost() + ":" + getDBPort() + "/";
    }
    default String[] getEnv(){
        return new String[]{getDBEnvUser()+"="+getDBUser(), getDBEnvPassword()+"="+getDBPassword()};
    }

    default String getDBDriverKey(){return "driver";}
    default String getDBNameKey(){return "dbname";}
    default String getDBUserKey(){return "user";}
    default String getDBPasswordKey(){return "password";}
    default String getDBHostKey(){return "host";}
    default String getDBPortKey(){return "port";}
    default String getDBEnvUserKey(){return "userEnv";}
    default String getDBEnvPasswordKey(){return "passwordEnv";}
    String getDBNameDefaultValue();
    String getDBUserDefaultValue();
    String getDBPasswordDefaultValue();
    String getDBHostDefaultValue();
    Integer getDBPortDefaultValue();
    String getDBDriverDefaultValue();
    String getDBEnvUserDefaultValue();
    String getDBEnvPasswordDefaultValue();

}
