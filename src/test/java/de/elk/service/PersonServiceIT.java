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
}