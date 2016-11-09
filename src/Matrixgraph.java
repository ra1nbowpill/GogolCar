import java.util.ArrayList;

public class Matrixgraph extends IGraph{
	
	private int V;
	private int E;
	private boolean[][] graphMatrix ;
	
	public Matrixgraph(int V) {
		// TODO Auto-generated constructor stub
		if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        this.graphMatrix = new boolean[V][V];
	}
	
	@Override
	public void addArc(Arc A) {
		// TODO Auto-generated method stub
		if (!graphMatrix[A.Src()][A.Dst()]) E++;
		graphMatrix[A.Src()][A.Dst()] = true;
		graphMatrix[A.Src()][A.Src()] = true;
	}	

	@Override
	public void addVertex(int vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeArc(Arc A) {
		// TODO Auto-generated method stub
		if (graphMatrix[A.Src()][A.Dst()]) E--;
		graphMatrix[A.Src()][A.Dst()] = false;
		graphMatrix[A.Dst()][A.Src()] = false;
	}

	@Override
	public void removeVertex(int vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isArc(Arc A) {
		// TODO Auto-generated method stub
		return graphMatrix[A.Src()][A.Dst()];
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
