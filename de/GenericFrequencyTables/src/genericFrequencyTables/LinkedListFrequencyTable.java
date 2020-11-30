package genericFrequencyTables;

import java.net.StandardSocketOptions;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Jan-Niclas Serr + Simon Warmers
 * @since 5.5.2020
 */
public class LinkedListFrequencyTable<T> extends AbstractFrequencyTable<T> {

    public LinkedListFrequencyTable() {
        clear();
    }

    private class Node {
        private Node next;
        private Node prev;
        private Word<T> data;

        public Node(Word<T> x, Node n, Node p) {
            data = x;
            next = n;
            prev = p;
        }
    }

    private Node begin;
    private Node end;
    private int size;

    @Override
    public Word<T>[] getFqTable() {
        Word<T> temp[] = new Word[size - 1];
        Node p = begin;
        for (int i = 0; i < size - 1; i++) {
            temp[i] = p.data;
            p = p.next;
        }
        return temp;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public Iterator<Word<T>> iterator() {
        return new LinkedListFrequencyTableIterator();
    }


    private class LinkedListFrequencyTableIterator implements Iterator<Word<T>>{
        private Node p = begin;

        @Override
        public boolean hasNext() {
            return p.next.next != null;
        }

        @Override
        public Word<T> next() {
            p = p.next;
            return p.data;
        }
    }

    @Override
    public void clear() {
        begin = new Node(null, null, null);
        end = new Node(null, null, begin);
        begin.next = end;
        size = 0;
    }

    @Override
    public void add(T w, int f) {

        Node p = begin.next;
        while (p.next != null && !p.data.getWord().equals(w)) { //Liste durchlaufen
            p = p.next;
        }
        if (p.next == null) { //Neues Wort am Ende einfügen
            Word x = new Word(w, f);
            Node r = new Node(x, p, p.prev);
            r.prev.next = r;
            p.prev = r;
            p = r;
            size++;
        } else if (p.data.getWord().equals(w)) { //Anzahl eines vorhandenen Wortes erhöhen
            p.data.addFrequency(f);

        }
        Node old = p;
        while (p.prev.prev != null && p.prev.data.getFrequency() < old.data.getFrequency()) { //Liste durchlaufen
            p = p.prev;
        }
        while (p.prev.prev != null && p.prev.data.getWord().equals( old.data.getWord())
                && p.prev.data.getFrequency() == old.data.getFrequency()) {
            p = p.prev;
        }
        if (p.data.equals(old)) {

        }
        Node r = new Node(old.data, p, p.prev); //Knoten einfügen
        r.prev.next = r;
        p.prev = r;
        old.prev.next = old.next; //alten Knoten löschen
        old.next.prev = old.prev;

    }

    @Override
    public Word<T> get(int pos) {
        if (pos >= size) {
            return null;
        }
        Node p = begin.next; //Listenplatz 0
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }
        return p.data;
    }

    @Override
    public int get(Object w) {
        Node p = begin.next;
        while (p.next != null && !p.data.getWord().equals(w)) {
            p = p.next;
        }
        if (p.next == null) {
            return 0;
        }
        return p.data.getFrequency();
    }
}
