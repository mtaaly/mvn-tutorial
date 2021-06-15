package de.elk.service.collection;

import java.util.Collection;

public class CollectionWrapper<T> {

    private final Collection<T> collection;

    public CollectionWrapper(Collection<T> collection){
        this.collection =collection;
    }

    public void add(T element){
        collection.add(element);
    }
    public void remove(T element){
        collection.remove(element);
    }
}
