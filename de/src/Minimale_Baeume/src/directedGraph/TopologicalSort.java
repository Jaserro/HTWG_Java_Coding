// O. Bittel;
// 22.02.2017

package Minimale_Baeume.src.directedGraph;

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	private DirectedGraph<V> g;

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
		this.g = g;
		topSort(g);
    }

    private void topSort(DirectedGraph<V> g){

		Queue<V> q = new LinkedList<>();
		Map<V,Integer>  inDegree = new TreeMap<>();


		for (var v : g.getVertexSet()) {
			inDegree.put(v,g.getInDegree(v));
			if(inDegree.get(v) == 0){
				q.add(v);
			}
		}

		while (!q.isEmpty()){
			V v = q.remove();
			ts.add(v);
			for (var w: g.getSuccessorVertexSet(v)) {
				inDegree.put(w,inDegree.get(w)-1);
				if(inDegree.get(w) == 0){
					q.add(w);
				}

			}
		}
	}

	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
		if(ts.size() != g.getNumberOfVertexes()){
			return  null;
		}else{
			return Collections.unmodifiableList(ts);
		}
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}

		DirectedGraph<String> anziehen = new AdjacencyListDirectedGraph<>();
		anziehen.addEdge("Unterhose", "Hose");
		anziehen.addEdge("Socken", "Schuhe");
		anziehen.addEdge("Unterhemd", "Hemd");
		anziehen.addEdge("Hose", "Schuhe");
		anziehen.addEdge("Hose", "Guertel");
		anziehen.addEdge("Hemd", "Pulli");
		anziehen.addEdge("Pulli", "Mantel");
		anziehen.addEdge("Guertel", "Mantel");
		anziehen.addEdge("Schuhe", "Handschuhe");
		anziehen.addEdge("Mantel", "Schal");
		anziehen.addEdge("Schal", "Handschuhe");
		anziehen.addEdge("Muetze", "Handschuhe");

		TopologicalSort<String> ts2 = new TopologicalSort<>(anziehen);

		if (ts2.topologicalSortedList() != null) {
			System.out.println(ts2.topologicalSortedList());
		}

		anziehen.addEdge("Schal","Hose");

		TopologicalSort<String> ts3 = new TopologicalSort<>(anziehen);

		if (ts3.topologicalSortedList() != null) {
			System.out.println(ts3.topologicalSortedList());
		}else{
			System.out.println("Anziehen fehlgeschlagen");
		}

	}
}
