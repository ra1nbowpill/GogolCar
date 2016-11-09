import java.util.ArrayList;

public abstract class IGraph {
	
	int V=0;
	int A=0;

	public abstract void addArc(Arc A);
	public void addEdge (Arc A) {
		addArc(A);
		addArc(new Arc(A.dst(), A.src()));
	}
	public abstract void addVertex(int vertex);

	public abstract void removeArc(Arc arc);
	public void removeEdge(Arc arc) {
		removeArc(arc);
		removeArc(new Arc(arc.dst(), arc.src()));
	}
	
	public abstract void removeVertex(int vertex);
	public abstract boolean isArc(Arc A);
	public abstract boolean isVertex(int vertex);
	
	public abstract ArrayList<Arc> delta_out(int vertex);
	public abstract ArrayList<Arc> delta_in(int vertex);
	public abstract ArrayList<Integer> neighbours_out(int vertex);
	public abstract ArrayList<Integer> neighbours_in(int vertex);
	public abstract ArrayList<Integer> getVertices();
	public int V(){
		return V;
	}
	public int A(){
		return A;
	}
	
	public String toString() {
		String res = "delta_out\n";
		
		for (int vertex : getVertices()) {
			res += "[" + vertex + "] : " + delta_out(vertex) + "\n";
		}
		
		res += "delta_in\n";
		for (int vertex : getVertices()) {
			res += "[" + vertex + "] : " + delta_in(vertex) + "\n";
		}
		return res;
	}
	
}
