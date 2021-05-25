package de.elk.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
public class PersonServiceIT {

    @Test
    public void testFindePerson() {
        assertNotNull(new PersonService().findePerson("Marcellin"));
        assertNull(new PersonService().findePerson("Vianney"));
    }

    @Test
    public void testAddPerson() {
        PersonService personService = new PersonService();
        assertNull(personService.findePerson("Vianney"));
        personService.addPerson("Vianney",14);
        assertNotNull(personService.findePerson("Vianney"));
    }

    @Test
    public void testfindPersonInStreet_EmptyStreet() {
        PersonService personService = new PersonService();
        assertTrue(personService.findPersonInStreet(null).isEmpty());
    }
    @Test
    public void testfindPersonInStreet_PersonInStreet() {
        PersonService personService = new PersonService();
        Person newPerson=new Person(12,"tester");
        Adresse adresse=new Adresse();
        adresse.setStreet("test street");
        newPerson.setAdresse(adresse);
        assertTrue(personService.findPersonInStreet("test street").isEmpty());
        Datenbank.getPersonHashMap().put("tester",newPerson);
        assertFalse(personService.findPersonInStreet("test street").isEmpty());
    }
}