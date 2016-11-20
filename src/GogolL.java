import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GogolL {

	/* 2 groupes de sommets, edge dans anti_arbo et dans graphe, edge pas dans anti_arbo et dans graphe, edge deja visité ??? */
	public boolean DEBUG = false;
	private List<Integer> path = new ArrayList<>();
	private IGraph graph;
	
	public GogolL(IGraph graph) {
		this.graph = graph;
	}
	
	private static List<Integer> getCorresp(IGraph graph) {	
		List<Integer> corresp = new ArrayList<>();
		for (int vertex : graph.getVertices()) {
			corresp.add(vertex);
		}
		return corresp;
	}
	
	public static <T> Queue<T> flip(Queue<T> q) {
	    Queue<T> ret = new LinkedList<>();
	    recursiveFlip(q, ret);
	    return ret;
	}

	private static <T> void recursiveFlip(Queue<T> src, Queue<T> dest) {
	    T buffer = src.poll();
	    if(!src.isEmpty()) {
	        recursiveFlip(src, dest);
	    }
	    dest.offer(buffer);
	}
	
	Set<Integer> visited = new TreeSet<>();
	private Set<Arc> getAntiArbo(IGraph graph, int root) {
		if (!graph.isVertex(root)) {return null;}
		Set<Arc> arbo = new TreeSet<>();
		visited.add(root);
		for (Integer neighbour : graph.neighbours_out(root)) {
			if (!visited.contains(neighbour)) {
				arbo.add(new Arc(root, neighbour));
				arbo.addAll(getAntiArbo(graph, neighbour));
			}
		}
		return arbo;
	}
	
	private Map<Integer, LinkedList<Arc>> numberize(IGraph graph, Set<Arc> antiArbo, int root) {
		Map<Integer, LinkedList<Arc>> res = new HashMap<>();
		
		for (Integer vertex : graph.getVertices()) {
			Set<Arc> a = new TreeSet<>(graph.delta_out(vertex));
			a.retainAll(antiArbo);
			for (Arc arc : a) {
				if (res.get(vertex) == null) {
					res.put(vertex, new LinkedList<>());
				}
				res.get(vertex).add(arc);
			}
			Set<Arc> b = new TreeSet<>(graph.delta_out(vertex));
			b.removeAll(a);
			Set<Arc> ret = new TreeSet<>();
			for (Arc arc : b) {

				if (arc.dst() == root) {
					ret.add(arc);
					continue;
				}
				
				if (res.get(vertex) == null) {
					res.put(vertex, new LinkedList<>());
				}
				res.get(vertex).add(arc);
			}
			if (res.get(vertex) == null) {
				res.put(vertex, new LinkedList<>());
			}
			res.put(vertex, (LinkedList<Arc>) flip(res.get(vertex)));
			res.get(vertex).addAll(ret);

		}
		
		return res;
	}
	
	public List<Integer> algo(int vertex) {
		
		Set<Integer> oddVertices = new TreeSet<>();
		for (Integer i : graph.getVertices()) {
			if (graph.neighbours_out(i).size() % 2 == 1) {
				oddVertices.add(i);
			}
		}
		
		if (oddVertices.size() != 0 && oddVertices.size() != 2) {
			return null;
		}
		
		
		
		
		Set<Arc> antiArbo = getAntiArbo(graph, vertex);
		Map<Integer, LinkedList<Arc>> n = numberize(graph, antiArbo, vertex);
		Integer current = vertex;
		
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
		
		if (DEBUG) {
			System.out.println("Path : " + path);
		}
		
		return path;
	}

}
