package de.elk.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionTest {

    @Test
    public void testAdd() {
        Addition addition=new Addition();
        assertEquals(3,addition.add(1,2));
    }
}