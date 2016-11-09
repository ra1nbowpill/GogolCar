import java.util.ArrayList;
import java.util.LinkedList;

public abstract class IGraph {
	int A=0;
	int V=0;
	public abstract void addArc(Arc A);
	public void addEdge (int src, int dst) {
		addArc(new Arc(src,dst));
		addArc(new Arc(src,dst));
	}
	public abstract void addVertex(int vertex);
	public abstract int V();
	public abstract int A();
	public abstract void removeArc(Arc A);
	/**public void removeEdge(Arc A) {
		removeArc(A.src(), dst);
		removeArc(dst, src);
	}**/
	public abstract void removeVertex(int vertex);
	public abstract boolean isArc(Arc A);
	public abstract boolean isVertex(int vertex);
	
	public abstract ArrayList<Arc> delta_out(int vertex);
	public abstract ArrayList<Arc> delta_in(int vertex);
	public abstract ArrayList<Integer> neighbours_out(int vertex);
	public abstract ArrayList<Integer> neighbours_in(int vertex);
	
	public abstract ArrayList<Integer> getVertices();
	
	public String toString() {
		String res = "";
		
		for (int vertex : getVertices()) {
			res += "[" + vertex + "] : " + delta_out(vertex) + "\n";
		}
		
		return res;
	}
	
}
