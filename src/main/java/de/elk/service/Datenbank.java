package de.elk.service;

import java.util.HashMap;

public class Datenbank {

    private static HashMap<String,Person> personHashMap=new HashMap<>();

    static {
        personHashMap.put("Marcellin",new Person(37,"Marcellin"));
        personHashMap.put("Derrick",new Person(34,"Derrick"));
        personHashMap.put("Nelson",new Person(36,"Nelson"));
    }

    public Person findePerson(String name){
        return personHashMap.get(name);
    }

    public void addPerson(String name,int age){
        if(findePerson(name)==null){
            personHashMap.put(name,new Person(age,name));
        }else {
            throw new RuntimeException("Person already in DB");
        }
    }
}
