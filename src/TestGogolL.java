import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class TestGogolL {
	
	private Set<Arc> arrayToColl(int[][] array) {
		Set<Arc> res = new TreeSet<>();
		for (int[] elt : array) {
			res.add(new Arc(elt[0], elt[1]));
		}
		return res;
	}
	
	private Set<Arc> arcsToEdge(Collection<Arc> coll) {
		Set<Arc> withEdges = new TreeSet<>();
		for (Arc arc : coll) {
			withEdges.add(arc);
			withEdges.add(arc.antiArc());
		}
		return withEdges;
	}
	
	private Set<Arc> getArcs(IGraph graph) {
		Set<Arc> res = new TreeSet<>();
		for (Integer vertex : graph.getVertices()) {
			for (Arc arc : graph.delta_out(vertex)) {
				res.add(arc);
			}
		}
		return res;
	}
	
	public boolean algoSuceeded(IGraph graph, List<Integer> path) {
		
		Set<Arc> pathhsss = new TreeSet<>();
		for (int i = 1; i < path.size(); i++) {
			Arc a = new Arc(path.get(i-1), path.get(i));
			pathhsss.add(a);
			pathhsss.add(a.antiArc());
		}
		
		Set<Arc> arcs = getArcs(graph);
		arcs.removeAll(pathhsss);
		return arcs.isEmpty();
	}
	
	private void testAlgoForEachVertex(IGraph graph) {
		testAlgoForEachVertex(graph, false);
	}
	
	private void testAlgoForEachVertex(IGraph graph, boolean debug) {
		for (Integer root : graph.getVertices()) {
			List<Integer> path = new GogolL(debug).algo(graph, root);
			//System.out.println(algoSuceeded(graph, path) + "\t" + path + "\t" + new GogolL().getAntiArbo(graph, root));
			assert(algoSuceeded(graph, path));
			assert(path.get(0) == path.get(path.size() - 1));
		}
		//System.out.println();
	}
	
	
	/* Graphs with no odd degree vertices */
	
	@Test
	public void testGraph1() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graph1.txt")));
		IGraph graph = new AdjacencyListGraph(euler);
		
		testAlgoForEachVertex(graph);
	}

	
	@Test
	public void testGraph2() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graph2.txt")));
		IGraph graph = new AdjacencyListGraph(euler);

		testAlgoForEachVertex(graph);
	}
	
	@Test
	public void testGraph3() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graphodd1.txt")));
		IGraph graph = new AdjacencyListGraph(euler);
		graph.addEdge(new Arc(4, 10));
		
		testAlgoForEachVertex(graph);
	}
	
	/* Graphs with 2 odd degree vertices */
	
	@Test
	public void testOddGraph1() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graph1.txt")));
		IGraph graph = new AdjacencyListGraph(euler);
		graph.addEdge(new Arc(1, 5));
		
		testAlgoForEachVertex(graph);
	}

	
	@Test
	public void testOddGraph2() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graph2.txt")));
		IGraph graph = new AdjacencyListGraph(euler);
		graph.addEdge(new Arc(4, 11));
		
		testAlgoForEachVertex(graph);
	}
	
	@Test
	public void testOddGraph3() {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File("graphs/graphodd1.txt")));
		IGraph graph = new AdjacencyListGraph(euler);
		
		testAlgoForEachVertex(graph);
	}
}
