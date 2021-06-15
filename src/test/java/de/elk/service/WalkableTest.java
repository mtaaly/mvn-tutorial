package de.elk.service;

import de.elk.service.basic.Animal;
import de.elk.service.basic.Homme;
import de.elk.service.basic.Person;
import de.elk.service.basic.Walkable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalkableTest {

    @Test
    public void testWalk() {
        //Walkable walkable=new Homme();
        //
        //....
       // walkable.walk();
        Walkable walkable=new Animal();
        walkable.walk();

        Person homme=new Homme();
        //
        //....
        ((Walkable)homme).walk();
    }
}
