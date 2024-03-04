package ru.boost.database.dao.person;

import javax.swing.*;

public class PersonPOJO {
    private final long id;
    private String name;
    private ImageIcon image;
    private boolean access;

    public PersonPOJO(long id, String name, ImageIcon image, boolean access) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.access = access;
    }

    public PersonPOJO(long id, String name, ImageIcon image) {
        this.id = id;
        this.name = name;
        this.image = image;
        access = false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
