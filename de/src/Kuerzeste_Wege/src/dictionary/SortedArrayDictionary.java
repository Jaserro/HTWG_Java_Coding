package Kuerzeste_Wege.src.dictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {


    private static final int DEF_CAPACITY = 32;
    private int size;
    private Dictionary.Entry<K,V>[] data;
    private Comparator cmp;

    public SortedArrayDictionary() {
        size = 0;
        data = new Dictionary.Entry[DEF_CAPACITY];
        cmp = Comparator.naturalOrder();
    }

    public SortedArrayDictionary(Comparator <? super K> pcmp){
        size = 0;
        data = new Dictionary.Entry[DEF_CAPACITY];
        cmp = pcmp;
    }

    private int searchKey(K key) {
        /*for (int i = 0; i < size; i++) {
            if (data[i].getKey().equals(key)) {
                return i;
            }
        }*/
        int li = 0;
        int re = data.length-1;
        while (re >= li){
            int m = (li+re)/2;
            if (data[m] == null || key.compareTo(data[m].getKey()) < 0){
                re = m-1;
            }else if (key.equals(data[m].getKey())){
                return m;
            }else {
                li = m+1;
            }
        }
        return -1;
    }

    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);

        if (i >= 0){
            V r = data[i].getValue();
            data[i].setValue(value);
            return r;
        }
        if (data.length == size){
            data = Arrays.copyOf(data, 2*size);
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0){
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key, value);
        size++;
        return null;

    }

    @Override
    public V search(K key) {
        int i = searchKey(key);
        if (i >= 0) {
            return data[i].getValue();
        }else {
            return null;
        }
    }

    @Override
    public V remove(K key) {
        int i = searchKey(key);
        if (i == -1){
            return null;
        }

        V r = data[i].getValue();
        for (int j = 0; j < size-1; j++) {
            data[j] = data[j+1];
        }
        data[--size] = null;
        return r;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Dictionary.Entry<K, V>> iterator()  {
        return new Iterator<Dictionary.Entry<K, V>>() {
            private int pos = 0;
            @Override
            public boolean hasNext() {
                return pos < size;
            }

            @Override
            public Dictionary.Entry<K, V> next() {
                return data[pos++];
            }
        };
    }



}
