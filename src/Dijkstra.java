import java.util.*;

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

	private IGraph graph;

	public Dijkstra(IGraph graph) {
		initCorresp(graph);
		this.graph = graph;
	}

	private boolean isOver(int[] res) {
		for (int i = 0; i < res.length; i++) {
			if (res[i] == Integer.MAX_VALUE) {
				return false;
			}
		}
		return true;
	}

	private int[][] dijkstra(Element src) {

		int[] res = new int[graph.getVertices().size()];
		int[] pred = new int[graph.getVertices().size()];

		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.MAX_VALUE;
			pred[i] = -1;
		}
		res[get(src)] = 0;
		pred[get(src)] = -1;

		while(!isOver(res)){
			for (int indV1 = 0; indV1 < res.length; indV1++) {
				Element v1 = get(indV1);
				if (res[indV1] == Integer.MAX_VALUE) {
					continue;
				}
				for (Element v2 : graph.neighbours_out(v1)) {
					int indV2 = get(v2);
					if (res[indV2] > res[indV1] + 1) {
						res[indV2] = res[indV1] + 1;
						pred[indV2] = indV1;
					}
				}
			}
		}
		int[][] a = new int[2][];
		a[0] = res;
		a[1] = pred;
		return a;
	}

	public List<Element> dijkstra(Element src, Element dst) {
		if (!graph.isVertex(src) || !graph.isVertex(dst)) {
			return null;
		}
		List<Element> predecessor = new ArrayList<>();

		int[] pred = dijkstra(src)[1];

		int vertAct = get(dst);
		for(int i = 0; i < pred.length; i++) {
			predecessor.add(get(vertAct));
			vertAct = pred[vertAct];
			if (vertAct == -1) {
				break;
			}
		}
		
		Collections.reverse(predecessor);
		return predecessor;
	}

}