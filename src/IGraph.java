import java.util.ArrayList;

public abstract class IGraph {
	
	int V=0;
	int A=0;
	ArrayList<Arc> arcs =new ArrayList<>();
	public String label(Element A, Element B){
		for (Arc arc : arcs) {
			if(arc.src().equals(B) && arc.dst().equals(B))
				return arc.label();
		}
		return "No label for this arc";
	}
	public void addArc(Arc A){
		arcs.add(A);
	}
	public void addEdge (Arc A) {
		addArc(A);
		addArc(new Arc(A.dst(), A.src(),A.label()));
	}
	public abstract void addVertex(Element vertex);
	public void removeArc(Arc arc){
		arcs.remove(arc);
	}
	public void removeEdge(Arc arc) {
		removeArc(arc);
		removeArc(new Arc(arc.dst(), arc.src(),arc.label()));
	}
	public abstract void removeVertex(Element vertex);
	public abstract boolean isArc(Arc A);
	public abstract boolean isVertex(Element vertex);
	public abstract ArrayList<Arc> delta_out(Element vertex);
	public abstract ArrayList<Arc> delta_in(Element vertex);
	public abstract ArrayList<Element> neighbours_out(Element vertex);
	public abstract ArrayList<Element> neighbours_in(Element vertex);
	public abstract ArrayList<Element> getVertices();
	public int V(){
		return V;
	}
	public int A(){
		return A;
	}
	public String toString() {
		String res = "delta_out\n";
		
		for (Element vertex : getVertices()) {
			res += "[" + vertex + "] : " + delta_out(vertex) + "\n";
		}
		
		res += "delta_in\n";
		for (Element vertex : getVertices()) {
			res += "[" + vertex + "] : " + delta_in(vertex) + "\n";
		}
		return res;
	}
}
