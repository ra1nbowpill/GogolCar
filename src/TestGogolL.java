import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class TestGogolL {
	
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
	
	private void printEndInfo(IGraph graph, List<Integer> path, GogolL a) {
		Set<Arc> antiArbo = a.getAntiTree(graph, path.get(0));
		
		System.out.println(algoSuceeded(graph, path));
		System.out.println(path);
		System.out.println(antiArbo);
		System.out.println(a.numberize(graph,path.get(0), antiArbo));
	}
	
	private void testAlgoForEachVertex(IGraph graph, boolean debug) {
		for (Integer root : graph.getVertices()) {
			GogolL a = new GogolL();
			List<Integer> path = a.algo(graph, root);
			if (!algoSuceeded(graph, path))
				printEndInfo(graph, path, a);
			assert(algoSuceeded(graph, path));
			assert(path.get(0) == path.get(path.size() - 1));
		}
	}
	
	public IGraph graphOfPath(String path) {
		Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File(path)));
		return new AdjacencyListGraph(euler);
	}
	
	/* Graphs with no odd degree vertices */
	
	@Test
	public void testGraph1() {
		IGraph graph = graphOfPath("graphs/graph1.txt");
		
		testAlgoForEachVertex(graph);
	}

	
	@Test
	public void testGraph2() {
		IGraph graph = graphOfPath("graphs/graph2.txt");

		testAlgoForEachVertex(graph);
	}
	
	@Test
	public void testGraph3() {
		IGraph graph = graphOfPath("graphs/graphodd1.txt");
		graph.addEdge(new Arc(4, 10));
		
		testAlgoForEachVertex(graph);
	}
}
