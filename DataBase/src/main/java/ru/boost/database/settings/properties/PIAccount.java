package ru.boost.database.settings.properties;

public interface PIAccount extends PIProperties {

    default String getAccountLogin() {
        String value = getProperties().getProperty(getAccountLoginKey());
        return value == null ? getAccountLoginDefaultValue() : value;
    }

    default void setAccountLogin(String personalLogin) {
        getProperties().setProperty(getAccountLoginKey(),personalLogin);
    }

    default String getAccountPassword() {
        String value = getProperties().getProperty(getAccountPasswordKey());
        return value == null ? getAccountPasswordDefaultValue() : value;
    }

    default void setAccountPassword(String personalPassword) {
        getProperties().setProperty(getAccountPasswordKey(),personalPassword);
    }

    String getAccountLoginKey();
    String getAccountPasswordKey();
    String getAccountLoginDefaultValue();
    String getAccountPasswordDefaultValue();
}
