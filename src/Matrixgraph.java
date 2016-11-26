import java.util.ArrayList;

public class Matrixgraph extends IGraph{
	
	private boolean[][] graphMatrix ;
	
	public Matrixgraph(int V) {
		// TODO Auto-generated constructor stub
		if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.A = 0;
        this.graphMatrix = new boolean[V][V];
	}
	
	@Override
	public void addArc(Arc arc) {
		// TODO Auto-generated method stub
		if (!graphMatrix[arc.src()][arc.dst()]) A++;
		graphMatrix[arc.src()][arc.dst()] = true;
		graphMatrix[arc.dst()][arc.src()] = true;
	}	

	@Override
	public void addVertex(int vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeArc(Arc arc) {
		// TODO Auto-generated method stub
		if (graphMatrix[arc.src()][arc.dst()]) A--;
		graphMatrix[arc.src()][arc.dst()] = false;
		graphMatrix[arc.dst()][arc.src()] = false;
	}

	@Override
	public void removeVertex(int vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isArc(Arc A) {
		// TODO Auto-generated method stub
		return graphMatrix[A.src()][A.dst()];
	}

	@Override
	public boolean isVertex(int vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Arc> delta_out(int vertex) {
		return null;
	}

	@Override
	public ArrayList<Arc> delta_in(int vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> neighbours_out(int vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> neighbours_in(int vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
