package de.elk.service.collection;

import de.elk.service.basic.NotImplementedException;
import de.elk.service.basic.Person;

public class Femme extends Person {
    @Override
    public String gender() throws  NotImplementedException {
      throw new NotImplementedException();
    }
}
