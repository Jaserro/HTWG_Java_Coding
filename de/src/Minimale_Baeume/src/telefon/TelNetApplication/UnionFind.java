package Minimale_Baeume.src.telefon.TelNetApplication;

public class UnionFind {
    private int[] unionTree;
    int size;
    public UnionFind(int n){
        unionTree = new int[n];
        size = n;
        for (int x = 0; x < unionTree.length; x++) {
            unionTree[x] = -1;
        }
    }

    public int find(int e){
        if (e >= unionTree.length){
            throw new IllegalArgumentException("Eingabe nicht in der Grundmenge enthalten");
        }
        while (unionTree[e] >= 0){// e ist keine Wurzel
            e = unionTree[e];
        }
        return e;
    }

    public int size(){
        return size;
    }

    public void union(int s1, int s2){
        if (s1 >= unionTree.length || s2 >= unionTree.length){
            throw new IllegalArgumentException("Eingabe nicht in der Grundmenge enthalten");
        }
        if (unionTree[s1] >= 0 || unionTree[s2] >= 0) return;
        if (s1 == s2) return;

        if (-unionTree[s1] < -unionTree[s2]){ //Höhe von s1 < Höhe von s2
            unionTree[s1] = s2;
        } else {
            if (-unionTree[s1] == -unionTree[s2]){
                unionTree[s1]--;
            }
            unionTree[s2] = s1;
        }
        size--;
    }

    public static void main(String[] args) {
        UnionFind u = new UnionFind(10);
        System.out.println(u.size()); //10
        u.union(3,7);
        u.union(3,0);
        u.union(3,3);
        u.union(5,7);
        System.out.println(u.size()); //8
        System.out.println(u.find(7)); //3
        u.union(5,2);
        u.union(3,5);
        System.out.println(u.find(2)); //3
        System.out.println(u.size()); //6
        u.union(0,15); //Exception
    }
}
