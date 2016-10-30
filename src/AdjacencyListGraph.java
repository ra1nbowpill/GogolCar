import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AdjacencyListGraph extends IGraph{
	
	ArrayList<LinkedList<Integer>> adjacencyList = new ArrayList<>();
	ArrayList<Integer> corresp = new ArrayList<>();
	
	
	@Override
	public void addArc(int src, int dst) {
		int srcIndex = corresp.indexOf(src),
				dstIndex = corresp.indexOf(dst);
		
		if (srcIndex == -1) {
			addVertex(src);
		}
		if (dstIndex == -1) {
			addVertex(dst);
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
		adjacencyList.get(srcIndex).remove(dst);
	}

	@Override
	public void removeVertex(int vertex) {
		int srcIndex = corresp.indexOf(vertex);		
		if (srcIndex == -1) {
			return ;
		}
		adjacencyList.remove(srcIndex);
	}

	@Override
	public ArrayList<Arc> delta_out(int vertex) {
		// TODO Auto-generated method stub
		int vertexIndex = corresp.indexOf(vertex);
		if (vertexIndex == -1) {return new ArrayList<>();}
		return (ArrayList<Arc>) adjacencyList.get(vertexIndex)
				.stream().map(dst -> new Arc(vertex, dst))
				.collect(Collectors.toList());
	}

	@Override
	public ArrayList<Arc> delta_in(int vertex) {
		ArrayList<Integer> neighbours = new ArrayList<>();
		for (LinkedList<Integer> adjacency : adjacencyList) {
			if (adjacency.contains(vertex)) {
				neighbours.add(adjacencyList.indexOf(adjacency));
			}
		}
		return (ArrayList<Arc>) neighbours.stream()
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
				neighbours.add(adjacencyList.indexOf(adjacency));
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
