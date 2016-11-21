import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GogolL {

	public boolean DEBUG = false;
	
	private List<Integer> path = new ArrayList<>();

	
	GogolL() {}
	GogolL(boolean DEBUG) {
		this.DEBUG = DEBUG;
	}
	
	/* Compute tree from graph */
	Set<Integer> visited = new TreeSet<>();
	
	public Set<Arc> getAntiArbo(IGraph graph, int root) {
		if (!graph.isVertex(root)) {return null;}
		Set<Arc> arbo = new TreeSet<>();
		visited.add(root);
		for (Integer neighbour : graph.neighbours_out(root)) {
			if (!visited.contains(neighbour)) {
				arbo.add(new Arc(root, neighbour).antiArc());
				arbo.addAll(getAntiArbo(graph, neighbour));
			}
		}
		return arbo;
	}
	
	/* Define visiting order of arcs */
	private Map<Integer, Queue<Arc>> numberize(IGraph graph, Set<Arc> antiArbo, int root) {
		
		Map<Integer, Queue<Arc>> res = new HashMap<>();
		
		for (Integer vertex : graph.getVertices()) {
			res.put(vertex, new LinkedList<>());
			
			/* Initializing types of arcs to visit */
			Set<Arc> arcsInTree = new TreeSet<>(graph.delta_out(vertex));
			arcsInTree.retainAll(antiArbo);
			
			Set<Arc> arcsNotInTree = new TreeSet<>(graph.delta_out(vertex));
			arcsNotInTree.removeAll(arcsInTree);
			
			Set<Arc> arcsToRoot = new TreeSet<>();
			for (Arc arc : arcsNotInTree) {
				if (arc.dst() == root) {
					arcsToRoot.add(arc);
				}
			}
			arcsNotInTree.removeAll(arcsToRoot);
			
			
			/* Adding all vertices to the queue */
			
			res.get(vertex).addAll(arcsToRoot);
			res.get(vertex).addAll(arcsNotInTree);
			res.get(vertex).addAll(arcsInTree);
		}
		
		return res;
	}
	
	/* Remplis path */
	public void doAlgo(IGraph graph, int root) {
		Set<Arc> antiArbo = getAntiArbo(graph, root);
		Map<Integer, Queue<Arc>> n = numberize(graph, antiArbo, root);
		Integer current = root;
		
		if (DEBUG) {
			System.out.println("Arborescence : " + antiArbo);
			System.out.println("Numberized : " + n);
			System.out.println("Current : " + current);
		}
		
		path.add(current);
		while (n.get(current) != null && !n.get(current).isEmpty()) {

			Arc a = n.get(current).poll();
			
			if (a == null) {break;}
			
			if (DEBUG) System.out.println("Chosen : " + a + "  " + n.get(current));

			
			/* Suppression de a.antiArc dans les arcs a parcourir */
			LinkedList<Arc> bbb = new LinkedList<>();
			for (Arc i : n.get(a.dst())) {
				if (!i.equals(a.antiArc())) {
					bbb.add(i);
				}
			}
			n.put(a.dst(), bbb);
			
			current = a.dst;
			path.add(current);
			
			if (DEBUG) {
				System.out.println(n);
				System.out.println("Current : " + current);
			}
		}
		
	}
	
	public List<Integer> algo(IGraph graph, int root) {
		
		Set<Integer> oddVertices = new TreeSet<>();
		for (Integer i : graph.getVertices()) {
			if (graph.neighbours_out(i).size() % 2 == 1) {
				oddVertices.add(i);
			}
		}
		
		if (DEBUG) System.out.println("OddVertices : " + oddVertices.size());
		
		if (oddVertices.size() != 0 && oddVertices.size() != 2) {
			return null;
		}
		
		int newRoot = root;
		/* if root is not an odd vertex finding the nearest odd vertex to begin the algorithm */
		if (!oddVertices.contains(root) && oddVertices.size() == 2) {
			List<Integer> nearestPath = null;
			for (Integer oddVertex : oddVertices) {
				List<Integer> res =	Dijkstra.dijkstra(graph, root, oddVertex);
				if (DEBUG) System.out.println(res);
				if (nearestPath == null || res.size() < nearestPath.size()) {
					nearestPath = res;
				}
			}
			newRoot = nearestPath.get(nearestPath.size() - 1);
			nearestPath.remove(nearestPath.size() - 1);
			path.addAll(nearestPath);
		}
		
		doAlgo(graph, newRoot);
		
		Integer lastVertex = path.get(path.size() - 1);
		if (lastVertex != root) {
			List<Integer> pathToRoot = Dijkstra.dijkstra(graph, lastVertex, root);
			pathToRoot.remove(0); // Removing first element so it doesn't appear twice in the path
			path.addAll(pathToRoot);
		}
		
		if (DEBUG) {
			System.out.println("Path : " + path);
		}
		
		return path;
	}
	
}
