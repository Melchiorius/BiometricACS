package ru.boost.network.client.model;

import ru.boost.network.common.businesslogic.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class ClientModel {
    private List<Person> persons;

    public ClientModel() {
        this.persons = new ArrayList<>();
    }

    public void setData(List<Person> persons){
        this.persons = persons;
    }

    public Person[] getData(){
        return persons.stream().toArray(Person[]::new);
    }

    public void update(long id, String name, boolean access){
        Person person = find(id);
        if(person != null){
            person.setName(name);
            person.setAccess(access);
        }
    }

    public void delete(long id){
        Person person = find(id);
        if(person != null){
            persons.remove(person);
        }
    }

    private Person find(long id){
        for(Person person : persons){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }
}
