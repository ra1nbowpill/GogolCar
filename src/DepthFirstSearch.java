import java.util.Stack;

public class depthFirstSearch {
	 private boolean[] marked; 
	 private int count;   
	 public depthFirstSearch(IGraph G, Integer s, Stack<Integer> S) {
	        marked = new boolean[G.V()];
	        S.push(s);
	        dfs(G, s, S);
	    }
	 private void dfs(IGraph G, Integer v, Stack<Integer> S) {
	        count++;
	        marked[v] = true;
	        for (Integer w : G.neighbours_out(v)) {
	            if (!marked[w]) {
	            	S.push(w);
	                dfs(G, w, S);
	            }
	        }
	    }
	 public boolean marked(int v) {
	        return marked[v];
	    }
	 public int count() {
	        return count;
	    }
}