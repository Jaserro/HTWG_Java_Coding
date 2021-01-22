package Minimale_Baeume.src.telefon.TelNetApplication;

import telefon.princetonStdLib.StdDraw;

import java.util.*;

public class TelNet {
    private final int lbg;
    private boolean computed = false;
    private Map<TelKnoten,Integer> knoten;
    private PriorityQueue<TelVerbindung> verbindungen;
    private List<TelVerbindung> minimalBaum;

    public TelNet(int lbg){
        this.lbg = lbg;
        knoten = new HashMap<>();
        verbindungen = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
        minimalBaum = new LinkedList<>();
    }

    public boolean addTelKnoten(int x, int y){
        TelKnoten telKnoten = new TelKnoten(x,y);
        if (knoten.containsKey(telKnoten)){
            return false;
        }
        knoten.forEach((k,v) -> {if ((Math.abs(k.x - x) + Math.abs(k.y - y)) <= lbg){
            verbindungen.add(new TelVerbindung(k,telKnoten,Math.abs(k.x - x) + Math.abs(k.y - y)));}});
        knoten.put(telKnoten,knoten.size());
        return true;
    }

    public boolean computeOptTelNet(){
        UnionFind forest = new UnionFind(knoten.size());

        while (forest.size() != 1 && !verbindungen.isEmpty()){
            TelVerbindung min = verbindungen.poll();
            int t1 = forest.find(knoten.get(min.u));
            int t2 = forest.find(knoten.get(min.v));
            if (t1 != t2){
                forest.union(t1,t2);
                minimalBaum.add(min);
            }
        }
        if (!(verbindungen.isEmpty() || forest.size() != 1)) {
            computed = true;
        }
        return computed;
    }

    public void drawOptTelNet(int xMax, int yMax){
        if (computed){
            StdDraw.setXscale(0,xMax+1);
            StdDraw.setYscale(0,yMax+1);
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            knoten.forEach((k,v)->StdDraw.point(k.x,k.y));
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            minimalBaum.forEach(v -> StdDraw.line(v.u.x,v.u.y,v.v.x,v.v.y));
            System.out.println("Drawing complete");
        } else {
            throw new IllegalStateException();
        }
    }

    public void generateRandomTelNet(int n, int xMax, int yMax){
        while (knoten.size() < n) {
            Random rand = new Random();
            int x = rand.nextInt(xMax);
            int y = rand.nextInt(yMax);
            addTelKnoten(x,y);
        }
    }

    public List<TelVerbindung> getOptTelNet(){
        if (computed) return minimalBaum;
        throw new IllegalStateException();
    }

    public int getOptTelNetKosten(){
        int cost = 0;
        if (computed){
            for (TelVerbindung x:minimalBaum) {
                cost += x.c;
            }
            return cost;
        }
        throw new IllegalStateException();
    }

    public int size(){return knoten.size();}

    public static void main(String[] args) {
        TelNet net = new TelNet(7);
        net.addTelKnoten(1,1);
        net.addTelKnoten(3,1);
        net.addTelKnoten(4,2);
        net.addTelKnoten(3,4);
        net.addTelKnoten(7,5);
        net.addTelKnoten(2,6);
        net.addTelKnoten(4,7);
        System.out.println(net.computeOptTelNet());
        System.out.println(net.getOptTelNetKosten());
        System.out.println(net.getOptTelNet());
        TelNet net2 = new TelNet(100);
        net2.generateRandomTelNet(1000,1000,1000);
        System.out.println(net2.computeOptTelNet());
        System.out.println(net2.getOptTelNetKosten());
        net2.drawOptTelNet(1000,1000);
    }
}
