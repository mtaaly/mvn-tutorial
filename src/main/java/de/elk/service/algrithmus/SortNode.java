package de.elk.service.algrithmus;

import java.util.Arrays;

/**
 * Implementierung verschiedener Sortierverfahren.
 * @author Thomas Röfer
 */
public class SortNode
{

    private static <T extends Comparable<T>>int getListLength(Node<T> head) {
        if(head==null){
            return 0;
        }
        int lenght=1;
        Node<T> next=head.getNext();
        while (next!=null){
            next=next.getNext();
            lenght++;
        }
        return lenght;
    }

    public static <T extends Comparable<T>> void mergeSort( Node <T> a)
    {
        // Zwischenspeicher anlegen.
        @SuppressWarnings("unchecked")
        int length=getListLength(a);
        final Node<T>[] temp = new Node[length];
        Node<T> current=a;
        int index=0;
        while(current!=null){
            temp[index]=current;
            current=current.getNext();
            index++;
        }

        // Sortiere ganzes Array.
        mergeSort(temp);
        rebuildList(temp,a);


    }

    private static <T extends Comparable<T>>void rebuildList(Node<T>[] temp,Node<T> a) {
        for(int i=0;i< temp.length;i++){
            Node<T> node=temp[i];
            if(i+1< temp.length){
                node.next=temp[i+1];
            }else {
                node.next = null;
            }
        }
        Node<T> newHead=temp[0];
        Node<T> tempNode=a;
        while (newHead.next!=null){
            tempNode.elt= newHead.elt;
            tempNode.next=newHead.getNext();
        }

    }

    public static <T extends Comparable<T>> void mergeSort(final Node<T>[] a)
    {
        // Zwischenspeicher anlegen.
        @SuppressWarnings("unchecked")
        final Node<T>[] temp = (Node<T>[]) new Node[a.length];

        // Sortiere ganzes Array.
        mergeSort(a, 0, a.length, temp);
    }

    /**
     * Sortieren durch Mischen eines Teilbereichs eines Arrays.
     * @param a Das Array, in dem sortiert wird.
     * @param bottom Die Untergrenze des Teilbereichs (inklusiv).
     * @param top Die Obergrenze des Teilbereichs (exklusiv).
     * @param temp Ein Zwischenspeicher, der mindestens top - bottom
     *         Elemente groß ist.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void mergeSort(final Node<T>[] a,
                                                            final int bottom, final int top, final Node<T>[] temp)
    {
        if (top - bottom > 1) {
            final int middle = bottom + (top - bottom) / 2;
            mergeSort(a, bottom, middle, temp);
            mergeSort(a, middle, top, temp);
            merge(a, bottom, middle, top, temp);
        }
    }

    /**
     * Mischen zweier aufeinander folgender, bereits sortierter
     * Teilbereiche eines Arrays zu einem gemeinsamen, sortierten Bereich.
     * @param a Das Array, in dem sortiert wird.
     * @param bottom Die Untergrenze des ersten Teilbereichs (inklusiv).
     * @param middle Die Obergrenze des ersten Teilbereichs (exklusiv) und
     *         gleichzeitig die Untergrenze des zweiten Teilbereichs
     *         (inklusiv).
     * @param top Die Obergrenze des zweiten Teilbereichs (exklusiv).
     * @param temp Ein Zwischenspeicher, der mindestens top - bottom
     *         Elemente groß ist.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void merge(final Node<T>[] a,
                                                        final int bottom, final int middle, final int top, final Node<T>[] temp)
    {
        // i durchläuft den ersten Teilbereich, j den zweiten und k den
        // zusammengemischten Bereich.
        int i = bottom;
        int j = middle;
        int k = 0;

        // Solange beide Teilarrays noch nicht leer, kopiere kleineres Element.
        while (i < middle && j < top) {
            temp[k++] = a[i].getElt().compareTo(a[j].getElt()) <= 0 ? a[i++] : a[j++];
        }

        // Reste kopieren. Eines der Teilarrays ist leer, weshalb nur ein
        // Kopiervorgang tatsächlich ausgeführt wird.
        System.arraycopy(a, i, temp, k, middle - i);
        System.arraycopy(a, j, temp, k, top - j);

        // Zurück in Originalarray kopieren.
        System.arraycopy(temp, 0, a, bottom, top - bottom);
    }

    public static void main(String[] args) {
        Node<Integer> liste=new Node<>(8);
        liste.setNext(new Node<>(4));
        mergeSort(liste);
        liste.show();
    }
}

