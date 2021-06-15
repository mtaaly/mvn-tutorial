package de.elk.service.basic;

public class Animal implements Walkable{

    @Override
    public void walk() {
        System.out.println("Animal can walk");
    }
}
