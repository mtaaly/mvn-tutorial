package de.elk.service;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollecttionTest {

    @Test
    public void testListSize() {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        assertEquals(3,list.size());
    }

    @Test
    public void testSetSize() {
        Set<Integer> list=new HashSet<>();
        Set<Integer> listSet=new HashSet<>();
        list.add(1);
        list.add(2);
        list.add(2);
        assertEquals(2,list.size());
    }

    @Test
    public void testL() {
        List<Integer> list=new ArrayList<>();
        List<Integer> listLinked=new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.remove(2);
        assertEquals(2,list.size());
    }

}
