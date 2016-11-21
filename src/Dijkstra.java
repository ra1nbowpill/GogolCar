import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

	private static boolean isOver(int[] res) {
		for (int i = 0; i < res.length; i++) {
			if (res[i] == Integer.MAX_VALUE) {
				return false;
			}
		}
		return true;
	}
	
	private static List<Integer> getCorresp(IGraph graph) {	
		List<Integer> corresp = new ArrayList<>();
		for (int vertex : graph.getVertices()) {
			corresp.add(vertex);
		}
		return corresp;
	}
	
	private static int[][] dijkstra(IGraph graph, int src, List<Integer> corresp) {
		
		int[] res = new int[graph.getVertices().size()];
		int[] pred = new int[graph.getVertices().size()];

		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.MAX_VALUE;
			pred[i] = -1;
		}
		res[corresp.indexOf(src)] = 0;
		pred[corresp.indexOf(src)] = -1;
		
		while(!isOver(res)){
			for (int indV1 = 0; indV1 < res.length; indV1++) {
				int v1 = corresp.get(indV1);
				if (res[indV1] == Integer.MAX_VALUE) {
					continue;
				}
				for (Integer v2 : graph.neighbours_out(v1)) {
					int indV2 = corresp.indexOf(v2);
					if (res[indV2] > res[indV1] + 1) {
						res[indV2] = res[indV1] + 1;
						pred[indV2] = v1;
					}
				}
			}
		}
		int[][] a = new int[2][];
		a[0] = res;
		a[1] = pred;
		return a;
	}
	
	public static List<Integer> dijkstra(IGraph graph, int src, int dst) {
		if (!graph.isVertex(src) || !graph.isVertex(dst)) {
			return null;
		}
		List<Integer> corresp = getCorresp(graph);
		List<Integer> predecessor = new ArrayList<>();

		int[] pred = dijkstra(graph, src, corresp)[1];
		
		int vertAct = dst;
		for(int i = 0; i < pred.length; i++) {
			predecessor.add(vertAct);
			vertAct = pred[corresp.indexOf(vertAct)];
			if (vertAct == -1) {
				break;
			}
		}
		Collections.reverse(predecessor);
		return predecessor;
	}
	
	
	
}
