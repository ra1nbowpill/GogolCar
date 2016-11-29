import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GogolL {

	private IGraph graph;
	private Ville city;

	private List<Element> path = new ArrayList<>();

	public GogolL(Ville city) {
		this.graph = city.graphe;
		this.city = city;
	}

	/**
	 * Compute antiTree from graph
	 * @param graph
	 * @param root the antiRoot
	 * @return set of arcs representing antiTree
	 */
	private List<Element> visited = new ArrayList<>();
	public List<Arc> getAntiTree(IGraph graph, Element root) {
		if (!graph.isVertex(root)) {return null;}
		List<Arc> arbo = new ArrayList<>();
		visited.add(root);
		for (Element neighbour : graph.neighbours_out(root)) {
			if (!visited.contains(neighbour)) {
				arbo.add(new Arc(root, neighbour, "").antiArc());
				arbo.addAll(getAntiTree(graph, neighbour));
			}
		}
		return arbo;
	}

	private List<Element> visited2 = new ArrayList<>();
	public List<Road> getAntiTree(Ville city, Element beginningPlace) {

		if (!city.placeExists((Place)beginningPlace)) {return null;}

		List<Road> arbo = new ArrayList<>();
		visited2.add(beginningPlace);

		for (Element neighbour : city.neighbors(beginningPlace)) {
			if (!visited2.contains(neighbour)) {
				arbo.add(new Road("", (Place)neighbour, (Place)beginningPlace));
				arbo.addAll(getAntiTree(city, neighbour));
			}
		}
		
		return arbo;
	}

	/* Define visiting order of arcs */
	private Queue<Arc> getOrder(IGraph graph, Element vertex, List<Arc> antiArbo) {
		Queue<Arc> order = new LinkedList<>();
		
		List<Arc> bigNumber = new ArrayList<>(),
				mediumNumber = new ArrayList<>(),
				lowNumber = new ArrayList<>();
		
		for (Arc a : graph.delta_out(vertex)) {
			if (antiArbo.contains(a)) {
				bigNumber.add(a);
				continue;
			} else if (antiArbo.contains(a.antiArc())) {
				mediumNumber.add(a);
				continue;
			} else if (!antiArbo.contains(a.antiArc()) || !antiArbo.contains(a)) {
				lowNumber.add(a);
				continue;
			}
		}
		
		Comparator<Arc> c = new Comparator<Arc>(){
		    @Override
		    public int compare(Arc o1, Arc o2){
		        return graph.delta_out(o1.dst()).size() - graph.delta_out(o2.dst()).size();
		    }
		};
		
		lowNumber.sort(c);
		mediumNumber.sort(c);
		bigNumber.sort(c);
		
		order.addAll(lowNumber);
		order.addAll(mediumNumber);
		order.addAll(bigNumber);
				
		return order;
		
	}
	
	/**
	 * Define visiting order for each vertex
	 * @param graph input graph
	 * @param root antiRoot
	 * @param antiTree antiTree
	 * @return
	 */
	public Map<Element, Queue<Arc>> numberize(IGraph graph, Element root, List<Arc> antiTree) {
		
		Map<Element, Queue<Arc>> res = new HashMap<>();
		
		for (Element vertex : graph.getVertices()) {
			res.put(vertex, getOrder(graph, vertex, antiTree));
		}
		
		return res;
	}
	
	private void traversal(IGraph graph, Element root,
			List<Arc> antiTree, Map<Element, Queue<Arc>> arcsOrder) {
		
		Element current = root;
		
		path.add(current);
		while (arcsOrder.get(current) != null && !arcsOrder.get(current).isEmpty()) {
			Arc a = arcsOrder.get(current).poll();

			/* Suppression de a.antiArc dans les arcs a parcourir */
			LinkedList<Arc> bbb = new LinkedList<>();
			for (Arc i : arcsOrder.get(a.dst())) {
				if (!i.equals(a.antiArc())) {
					bbb.add(i);
				}
			}
			arcsOrder.put(a.dst(), bbb);
			
			current = a.dst;
			path.add(current);

		}
	}

	public List<Element> algo(String place) {
		Place beginningPlace = city.findPlace(place);
		if (beginningPlace == null) {
			System.err.println("Place " + place + " does not exist");
		}
		return algo(beginningPlace);
	}
	
	public List<Element> algo(Element root) {
		List<Arc> antiArbo = getAntiTree(graph, root);
		Map<Element, Queue<Arc>> arcsOrder = numberize(graph, root, antiArbo);
		
		traversal(graph, root, antiArbo, arcsOrder);
		return path;
	}
	
}
