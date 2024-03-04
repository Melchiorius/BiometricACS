package ru.boost.network.common.businesslogic.service;

import ru.boost.database.dao.person.PersonPOJO;
import ru.boost.database.dao.person.PersonDAO;
import ru.boost.network.common.businesslogic.entity.Person;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private static PersonServiceImpl instance;
    private PersonDAO personDAO;

    public static PersonService getInstance() {
        if(instance == null){
            System.out.println("UserServiceImpl:getInstance():NEW");
            instance = new PersonServiceImpl();
        }
        return instance;
    }

    private PersonServiceImpl(){
        personDAO = PersonDAO.getInstance();
    }

    public void insertPerson(long id, String name, ImageIcon image) {
        try {
            PersonPOJO person = new PersonPOJO(id, name, image);
            personDAO.insert(person);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Person> getAllPersons() {
        try {
            List<PersonPOJO> personDBOs = personDAO.getAll();
            List<Person> persons = new ArrayList<>();
            for(PersonPOJO person : personDBOs){
                Person newPerson = new Person(person.getId(),person.getName(),person.getImage());
                newPerson.setAccess(person.isAccess());
                persons.add(newPerson);
            }
            return persons;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void updatePerson(long id, String name, ImageIcon image, boolean access) {
        try {
            PersonPOJO person = new PersonPOJO(id, name, image);
            person.setAccess(access);
            personDAO.update(person);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deletePerson(long id) {
        try {
            personDAO.delete(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
