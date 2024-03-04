package ru.boost.network.common.businesslogic.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Person {
    private final long id;
    private String name;
    private ImageIcon image;
    private boolean access;

    public Person(long id, String name, ImageIcon image) {
        this.id = id;
        this.name = name;
        this.image = image;
        access = false;
    }

    public Person(long id, String name, byte[] image) {
        this(id,name, setImage(image));
    }

    @JsonCreator
    public Person(@JsonProperty("id")long id, @JsonProperty("name")String name, @JsonProperty("image")String image) {
        this(id,name, Base64.getDecoder().decode(image));
    }

    @JsonGetter("id")
    public long getId() {
        return id;
    }
    @JsonGetter("name")
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

    private static ImageIcon setImage(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            if (bufferedImage == null) {

                return null;
            }
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
    @JsonGetter("access")
    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public void copy(Person newPerson){
        this.name = newPerson.getName();
        this.image = newPerson.getImage();
        this.access = newPerson.isAccess();
    }

    @JsonGetter("image")
    public byte[] getImageBytes() {
        if (image == null) {
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(
                image.getIconWidth(),
                image.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        image.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return byteArrayOutputStream.toByteArray();
    }


}
