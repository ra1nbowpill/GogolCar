import java.util.*;
import java.util.stream.*;

public class AdjacencyListGraph extends IGraph{
	
	ArrayList<LinkedList<Integer>> adjacencyList = new ArrayList<>();
	ArrayList<Integer> corresp = new ArrayList<>();
	
	public AdjacencyListGraph(Iterable<Arc> arcs) {
		for (Arc arc : arcs) {
			addArc(arc);
		}
	}
	
	public  AdjacencyListGraph(Iterable<Arc> arcs, Iterable<Integer> vertices) {
		this(arcs);
		for (int vertex : vertices) {
			addVertex(vertex);
		}
	}
	
	@Override
	public void addArc(Arc arc) {
		int srcIndex = corresp.indexOf(arc.src()),
				dstIndex = corresp.indexOf(arc.dst());
		
		if (srcIndex == -1) {
			addVertex(arc.src());
			srcIndex = corresp.indexOf(arc.src());
		}
		if (dstIndex == -1) {
			addVertex(arc.dst());
			dstIndex = corresp.indexOf(arc.dst());
		}
		
		LinkedList<Integer> srcList = adjacencyList.get(srcIndex);
		if (srcList.contains(arc.dst())) {return ;}
		srcList.add(arc.dst());
	}

	@Override
	public void addVertex(int vertex) {
		if (corresp.contains(vertex)) {return ;}
		
		adjacencyList.add(new LinkedList<>());
		corresp.add(vertex);
	}

	@Override
	public void removeArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());		
		if (srcIndex == -1) {
			return ;
		}
		adjacencyList.get(srcIndex).remove(A.dst());
	}

	@Override
	public void removeVertex(int vertex) {
		int srcIndex = corresp.indexOf(vertex);		
		if (srcIndex == -1) {
			return ;
		}
		//Removing every arc that goes to vertex
		for (Arc arc : delta_in(vertex)) {
			removeArc(arc);
		}
		adjacencyList.remove(srcIndex);
		corresp.remove(srcIndex);
	}

	@Override
	public ArrayList<Arc> delta_out(int vertex) {
		return (ArrayList<Arc>) neighbours_out(vertex)
				.stream().map(dst -> new Arc(vertex, dst))
				.collect(Collectors.toList());
	}

	@Override
	public ArrayList<Arc> delta_in(int vertex) {
		return (ArrayList<Arc>) neighbours_in(vertex).stream()
				.map(src -> new Arc(src, vertex))
				.collect(Collectors.toList());
	}

	@Override
	public ArrayList<Integer> neighbours_out(int vertex) {
		int vertexIndex = corresp.indexOf(vertex);
		if (vertexIndex == -1) {return new ArrayList<>();}
		
		return new ArrayList<>(adjacencyList.get(vertexIndex));
	}

	@Override
	public ArrayList<Integer> neighbours_in(int vertex) {
		ArrayList<Integer> neighbours = new ArrayList<>();
		for (LinkedList<Integer> adjacency : adjacencyList) {
			if (adjacency.contains(vertex)) {
				neighbours.add(corresp.get(adjacencyList.indexOf(adjacency)));
			}
		}
		return neighbours;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		return corresp;
	}

	@Override
	public boolean isArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());
		if (srcIndex == -1) {return false;}
		return adjacencyList.get(srcIndex).contains(A.dst());
	}

	@Override
	public boolean isVertex(int vertex) {
		return corresp.contains(vertex);
	}

}
