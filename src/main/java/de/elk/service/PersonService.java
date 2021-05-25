package de.elk.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PersonService {
    Datenbank datenbank=new Datenbank();

    Person findePerson(String name){
        return datenbank.findePerson(name);
    }

    void addPerson(String name,int age){
        datenbank.addPerson(name,age);
    }

    public List<Person> findPersonInStreet(String street){
        List<Person> result=new LinkedList<>();
        Map<String,Person> personMap=Datenbank.getPersonHashMap();

        for(Map.Entry<String,Person> data: personMap.entrySet()){
            Person person= data.getValue();
            if( person.getAdresse()!=null && person.getAdresse().getStreet()!=null && person.getAdresse().getStreet().equalsIgnoreCase(street)){
                result.add(person);
            }
        }

        return result;


    }
}
