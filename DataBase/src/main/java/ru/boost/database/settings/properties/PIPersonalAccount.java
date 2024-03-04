package ru.boost.database.settings.properties;

public interface PIPersonalAccount extends PIProperties{
    default String getPersonalLogin() {
        String value = getProperties().getProperty(getPersonalLoginKey());
        return value == null ? getPersonalLoginDefaultValue() : value;
    }

    default void setPersonalLogin(String personalLogin) {
        getProperties().setProperty(getPersonalLoginKey(),personalLogin);
    }

    default String getPersonalPassword() {
        String value = getProperties().getProperty(getPersonalPasswordKey());
        return value == null ? getPersonalPasswordDefaultValue() : value;
    }

    default void setPersonalPassword(String personalPassword) {
        getProperties().setProperty(getPersonalPasswordKey(),personalPassword);
    }

    String getPersonalLoginKey();
    String getPersonalPasswordKey();
    String getPersonalLoginDefaultValue();
    String getPersonalPasswordDefaultValue();
}
