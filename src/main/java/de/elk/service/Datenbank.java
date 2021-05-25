package de.elk.service;

import java.util.HashMap;
import java.util.Map;

public class Datenbank {

    private static HashMap<String,Person> personHashMap=new HashMap<>();

    static {
        personHashMap.put("Marcellin",new Person(37,"Marcellin"));
        personHashMap.put("Derrick",new Person(34,"Derrick"));
        personHashMap.put("Nelson",new Person(36,"Nelson"));
    }

    public Person findePerson(String name){
        for(Map.Entry<String,Person> keyEntry:personHashMap.entrySet()){

        }
        return personHashMap.get(name);
    }

    public void addPerson(String name,int age){
        if(findePerson(name)==null){
            personHashMap.put(name,new Person(age,name));
        }else {
            throw new RuntimeException("Person already in DB");
        }
    }

    public static HashMap<String, Person> getPersonHashMap() {
        return personHashMap;
    }
}
