import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyListGraphMulti extends IGraph{
	Map<Element, LinkedList<Arc>> adj = new HashMap<>();

	public AdjacencyListGraphMulti(Iterable<Arc> arcs) {
		for (Arc arc : arcs) {
			addArc(arc);
			super.addArc(arc);
		}
	}

	public AdjacencyListGraphMulti(Iterable<Arc> arcs, Iterable<Element> vertices) {
		this(arcs);
		for (Element vertex : vertices) {
			addVertex(vertex);
		}
	}

	public AdjacencyListGraphMulti(IGraph graph) {
		for (Element vertex : graph.getVertices()) {
			for (Arc arc : graph.delta_out(vertex)) {
				this.addArc(arc);
			}
		}
	}

	public AdjacencyListGraphMulti() {}

	@Override
	public void addArc(Arc arc) {

		if (!isVertex(arc.src())) {
			addVertex(arc.src());
		}
		if (!isVertex(arc.dst())) {
			addVertex(arc.dst());
		}

		adj.get(arc.src()).add(arc);
		super.addArc(arc);
	}
	@Override
	public void addVertex(Element vertex) {
		if (isVertex(vertex)) {
			return;
		}

		adj.put(vertex, new LinkedList<>());
		this.V++;
	}
	@Override
	public void removeArc(Arc arc) {
		Iterator<Arc> it = adj.get(arc.src()).iterator();
		int index = -1;
		while(it.hasNext()) {
			Arc a = it.next();
			index++;
			if (a == arc) {
				adj.get(arc.src()).remove(index);
			}
		}
		super.removeArc(arc);
	}
	@Override
	public void removeVertex(Element vertex) {

		//Removing every arc that goes to vertex
		for (Arc arc : delta_in(vertex)) {
			removeArc(arc);
		}
		adj.remove(vertex);
	}
	@Override
	public ArrayList<Arc> delta_out(Element vertex) {
		ArrayList<Arc> neighbours = new ArrayList<>();
		neighbours.addAll(adj.get(vertex));
		return neighbours;
	}
	@Override
	public ArrayList<Arc> delta_in(Element vertex) {

		ArrayList<Arc> neighbours = new ArrayList<>();
		for (Element v : getVertices()) {
			for (Arc arc : delta_out(v)) {
				if (arc.dst() == vertex) {
					neighbours.add(arc);
				}
			}
		}
		return neighbours;
	}
	@Override
	public ArrayList<Element> neighbours_out(Element vertex) {
		return (ArrayList<Element>) delta_out(vertex).stream()
				.map(arc -> arc.dst())
				.collect(Collectors.toList());
	}
	@Override
	public ArrayList<Element> neighbours_in(Element vertex) {
		return (ArrayList<Element>) delta_in(vertex).stream()
				.map(arc -> arc.dst())
				.collect(Collectors.toList());
	}
	@Override
	public ArrayList<Element> getVertices() {
		return new ArrayList<>(adj.keySet());
	}
	@Override
	public boolean isArc(Arc arc) {
		return adj.get(arc.src()).contains(arc);
	}
	@Override
	public boolean isVertex(Element vertex) {
		return adj.containsKey(vertex);
	}
}
