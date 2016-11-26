import java.util.*;
import java.util.stream.*;

public class AdjacencyListGraph extends IGraph{
	ArrayList<LinkedList<Element>> arcsTable = new ArrayList<>();
	ArrayList<Element> corresp = new ArrayList<>();
	
	public AdjacencyListGraph(Iterable<Arc> arcs) {
		for (Arc arc : arcs) {
			addArc(arc);
			super.addArc(arc);
		}
	}
	public  AdjacencyListGraph(Iterable<Arc> arcs, Iterable<Element> vertices) {
		this(arcs);
		for (Element vertex : vertices) {
			addVertex(vertex);
		}
	}
	public AdjacencyListGraph() {
		// TODO Auto-generated constructor stub
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
		LinkedList<Element> srcList = arcsTable.get(srcIndex);
		if (srcList.contains(arc.dst())) {return ;}
		srcList.add(arc.dst());
		super.addArc(arc);
	}
	@Override
	public void addVertex(Element vertex) {
		if (corresp.contains(vertex)) {return ;}
		
		arcsTable.add(new LinkedList<>());
		corresp.add(vertex);
		this.V++;
	}
	@Override
	public void removeArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());		
		if (srcIndex == -1) {
			return ;
		}
		arcsTable.get(srcIndex).remove(A.dst());
		super.removeArc(A);
	}
	@Override
	public void removeVertex(Element vertex) {
		int srcIndex = corresp.indexOf(vertex);		
		if (srcIndex == -1) {
			return ;
		}
		//Removing every arc that goes to vertex
		for (Arc arc : delta_in(vertex)) {
			removeArc(arc);
		}
		arcsTable.remove(srcIndex);
		corresp.remove(srcIndex);
	}
	@Override
	public ArrayList<Arc> delta_out(Element vertex) {
		return (ArrayList<Arc>) neighbours_out(vertex)
				.stream().map(dst -> new Arc(vertex, dst,this.label(vertex, dst)))
				.collect(Collectors.toList());
	}
	@Override
	public ArrayList<Arc> delta_in(Element vertex) {
		return (ArrayList<Arc>) neighbours_in(vertex).stream()
				.map(src -> new Arc(src, vertex,this.label(src, vertex)))
				.collect(Collectors.toList());
	}
	@Override
	public ArrayList<Element> neighbours_out(Element vertex) {
		int vertexIndex = corresp.indexOf(vertex);
		if (vertexIndex == -1) {return new ArrayList<>();}
		
		return new ArrayList<>(arcsTable.get(vertexIndex));
	}
	@Override
	public ArrayList<Element> neighbours_in(Element vertex) {
		ArrayList<Element> neighbours = new ArrayList<>();
		for (LinkedList<Element> adjacency : arcsTable) {
			if (adjacency.contains(vertex)) {
				neighbours.add(corresp.get(arcsTable.indexOf(adjacency)));
			}
		}
		return neighbours;
	}
	@Override
	public ArrayList<Element> getVertices() {
		return corresp;
	}
	@Override
	public boolean isArc(Arc A) {
		int srcIndex = corresp.indexOf(A.src());
		if (srcIndex == -1) {return false;}
		return arcsTable.get(srcIndex).contains(A.dst());
	}
	@Override
	public boolean isVertex(Element vertex) {
		return corresp.contains(vertex);
	}
}
