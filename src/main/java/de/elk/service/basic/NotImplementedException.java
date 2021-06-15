package de.elk.service.basic;

public class NotImplementedException extends Exception{
    public NotImplementedException(){
        super("Not impelemented ...");
    }

    public NotImplementedException(String message){
        super(message);
    }
}
