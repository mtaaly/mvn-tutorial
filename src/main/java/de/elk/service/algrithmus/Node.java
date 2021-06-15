package de.elk.service.algrithmus;

public class Node<T extends Comparable>{
    T elt;
    Node<T>next;

    Node( T elt){
        this.elt=elt;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getElt() {
        return elt;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
    public void show(){
        System.out.println(elt);
        Node<T>temp=next;
        while (temp!=null){
            System.out.print("<"+temp.elt);
            temp=temp.next;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "elt=" + elt +
                '}';
    }
}
