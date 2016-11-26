import java.util.Arrays;

public class Dijkstra {

	private boolean isOver(int[] res) {
		for (int i = 0; i < res.length; i++) {
			if (res[i] == Integer.MAX_VALUE) {
				return false;
			}
		}
		return true;
	}
	
	public int[] dijkstra(IGraph graph, int src) {
		int[] res = new int[graph.getVertices().size()];
		/*fonction qui a un vertex renvoi un indice*/
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.MAX_VALUE;
		}
		res[src] = 0;
		
		//while(isOver(res)){
			System.out.println(Arrays.toString(res));
			for (int i = 0; i < res.length; i++) {
				if (res[i] == Integer.MAX_VALUE) {
					continue;
				}
				for (Integer neighbour : graph.neighbours_out(i)) {
					if (res[neighbour] > res[i] + 1) {
						res[neighbour] = res[i] + 1;
					}
				}
			}
		//}
		
		return res;
	}
	
	
	
}
