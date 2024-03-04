/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.boost.database.dao.person;

import ru.boost.database.DBController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author borisost
 */
public class PersonDAOImpl implements PersonDAO {

    private static String tableName = "person";

    private final String createTableSql = "CREATE TABLE "+tableName+" (" +
            " id SERIAL PRIMARY KEY," +
            " name VARCHAR(255)," +
            " image BYTEA," +
            " access BOOLEAN" +
            ")";

    private static PersonDAOImpl instance;

    public static PersonDAO getInstance() {
        if(instance == null){
            System.out.println("UserDAOImpl:getInstance():NEW");
            instance = new PersonDAOImpl();
        }
        return instance;
    }

    private PersonDAOImpl(){
        if(!DBController.getInstance().isTableExists(tableName)){
            try {
                DBController.getInstance().createTable(createTableSql);
            }
            catch(SQLException ee){
                System.out.println(ee);
            }
        }
    }

    public static void main(String[] args) {
        getInstance();
    }

    public void insert(PersonPOJO person) throws SQLException {
        String query = "INSERT INTO "+tableName+" (name, image, access) VALUES (?, ?, ?)";
        Connection connection = DBController.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getName());
            statement.setBytes(2, imageToByteArray(person.getImage()));
            statement.setBoolean(3, person.isAccess());
            statement.executeUpdate();
        }
        finally {
            connection.close();
        }
    }

    public List<PersonPOJO> getAll() throws SQLException {
        List<PersonPOJO> persons = new ArrayList<>();
        String query = "SELECT * FROM "+tableName;
        Connection connection = DBController.getInstance().getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                byte[] imageBytes = resultSet.getBytes("image");
                ImageIcon image = byteArrayToImage(imageBytes);
                boolean access = resultSet.getBoolean("access");
                PersonPOJO person = new PersonPOJO(id, name, image);
                person.setAccess(access);
                persons.add(person);
            }
        }
        finally {
            connection.close();
        }
        return persons;
    }

    public void update(PersonPOJO person) throws SQLException {
        String query = "UPDATE "+tableName+" SET name = ?, image = ?, access = ? WHERE id = ?";
        Connection connection = DBController.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getName());
            statement.setBytes(2, imageToByteArray(person.getImage()));
            statement.setBoolean(3, person.isAccess());
            statement.setLong(4, person.getId());
            statement.executeUpdate();
        }
        finally {
            connection.close();
        }
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM "+tableName+" WHERE id = ?";
        Connection connection = DBController.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        finally {
            connection.close();
        }
    }

    /**
     * Метод для преобразования изображения ImageIcon в массив байтов
     * @param imageIcon
     * @return
     */
    private byte[] imageToByteArray(ImageIcon imageIcon) {
        if (imageIcon == null) {
            return null;
        }

        BufferedImage bufferedImage = new BufferedImage(
                imageIcon.getIconWidth(),
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        imageIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Метод для преобразования массива байтов в изображение ImageIcon
     * @param byteArray
     * @return
     */
    private ImageIcon byteArrayToImage(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return new ImageIcon(byteArray);
    }
}
