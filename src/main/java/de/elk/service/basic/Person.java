package de.elk.service.basic;

public abstract class Person {
    private int age;
    public abstract String gender() throws NotImplementedException;

    public final String eatMeat(){
        return "I can eat meat";
    }

}
