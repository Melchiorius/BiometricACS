package ru.boost.network.common.businesslogic.service;

import ru.boost.network.common.businesslogic.entity.Person;

import javax.swing.*;
import java.util.List;

public interface PersonService {
    static PersonService getInstance(){
        return PersonServiceImpl.getInstance();
    }

    void insertPerson(long id, String name, ImageIcon image);
    default void insertPerson(Person person){
        insertPerson(person.getId(),person.getName(),person.getImage());
    }
    List<Person> getAllPersons();
    void updatePerson(long id, String name, ImageIcon image, boolean access);
    default void updatePerson(Person person){
        updatePerson(person.getId(),person.getName(),person.getImage(), person.isAccess());
    }
    void deletePerson(long id);
}
