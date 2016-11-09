import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AdjacencyListGraph extends IGraph{
	
	ArrayList<LinkedList<Integer>> adjacencyList = new ArrayList<>();
	ArrayList<Integer> corresp = new ArrayList<>();
	
	
	@Override
	public void addArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src()),
				dstIndex = corresp.indexOf(A.dst());
		
		if (srcIndex == -1) {
			addVertex(A.src());
			srcIndex = corresp.indexOf(A.src());
		}
		if (dstIndex == -1) {
			addVertex(A.dst());
			dstIndex = corresp.indexOf(A.dst());
		}
		
		LinkedList<Integer> srcList;
		srcList= adjacencyList.get(srcIndex);
		if (srcList.contains(A.dst())) {return ;}
		srcList.add(A.dst());
		this.A++;

	}

	@Override
	public void addVertex(int vertex) {
		if (corresp.contains(vertex)) {return ;}
		
		adjacencyList.add(new LinkedList<>());
		corresp.add(vertex);
		this.V++;
	}

	@Override
	public void removeArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());		
		if (srcIndex == -1) {
			return ;
		}
		adjacencyList.get(srcIndex).remove(A.dst());
		this.A--;
	}

	@Override
	public void removeVertex(int vertex) {
		int srcIndex = corresp.indexOf(vertex);		
		if (srcIndex == -1) {
			return ;
		}
		adjacencyList.remove(srcIndex);
		this.V--;
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
	public boolean isArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());
		if (srcIndex == -1) {return false;}
		return adjacencyList.get(srcIndex).contains(A.dst());
	}

	@Override
	public boolean isVertex(int vertex) {
		return corresp.contains(vertex);
	}


	@Override
	public int V() {
		return V;
	}

	@Override
	public int A() {
		return A;
	}
	
}
