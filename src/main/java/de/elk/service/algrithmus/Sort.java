package de.elk.service.algrithmus;

import java.util.Arrays;

/**
 * Implementierung verschiedener Sortierverfahren.
 * @author Thomas Röfer
 */
public class Sort
{
    /**
     * Erzeugt die nächste Permutation einer Folge nach dem Algorithmus von
     * Dijkstra.
     * @param a Die Folge, die permutiert wird.
     * @param <T> Der Typ der Elemente der Folge.
     */
    public static <T extends Comparable<T>> void nextPerm(final T[] a)
    {
        int i, j;

        // Finde das letzte Element, das kleiner als sein Nachfolger ist.
        for (i = a.length - 2; i >= 0 && a[i].compareTo(a[i + 1]) >= 0; --i);
        if (i >= 0) {
            // Finde das letzte Element, das größer als a[i] ist.
            for (j = a.length - 1; a[j].compareTo(a[i]) <= 0; --j);

            // Beide austauschen.
            swap(a, i, j);
        }

        // Elemente ab a[i + 1] umdrehen.
        reverse(a, i + 1, a.length - 1);
    }

    /**
     * Invertiert die Reihenfolge aller Elemente in einem Teilbereich eines
     * Arrays.
     * @param a Das Array, das verändert wird.
     * @param l Die Untergrenze des Indexbereichs des Arrays, der invertiert
     *          wird.
     * @param r Die Obergrenze des Indexbereichs des Arrays, der invertiert
     *          wird.
     */
    private static void reverse(final Object[] a, int l, int r)
    {
        while (l < r) {
            swap(a, l++, r--);
        }
    }

    /**
     * Tauscht zwei Elemente eines Arrays aus.
     * @param a Das Array, in dem die beiden Elemente getauscht werden.
     * @param i Der Index des einen Elements.
     * @param j Der Index des anderen Elements.
     */
    private static void swap(final Object[] a, final int i, final int j)
    {
        final Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Naives Sortieren. Die Elemente des Arrays werden so lange permutiert,
     * bis sie nach ihrer natürlichen Ordnung aufsteigend sortiert sind.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void naiveSort(final T[] a)
    {
        while (!sorted(a)) {
            nextPerm(a);
        }
    }

    /**
     * Testet, ob ein Array nach der natürlichen Ordnung seiner Elemente
     * aufsteigend sortiert ist.
     * @param a Das Array, dessen Ordnung getestet wird.
     * @param <T> Der Typ der Elemente des Arrays.
     * @return Ist das Array sortiert?
     */
    private static <T extends Comparable<T>> boolean sorted(final T[] a)
    {
        int i;
        for (i = 1; i < a.length && a[i - 1].compareTo(a[i]) <= 0; ++i);
        return i >= a.length;
    }

    /**
     * Sortieren durch Einfügen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void insertionSort(final T[] a)
    {
        for (int i = 0; i < a.length; ++i) {
            // Einzufügendes Element sichern.
            final T value = a[i];

            // Rückwärts nach Einfügestelle suchen und dabei Platz schaffen.
            int j;
            for (j = i; j > 0 && a[j - 1].compareTo(value) > 0; --j) {
                a[j] = a[j - 1];
            }

            // Gesichertes Element an Einfügestelle schreiben.
            a[j] = value;
        }
    }

    /**
     * Sortieren durch Einfügen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * Das Suchen nach der Einfügestelle hat hier nur den Aufwand O(log N).
     * Das Verschieben hat aber weiterhin den Aufwand O(N).
     * Achtung: Da Arrays.binarySearch nicht zusichert, dass immer das
     * letzte Vorkommen gefunden wird, ist diese konkrete Implementierung
     * nicht stabil.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void insertionSort2(final T[] a)
    {
        for (int i = 0; i < a.length; ++i) {
            // Einzufügendes Element sichern.
            final T value = a[i];

            // Einfügestelle suchen. Wenn das gesuchte Element noch nicht vohanden ist,
            // ist sie die Stelle, an der es eingefügt werden müsste. Ist es bereits
            // vorhanden, wird der Index dahinter verwendet.
            final int insertionPos = Math.abs(Arrays.binarySearch(a, 0, i, value) + 1);

            // Platz schaffen. Dabei wird a[i] überschrieben.
            System.arraycopy(a, insertionPos, a, insertionPos + 1, i - insertionPos);

            // Gesichertes Element an Einfügestelle schreiben.
            a[insertionPos] = value;
        }
    }

    /**
     * Sortieren durch Auswählen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void selectionSort(final T[] a)
    {
        // Für alle Positionen im Array (außer der letzten).
        for (int i = 0; i < a.length - 1; ++i) {
            // Tausche das kleinste Element des Restarrays an diese Stelle.
            for (int j = i + 1; j < a.length; ++j) {
                if (a[i].compareTo(a[j]) > 0) {
                    swap(a, i, j);
                }
            }
        }
    }

    /**
     * BubbleSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void bubbleSort(final T[] a)
    {
        boolean swapped = true;

        // Laufe rückwärts durch das Array. Alles oberhalb von i ist bereits
        // sortiert. Der Bereich bis i ist auch sortiert, wenn im vorherigen
        // Durchgang nicht mehr getauscht wurde (-> Abbruch).
        for (int i = a.length - 1; i > 0 && swapped; --i) {
            // In diesem Durchlauf wurde noch nicht getauscht.
            swapped = false;

            // Laufe bis zur Obergrenze unter tausche jeweils aufeinander
            // folgende Elemente, wenn sie falsch angeordnet sind.
            for (int j = 0; j < i; ++j) {
                if (a[j].compareTo(a[j + 1]) > 0) {
                    swap(a, j + 1, j);
                    swapped = true;
                }
            }
        }
    }

    /**
     * Sortieren durch Mischen entsprechend der natürlichen Ordnung der
     * Elemente in einem Array.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void mergeSort(final T[] a)
    {
        // Zwischenspeicher anlegen.
        @SuppressWarnings("unchecked")
        final T[] temp = (T[]) new Comparable[a.length];

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
    private static <T extends Comparable<T>> void mergeSort(final T[] a,
                                                            final int bottom, final int top, final T[] temp)
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
    private static <T extends Comparable<T>> void merge(final T[] a,
                                                        final int bottom, final int middle, final int top, final T[] temp)
    {
        // i durchläuft den ersten Teilbereich, j den zweiten und k den
        // zusammengemischten Bereich.
        int i = bottom;
        int j = middle;
        int k = 0;

        // Solange beide Teilarrays noch nicht leer, kopiere kleineres Element.
        while (i < middle && j < top) {
            temp[k++] = a[i].compareTo(a[j]) <= 0 ? a[i++] : a[j++];
        }

        // Reste kopieren. Eines der Teilarrays ist leer, weshalb nur ein
        // Kopiervorgang tatsächlich ausgeführt wird.
        System.arraycopy(a, i, temp, k, middle - i);
        System.arraycopy(a, j, temp, k, top - j);

        // Zurück in Originalarray kopieren.
        System.arraycopy(temp, 0, a, bottom, top - bottom);
    }
}

