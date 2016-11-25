import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import org.junit.Test;

public class TestAdjacencyListGraph {

		IGraph graph;
		Integer[] listVertices = {9,10,11};
		Iterable<Integer> vertices = Arrays.asList(listVertices);
		
		int[][] listArcs = {{10,2},{10,3},{10,4},{2,5},{2,3},{2,10},{3,8},{3,4},{3,2},{4,10}};
		Collection<Arc> arcs = arrayToColl(listArcs);
		
		
		
		private Collection<Arc> arrayToColl(int[][] array) {
			Collection<Arc> res = new TreeSet<>();
			for (int[] elt : array) {
				res.add(new Arc(elt[0], elt[1]));
			}
			return res;
		}
		
		@Test
		public void testRemoveVertex() {
			graph = new AdjacencyListGraph(arcs, vertices);
			int vertex = 10;

			graph.removeVertex(10);
			
			assert(graph.delta_in(vertex).isEmpty() && graph.delta_out(vertex).isEmpty());
			assert(!graph.isVertex(vertex));
			for (Integer i : graph.getVertices()) {
				assert(!graph.isArc(new Arc(i, vertex)) && !graph.isArc(new Arc(vertex, i)));
			}
		}
		
		public void testAddEdge() {
			graph = new AdjacencyListGraph(arcs, vertices);
			graph.addEdge(new Arc(10, 8));
			assert(graph.isArc(new Arc(10, 8)) && graph.isArc(new Arc(8, 10)));
		}
		
		@Test
		public void testRemoveEdge() {
			graph = new AdjacencyListGraph(arcs, vertices);
			graph.removeEdge(new Arc(1, 2));
			assert(!graph.isArc(new Arc(1, 2)) && !graph.isArc(new Arc(2, 1)));
		}
		
		public void testAddArc() {
			graph = new AdjacencyListGraph(arcs, vertices);
			graph.addArc(new Arc(10, 8));
			assert(graph.isArc(new Arc(10, 8)));
		}
		
		@Test
		public void testRemoveArc() {
			graph = new AdjacencyListGraph(arcs, vertices);
			graph.removeArc(new Arc(1, 2));
			assert(!graph.isArc(new Arc(1, 2)));
		}
		
		@Test
		public void dijkstraTest() {
			graph = new AdjacencyListGraph(arcs);
			Dijkstra.dijkstra(graph, 1, 10);
			
		}
		
	}