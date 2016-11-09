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
	public void addArc(int src, int dst) {
		int srcIndex = corresp.indexOf(src),
				dstIndex = corresp.indexOf(dst);
		
		if (srcIndex == -1) {
			addVertex(src);
			srcIndex = corresp.indexOf(src);
		}
		if (dstIndex == -1) {
			addVertex(dst);
			dstIndex = corresp.indexOf(dst);
		}
		
		LinkedList<Integer> srcList = adjacencyList.get(srcIndex);
		if (srcList.contains(dst)) {return ;}
		srcList.add(dst);
	}

	@Override
	public void addVertex(int vertex) {
		if (corresp.contains(vertex)) {return ;}
		
		adjacencyList.add(new LinkedList<>());
		corresp.add(vertex);
	}

	@Override
	public void removeArc(int src, int dst) {
		int srcIndex = corresp.indexOf(src);
		if (srcIndex == -1) {
			return ;
		}
		adjacencyList.get(srcIndex).remove((Object) dst);
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
	public boolean isArc(int src, int dst) {
		int srcIndex = corresp.indexOf(src);
		if (srcIndex == -1) {return false;}
		return adjacencyList.get(srcIndex).contains(dst);
	}

	@Override
	public boolean isVertex(int vertex) {
		return corresp.contains(vertex);
	}
	
}
