import java.util.*;

public class GogolL implements Algo {

	private Ville city;
	private IGraph graph;

	public boolean DEBUG = false;

	private List<Road> path;
	private Set<Element> visited;
	/**
	 * Compute antiTree from graph
	 * @param root the antiRoot
	 * @return set of arcs representing antiTree
	 */
	private Set<Arc> getAntiTree(Element root) {
		if (!graph.isVertex(root)) {return null;}
		Set<Arc> arbo = new TreeSet<>();
		visited.add(root);
		for (Arc neighbour : graph.delta_out(root)) {
			if (!visited.contains(neighbour.dst())) {
				arbo.add(neighbour.antiArc());
				arbo.addAll(getAntiTree(neighbour.dst()));
			}
		}
		return arbo;
	}

	public Set<Arc> publicAntiTree(Element root) {
		visited = new TreeSet<>();
		return getAntiTree(root);
	}

	/* Define visiting order of arcs */
	private Queue<Arc> getOrder(Element vertex, Set<Arc> antiArbo) {
		Queue<Arc> order = new LinkedList<>();

		List<Arc> bigNumber = new ArrayList<>(),
				mediumNumber = new ArrayList<>(),
				lowNumber = new ArrayList<>();

		for (Arc neighbour : graph.delta_out(vertex)) {
			boolean added = false;
			for (Arc antiArc : antiArbo) {
				if (added) break;
				if (antiArc.equals(neighbour)) {
					bigNumber.add(neighbour);
					added = true;
				}
			}
			for (Arc antiArc : antiArbo) {
				if (added) break;
				if (antiArc.equals(neighbour.antiArc())) {
					mediumNumber.add(neighbour);
					added = true;
				}
			}
			for (Arc antiArc : antiArbo) {
				if (added) break;
				if (!antiArc.equals(neighbour.antiArc()) || !antiArc.equals(neighbour)) {
					lowNumber.add(neighbour);
					added = true;
				}
			}
		}
		order.addAll(lowNumber);
		order.addAll(mediumNumber);
		order.addAll(bigNumber);



		return order;

	}

	/**
	 * Define visiting order for each vertex
	 * @param antiTree antiTree
	 * @return
	 */
	public Map<Element, Queue<Arc>> numberize(Set<Arc> antiTree) {

		Map<Element, Queue<Arc>> res = new HashMap<>();

		for (Element vertex : graph.getVertices()) {
			res.put(vertex, getOrder(vertex, antiTree));
		}

		return res;
	}

	private void traversal(Element root, Map<Element, Queue<Arc>> arcsOrder) {

		Element current = root;

		while (arcsOrder.get(current) != null && !arcsOrder.get(current).isEmpty()) {
			Arc a = arcsOrder.get(current).poll();

			/* Suppression de a.antiArc dans les arcs a parcourir */
			Queue<Arc> bbb = new LinkedList<>();
			boolean didRemove = false;
			for (Arc i : arcsOrder.get(a.dst())) {
				if (i.src() == a.dst() && i.dst() == a.src()) {
					if (didRemove) {
						bbb.add(i);
					}
					didRemove = true;
				} else {
					bbb.add(i);

				}
			}
			arcsOrder.put(a.dst(), bbb);

			current = a.dst();
			path.add(city.toRoad(a));
		}
	}

	private Set<Element> oddVertices() {
		Set<Element> oddVertices = new TreeSet<>();
		for (Element vertex : graph.getVertices()) {
			if (graph.neighbours_out(vertex).size() % 2 == 1) {
				oddVertices.add(vertex);
			}
		}
		return oddVertices;
	}

	@Override
	public void setCity(Ville city) {
		this.city = city;
		this.graph = city.graphe;
	}

	@Override
	public List<Road> algo(Element root) {

		if (oddVertices().size() != 0) {
			System.out.println("This city is not eulerian");
			System.out.println("Could not execute GogolL");
			return Collections.emptyList();
		}

		path = new ArrayList<>();
		visited = new TreeSet<>();
		Set<Arc> antiArbo = getAntiTree(root);
		Map<Element, Queue<Arc>> arcsOrder = numberize(antiArbo);

		traversal(root, arcsOrder);
		return path;
	}

	@Override
	public List<Road> algo(String place) {
		Place beginningPlace = city.findPlace(place);
		if (beginningPlace == null) {
			System.err.println("Place " + place + " does not exist");
			return Collections.emptyList();
		}
		return algo(beginningPlace);
	}
}