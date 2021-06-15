package de.elk.service.basic;

public interface Walkable {

    void walk();

    default void doSomething(){
        System.out.println("test");
    }
}
