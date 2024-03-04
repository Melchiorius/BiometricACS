package ru.boost.network.common.businesslogic.model;

import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.network.common.businesslogic.setttings.Settings;
import ru.boost.network.common.businesslogic.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

public class BaseModel implements IBaseModel{
    private boolean running;

    private List<Person> persons;



    public BaseModel() {
        this.running = true;
        loadPersons();
    }

    private void loadPersons(){
        persons = PersonService.getInstance().getAllPersons();
        if(Settings.debug && persons.isEmpty()){
            loadDefault();
        }
    }

    private void loadDefault(){
        Person[] persons = Settings.getDebugData();
        for(Person person : persons){
            PersonService.getInstance().insertPerson(person);
        }
        this.persons = PersonService.getInstance().getAllPersons();
    }

    private Person findPersonById(long id){
        for(Person person : persons){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }

    public List<Person> getAllPerson(){
        return persons.stream().collect(Collectors.toList());
    }

    public void updatePerson(long id, String name, boolean access){
        Person person = findPersonById(id);
        if(person != null){
            person.setName(name);
            person.setAccess(access);
            PersonService.getInstance().updatePerson(person);
        }
    }

    public void deletePerson(long id){
        Person person = findPersonById(id);
        if(person != null){
            persons.remove(person);
            PersonService.getInstance().deletePerson(id);
        }
    }


    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
