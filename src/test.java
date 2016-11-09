import java.util.Stack;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IGraph G=new AdjacencyListGraph();
		G.addArc(new Arc(1, 2));
		G.addArc(new Arc(1, 3));
		G.addArc(new Arc(2, 5));
		G.addArc(new Arc(2, 4));
		G.addArc(new Arc(3, 5));
		G.addArc(new Arc(3,4));
		G.addArc(new Arc(2, 1));
		G.addArc(new Arc(3, 1));
		G.addArc(new Arc(5, 2));
		G.addArc(new Arc(4, 2));
		G.addArc(new Arc(5, 3));
		G.addArc(new Arc(4, 3));
		Stack<Integer> S=new Stack<>();
		DepthFirstSearch D=new DepthFirstSearch(G, 2, S);
		System.out.println(S);
	}

}
