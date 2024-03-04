package ru.boost.network.common.businesslogic.model;

import ru.boost.network.common.businesslogic.entity.Person;

import java.util.List;

public interface IBaseModel {


    List<Person> getAllPerson();

    void updatePerson(long id, String name, boolean access);

    void deletePerson(long id);

    boolean isRunning();

    void setRunning(boolean running);
}
