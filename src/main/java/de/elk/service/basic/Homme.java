package de.elk.service.basic;

public class Homme extends Person implements Walkable{
    private String name;
    private String sex;
    private int age;//double,float, boolean


    @Override
    public void walk() {
        System.out.println("Homme can walk");
    }

    @Override
    public String gender() {
        return "masculin";
    }
}
