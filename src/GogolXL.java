import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GogolXL {
	/*
	Dans le cas général, il y a toujours un nombre pair de sommets de degré impair.
	La solution optimale peut être obtenue par l'algorithme suivant :
	
	- Former un nouveau graphe G’, constitué uniquement des sommets de degré impair
	dans le graphe initial G.
	
	- Entre deux sommets de G’, ajouter une arête dont la longueur est celle du plus
	court chemin entre les deux sommets dans G.
	
	- Trouver un couplage parfait de poids minimum dans G’, ce qu'on peut calculer
	avec un algorithme de complexité O(n3)
	
	- Pour chaque paire de sommets u et v couplés dans G’, ajouter au graphe initial
	G les arêtes du plus court chemin de u à v.

	 */

	private Set<Element> oddVertices(IGraph graph) {
		Set<Element> oddVertices = new TreeSet<>();
		for (Element vertex : graph.getVertices()) {
			if (graph.neighbours_out(vertex).size() % 2 == 1) {
				oddVertices.add(vertex);
			}
		}
		return oddVertices;
	}

	/**
	 * Computes G' the complete graph containing the odd degree vertices of G
	 * the weigh is the shortest path from x to y in G
	 * @param graph the base graph
	 * @return a graph
	 */
	public IGraph constructOddGraph(IGraph graph) {
		
		IGraph oddGraph = new AdjacencyListGraph(Collections.emptySet());
		Set<Element> oddVertices = oddVertices(graph);
		
		for (Element oddVertex1 : oddVertices) {
			for (Element oddVertex2 : oddVertices) {
				if (oddVertex1 == oddVertex2) {
					continue;
				}
				// TODO this must save the given arc
				/*oddGraph.addArc(new WeighedArc(oddVertex1, oddVertex2, "",
						Dijkstra.dijkstra(graph, oddVertex1, oddVertex2).size()));*/
			}
		}
		
		return oddGraph;
	}
	
	public Set<Arc> perfectMatch(IGraph graph) {
		Set<Arc> match = new TreeSet<>();
		List<WeighedArc> arcs = new ArrayList<>();
		Set<Element> inMatch = new TreeSet<>();
		
		/* Getting all the arcs from the graph */
		for (Element vertex : graph.getVertices()) {
			// TODO	public List<Arc> IGraph::arcs_out(Integer vertex);
			//arcs.addAll(graph.arcs_out(vertex));
		}
		
		/* Sorting the arcs based on their weight */

		arcs.sort((o1, o2) -> o1.weight() - o2.weight());

		/* Finding perfectMatch with euristic */
		for (WeighedArc arc : arcs) {
			if (inMatch.contains(arc.src()) || inMatch.contains(arc.dst())) {
				continue;
			}
			match.add(arc);
			inMatch.add(arc.src());
			inMatch.add(arc.dst());
		}
		
		if (!inMatch.containsAll(graph.getVertices())) {
			System.err.println("The euristic is not good");
			assert(false);
		}
		
		return match;
	}
	
	private List<Arc> arcsOfPath(List<Element> path) {
		List<Arc> arcs = new ArrayList<>();
		for (int i = 1; i < path.size(); i++) {
			arcs.add(new Arc(path.get(i-1), path.get(i), ""));
		}
		return arcs;
	}
	
	public IGraph makeGraphEulerian(IGraph graph, Set<Arc> match) {
		
		IGraph eulerGraph = new AdjacencyListGraph(graph);
		Dijkstra dijkstra = new Dijkstra(graph);

		for (Arc matchArc : match) {
			List<Arc> path = arcsOfPath(
						dijkstra.dijkstra(matchArc.src(), matchArc.dst()) );
			for (Arc arc : path) {
				// TODO This must allow multiArcs
				eulerGraph.addArc(arc);
			}
			
		}
		
		return null;
	}
	
	public List<Element> algo(IGraph graph, Element root) {
		
		IGraph oddGraph = constructOddGraph(graph);
		
		Set<Arc> match = perfectMatch(oddGraph);
		
		IGraph eulerianGraph = makeGraphEulerian(oddGraph, match);
		
		GogolL a = new GogolL();
		return a.algo(eulerianGraph, root);
		
	}
	
}
