package ru.boost.database;

import ru.boost.database.settings.Settings;
import ru.boost.database.settings.properties.PIDataBase;

import java.io.File;
import java.nio.file.Paths;

public class DBConfig extends Settings implements PIDataBase {


    private String host;
    private int port;
    private String dbname;
    private String driver;
    private String userEnv;
    private String user;
    private String passwordEnv;
    private String password;

    private static DBConfig instance;

    public static DBConfig getInstance() {
        if (instance == null) {
            instance = new DBConfig();
        }
        return instance;
    }

    private DBConfig() {
        super();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUserEnv() {
        return userEnv;
    }

    public void setUserEnv(String userEnv) {
        this.userEnv = userEnv;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswordEnv() {
        return passwordEnv;
    }

    public void setPasswordEnv(String passwordEnv) {
        this.passwordEnv = passwordEnv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    protected String getSettingsFile() {
        return "settings.properties";
    }

    @Override
    public String getDBNameDefaultValue() {
        return "bacsdb";
    }

    @Override
    public String getDBUserDefaultValue() {
        return "postgres";
    }

    @Override
    public String getDBPasswordDefaultValue() {
        return "19860314";
    }

    @Override
    public String getDBHostDefaultValue() {
        return "localhost";
    }

    @Override
    public Integer getDBPortDefaultValue() {
        return 5432;
    }

    @Override
    public String getDBDriverDefaultValue() {
        return "jdbc:postgresql://";
    }

    @Override
    public String getDBEnvUserDefaultValue() {
        return "POSTGRES_USER";
    }

    @Override
    public String getDBEnvPasswordDefaultValue() {
        return "POSTGRES_PASSWORD";
    }

    @Override
    protected void fillProperties() {
        host = getProperty(getDBHostKey());
        port = Integer.parseInt(getProperty(getDBPortKey()));
        dbname = getProperty(getDBNameKey());
        driver = getProperty(getDBDriverKey());
        userEnv = getProperty(getDBEnvUserKey());
        user = getProperty(getDBUserKey());
        passwordEnv = getProperty(getDBEnvPasswordKey());
        password = getProperty(getDBPasswordKey());
    }

    @Override
    protected void fillDefault() {
        System.out.println("DBConfig:fillDefault():");
        setProperty(getDBHostKey(), getDBHostDefaultValue());
        setProperty(getDBPortKey(), getDBPortDefaultValue()+"");
        setProperty(getDBNameKey(), getDBNameDefaultValue());
        setProperty(getDBDriverKey(), getDBDriverDefaultValue());
        setProperty(getDBEnvUserKey(), getDBEnvUserDefaultValue());
        setProperty(getDBUserKey(), getDBUserDefaultValue());
        setProperty(getDBEnvPasswordKey(), getDBEnvPasswordDefaultValue());
        setProperty(getDBPasswordKey(), getDBPasswordDefaultValue());
        fillProperties();
        save();
    }

    @Override
    protected void saveProperties() {
        setProperty(getDBHostKey(), host);
        setProperty(getDBPortKey(), ""+port);
        setProperty(getDBNameKey(), dbname);
        setProperty(getDBDriverKey(), driver);
        setProperty(getDBEnvUserKey(), userEnv);
        setProperty(getDBUserKey(), user);
        setProperty(getDBEnvPasswordKey(), passwordEnv);
        setProperty(getDBPasswordKey(), password);
    }
}
