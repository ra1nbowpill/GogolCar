import java.util.ArrayList;
import java.util.LinkedList;

public abstract class IGraph {
	
	ArrayList<String> verticesValue;
	
	public String toString() {
		String res = "";
		for(Integer vertex : getVertices()) {
			res += verticesValue.get(vertex) + " :";
			for(Arc arc : delta_out(vertex)) {
				res += " " + verticesValue.get(arc.dst);
			}
			res += "\n";
		}
		return res;
	}
	
	public abstract void addArc(int src, int dst);
	public void addEdge (int src, int dst) {
		addArc(src, dst);
		addArc(dst, src);
	}
	public abstract void addVertex(int vertex);
	
	public abstract void removeArc(int src, int dst);
	public void removeEdge(int src, int dst) {
		removeArc(src, dst);
		removeArc(dst, src);
	}
	public abstract void removeVertex(int src, int dst);
	
	public abstract LinkedList<Arc> delta_out(int vertex);
	public abstract LinkedList<Arc> delta_in(int vertex);
	public abstract LinkedList<Integer> neighbours_out(int vertex);
	public abstract LinkedList<Integer> neighbours_in(int vertex);
	
	public abstract ArrayList<Integer> getVertices();
	
}
