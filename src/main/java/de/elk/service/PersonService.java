package de.elk.service;

public class PersonService {
    Datenbank datenbank=new Datenbank();

    Person findePerson(String name){
        return datenbank.findePerson(name);
    }

    void addPerson(String name,int age){
        datenbank.addPerson(name,age);
    }
}
