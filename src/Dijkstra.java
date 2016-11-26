import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {

	private Map<Integer, Element> idToElt = new HashMap<>();
	private Map<Element, Integer> eltToId = new HashMap<>();

	private void initCorresp(IGraph graph) {
		Integer id = 0;
		for (Element elt : graph.getVertices()) {
			idToElt.put(id, elt);
			eltToId.put(elt, id);
			id++;
		}
	}

	private Integer get(Element elt) {return eltToId.get(elt);}
	private Element get(Integer id) {return idToElt.get(id);}

	private boolean isOver(int[] res) {
		for (int i = 0; i < res.length; i++) {
			if (res[i] == Integer.MAX_VALUE) {
				return false;
			}
		}
		return true;
	}
	
	public int[] dijkstra(IGraph graph, int src) {

		initCorresp(graph);

		int[] res = new int[graph.getVertices().size()];
		/* fonction qui a un vertex renvoie un indice */
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.MAX_VALUE;
		}
		res[src] = 0;
		
		while(isOver(res)) {
			System.out.println(Arrays.toString(res));
			for (int i = 0; i < res.length; i++) {
				if (res[i] == Integer.MAX_VALUE) {
					continue;
				}
				for (Element neighbour : graph.neighbours_out(get(i))) {
					if (res[get(neighbour)] > res[get(neighbour)] + 1) {
						res[get(neighbour)] = res[get(neighbour)] + 1;
					}
				}
			}
		}
		
		return res;
	}
	
	
	
}
