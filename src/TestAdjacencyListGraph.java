import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TestAdjacencyListGraph {

		IGraph graph;
		Integer[] listVertices = {9,10,11};
		Iterable<Integer> vertices = Arrays.asList(listVertices);
		int[][] listArcs = {{1,2},{1,3},{1,4},{2,5},{2,3},{2,1},{3,8},{3,4},{3,2},{4,1}};
		Iterable<Arc> arcs = (ArrayList<Arc>) Stream.of(listArcs).map(elt -> new Arc(elt[0], elt[1])).collect(Collectors.toList());
		
		@Test
		public void test_removeVertex() {
			graph = new AdjacencyListGraph(arcs, vertices);

			graph.removeVertex(10);
		}
		
		@Test
		public void myTest() {
			System.out.println("MyTest");
			graph = new AdjacencyListGraph(arcs, vertices);
			System.out.println(graph);
			graph.addEdge(new Arc(10, 8));
			System.out.println(graph);
			
			graph.removeVertex(10);
			System.out.println(graph);
			graph.removeEdge(new Arc(1, 2));
			System.out.println(graph);
		}
		
		@Test
		public void disjkstraTest() {
			System.out.println("diskjaTest");
			graph = new AdjacencyListGraph(arcs);
			Dijkstra a = new Dijkstra();
			System.out.println("disktra\n" + Arrays.toString(a.dijkstra(graph, 2)));
			
		}
		
	}