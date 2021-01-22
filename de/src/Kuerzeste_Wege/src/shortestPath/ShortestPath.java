package Kuerzeste_Wege.src.shortestPath;

import directedGraph.*;
import sim.SYSimulation;

import java.awt.*;
import java.util.*;
import java.util.List;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; // Distanz für jeden Knoten
	Map<V,V> pred; // Vorgänger für jeden Knoten
	DirectedGraph<V> dg;
	Heuristic<V> dh;
	V start;
	V end;
	LinkedList<V> path;
	// ...

	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		this.dg = g;
		this.dh = h;
		this.dist = new TreeMap<>();
		this.pred = new TreeMap<>();
		this.path = new LinkedList<>();
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g) {
		this.start = s;
		this.end = g;
		this.dist = new TreeMap<>();
		this.pred = new TreeMap<>();
		this.path = new LinkedList<>();

		List<V> kl = new ArrayList<>();
		for (var v : dg.getVertexSet() ) {
			dist.put(v,Double.POSITIVE_INFINITY);
			pred.put(v,null);
		}
		dist.put(s,0.0);
		kl.add(s);

		while(!kl.isEmpty()){

			V min = null;
			double distanz = Double.POSITIVE_INFINITY;

			for (var v :kl ) {
				if(dh != null){
					if((dist.get(v) + dh.estimatedCost(v,g) ) < distanz){
						min = v;
						distanz = dist.get(v) + dh.estimatedCost(v,g);
					}
				}else {
					if(dist.get(v) < distanz){
						min = v;
						distanz = dist.get(v);
					}
				}
			}
			printCurrentVertex(min);
			if(g.equals(min)){
				return;
			}
			kl.remove(min);

			for (var w:	dg.getSuccessorVertexSet(min) ) {
				if(dist.get(w) == Double.POSITIVE_INFINITY){
					kl.add(w);
				}
				if(dist.get(min) + dg.getWeight(min,w) < dist.get(w)){
					pred.put(w,min);
					dist.put(w, dist.get(min) + dg.getWeight(min,w));
				}
			}
		}
	}

	private void printCurrentVertex(V v){
		if (sim != null)
			sim.visitStation((Integer) v, Color.BLUE);
	}


	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {

		LinkedList<V> path = new LinkedList<>();

		path.add(end);

		V current = pred.get(end);
		if(current == null){
			throw new IllegalArgumentException();
		}
		do
		{
			path.add(current);
		} while ((current = pred.get(current)) != start);
		path.add(start);

		Collections.reverse(path);
		return path;

	}

	public List<V> getShortestPathSteps() {
		return this.path;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		Double current = dist.get(end);
		if(current == null){
			throw new IllegalArgumentException();
		}
		return this.dist.get(end);
	}

}
