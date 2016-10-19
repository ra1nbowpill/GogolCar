import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph{
	ArrayList<LinkedList<Integer>> adjacency =new ArrayList<>();
	HashMap<Integer, LinkedList<Integer>> vertices = new HashMap<>();
	public void add(int vertex){
		LinkedList<Integer> var=new LinkedList<Integer>();
		adjacency.add(var);
		vertices.put(vertex, var);
	}
	public void add(int src,int dest){
		if(!vertices.containsKey(src))
			this.add(src);
		if(!vertices.containsKey(dest))
			this.add(dest);
		vertices.get(src).add(dest);
		vertices.get(dest).add(src);
	}
	public LinkedList<Integer> naiveGogolS(Integer firstVertex){
		HashMap<Integer, Integer> visited =new HashMap<>();
		for (Integer  vertex : vertices.keySet()) {
			visited.put(vertex, 0);
		}
		return depthFirst(firstVertex, visited);
	}
	
	private LinkedList<Integer> depthFirst(Integer firstVertex, HashMap<Integer, Integer> visited){
		LinkedList<Integer> path= new LinkedList<>();

		visited.put(firstVertex, 1);
		for (Integer vertex : vertices.get(firstVertex)) {
			if(visited.get(vertex)!=1)
				path.addAll(depthFirst(vertex, visited));
		}
		System.out.println(firstVertex);
		path.add(firstVertex);
		return path;
	}
	
	public static void main(String args[]){
		Graph G=new Graph();
		G.add(1, 2);
		G.add(1, 5);
		G.add(2, 3);
		G.add(2, 4);
		G.add(3, 5);
		System.out.println(G.naiveGogolS(1));
	}
}
