package dictionary;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K, V> implements Dictionary<K, V> {

    private LinkedList<Entry<K,V>>[] tab;
    private static final int DEF_CAPACITY = 31;
    private int size;

    public HashDictionary(){
        tab = new LinkedList[DEF_CAPACITY];
        size = 0;
    }

    public HashDictionary(int i){
        size = 0;
        if(checkPrime(i)) {
            tab = new LinkedList[i];
        }else{
            tab = new LinkedList[getNextPrime(i)];
        }
    }

    @Override
    public V insert(K key, V value) {
        V oldV = search(key);
        if (oldV != null){
            int adr = searchAdr(key,tab);
            for (Entry<K,V> aE: tab[adr]) {
                if (key.equals(aE.getKey())){
                    aE.setValue(value);
                }
            }
        } else {
            int adr = searchAdr(key,tab);

            if (tab[adr] == null){
                tab[adr] = new LinkedList<>();
            }
            tab[adr].add(new Entry<>(key,value));
            size++;
            if (tab[adr].size() > 2){
                refactor();
            }
        }
        return oldV;
    }

    @Override
    public V search(K key) {

        int adr = searchAdr(key,tab);

        if (tab[adr] != null){

            for (Entry<K,V> aE: tab[adr]) {
                if (key.equals(aE.getKey())){
                   return aE.getValue();
                }
            }
        }
        return null;
    }

    public int searchAdr(K key,LinkedList<Entry<K,V>>[] list){
        int adr = key.hashCode();
        if (adr < 0){
            adr = -adr;
        }
        return adr % list.length;
    }

    @Override
    public V remove(K key) {
        int adr = searchAdr(key,tab);
        V oldV = search(key);
        for (int i = 0; i < tab[adr].size(); i++) {
            if (oldV != null){
                tab[adr].remove(i);
                size--;
            }
        }
        return oldV;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            int currentIndex = 0;
            int currentEntry = 0;
            Iterator<Entry<K, V>> listIt = null;

            @Override
            public boolean hasNext() {
                return currentEntry < size;
            }

            @Override
            public Entry<K, V> next() {
                assert hasNext();
                if (listIt == null || !listIt.hasNext()) {
                    while (tab[++currentIndex] == null || tab[currentIndex].size() == 0);
                    listIt = tab[currentIndex].iterator();
                }
                currentEntry++;
                return listIt.next();
            }
        };
    }

    private boolean checkPrime(int check){
        for (int i = 2; i < check; i++) {
            if (check % i == 0){
                return false;
            }
        }
        return true;
    }

    private int getNextPrime(int prime){
        prime++;
        while (!checkPrime(prime)){
            prime++;
        }
        return prime;
    }



    private void refactor(){
        LinkedList<Entry<K,V>>[] temp = new LinkedList[getNextPrime(tab.length * 2)];

        for (LinkedList<Entry<K,V>> ll: tab) {
            if (ll == null){
                continue;
            }
            for (Entry<K,V> aE: ll) {
                int adr = searchAdr(aE.getKey(),temp);//fix Adresse der neune Liste errechnet
                if (temp[adr] == null){
                    temp[adr] = new LinkedList<>();
                }
                temp[adr].add(new Entry<>(aE.getKey(),aE.getValue()));
            }
        }
        tab = temp;
    }

}
