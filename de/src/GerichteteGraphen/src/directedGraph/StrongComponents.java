// O. Bittel;
// 05-09-2018

package GerichteteGraphen.src.directedGraph;

import directedGraph.AdjacencyListDirectedGraph;
import directedGraph.DepthFirstOrder;
import directedGraph.DirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 02.03.2020
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(directedGraph.DirectedGraph<V> g) {
		KosarajuSharirAlgorithmus(g);
	}

	private void KosarajuSharirAlgorithmus(directedGraph.DirectedGraph<V> g) {
		directedGraph.DepthFirstOrder<V> dep = new DepthFirstOrder<>(g);
		//List<V> postOrder =  dep.postOrder();
		List<V> postOrderi =  new LinkedList<V>(dep.postOrder());
		Collections.reverse(postOrderi);

		directedGraph.DirectedGraph<V> gi = g.invert();

		Set<V> besucht = new TreeSet<>();
		for (var v: postOrderi) {
			if(!besucht.contains(v)){
				comp.put(comp.size(),new TreeSet<>());
				visitDF(v,gi,besucht);

			}
		}

	}

	private void visitDF(V v, directedGraph.DirectedGraph<V> g, Set<V> besucht) {
		besucht.add(v);
		comp.get(comp.size()-1).add(v);
		for(var w: g.getSuccessorVertexSet(v)){
			if(!besucht.contains(w)){
				visitDF(w,g,besucht);
			}
		}
	}

	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < comp.size(); i++) {
			sb.append("Component ").append(i).append(": ");
			for (var v : comp.get(i)) {
				sb.append(v).append(",");
			}
			sb.append("\n");
		}

		return sb.toString();
	}
	
	/**
	 * Liest einen gerichteten Graphen von einer Datei ein. 
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static directedGraph.DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		directedGraph.DirectedGraph<Integer> g = new directedGraph.AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextLine();
        sc.nextLine();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}
	
	private static void test1() {
		directedGraph.DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7, 
        	// Component 1: 8, 
            // Component 2: 1, 2, 3, 
            // Component 3: 4, 
	}
	
	private static void test2() throws FileNotFoundException {
		DirectedGraph<Integer> g = readDirectedGraph(new File("de/GerichteteGraphen/mediumDG.txt"));
		System.out.println(g.getNumberOfVertexes());
		System.out.println(g.getNumberOfEdges());
		System.out.println(g);
		
		System.out.println("");
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println(sc.numberOfComp());  // 10
		System.out.println(sc);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		test1();
		test2();
	}
}
