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
	public GogolXL(Ville ville){
		
	}
	private Set<Element> oddVertices(Ville ville) {
		Set<Element> oddVertices = new TreeSet<>();
		for (Element place : ville.getPlaces()) {
			if (ville.neighbors_out(place).size() % 2 == 1) {
				oddVertices.add(place);
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
	public Ville constructOddGraph(Ville city) {
		
		Ville oddGraph = new Ville(city.name+"_bis");
		Set<Element> oddVertices = oddVertices(city);
		
		for (Element oddVertex1 : oddVertices) {
			for (Element oddVertex2 : oddVertices) {
				if (oddVertex1 == oddVertex2) {
					continue;
				}
				// TODO this must save the given arc
				Dijkstra a= new Dijkstra(city.graphe);
				oddGraph.graphe.addArc(new Arc(oddVertex1, oddVertex2, "",
						a.dijkstra(oddVertex1, oddVertex2).size()));
			}
		}
		
		return oddGraph;
	}
	
	public Set<Arc> perfectMatch(Ville city) {
		Set<Arc> match = new TreeSet<>();
		List<Arc> arcs = new ArrayList<>();
		Set<Element> inMatch = new TreeSet<>();
		
		/* Getting all the arcs from the graph */
		for (Element vertex : city.getPlaces()) {
			arcs.addAll(city.route_out(vertex));
		}
		
		/* Sorting the arcs based on their weight */

		arcs.sort((o1, o2) -> o1.getWeight() - o2.getWeight());

		/* Finding perfectMatch with euristic */
		for (Arc arc : arcs) {
			if (inMatch.contains(arc.src()) || inMatch.contains(arc.dst())) {
				continue;
			}
			match.add(arc);
			inMatch.add(arc.src());
			inMatch.add(arc.dst());
		}
		
		if (!inMatch.containsAll(city.getPlaces())) {
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
	
	public Ville makeGraphEulerian(Ville city, Set<Arc> match) {
		
		Ville eulerGraph = new Ville(city.name+"_Eulrn");
		Dijkstra dijkstra = new Dijkstra(city.graphe);

		for (Arc matchArc : match) {
			List<Arc> path = arcsOfPath(
						dijkstra.dijkstra(matchArc.src(), matchArc.dst()) );
			for (Arc arc : path) {
				// TODO This must allow multiArcs
				eulerGraph.graphe.addArc(arc);
			}
			
		}
		
		return eulerGraph;
	}
	
	public List<Element> algo(Ville city, Element root) {
		
		Ville oddGraph = constructOddGraph(city);
		
		Set<Arc> match = perfectMatch(oddGraph);
		
		Ville eulerianGraph = makeGraphEulerian(oddGraph, match);
		
		GogolL a = new GogolL();
		a.setCity(eulerianGraph);
		return a.algo(root);
		
	}
	
}
