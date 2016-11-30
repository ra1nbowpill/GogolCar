import java.util.*;

public class GogolXL implements Algo {
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

	private Ville city;

	public GogolXL() {}

	private Set<Element> oddVertices(Ville ville) {
		return oddVertices(ville.graphe);
	}

	private Set<Element> oddVertices(IGraph graph) {
		Set<Element> oddVertices = new TreeSet<>();
		for (Element place : graph.getVertices()) {
			if (graph.delta_out(place).size() % 2 == 1) {
				oddVertices.add(place);
			}
		}
		return oddVertices;
	}

	/**
	 * Computes G' the complete graph containing the odd degree vertices of G
	 * the weigh is the shortest path from x to y in G
	 * @param city the base graph
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
				Dijkstra a= new Dijkstra(city.graphe);

				List<Element> shortest = a.dijkstra(oddVertex1, oddVertex2);
				Arc arc = new Arc(oddVertex1, oddVertex2, Ville.generate(10), shortest.size());
				oddGraph.graphe.addArc(arc);
			}
		}
		
		return oddGraph;
	}
	
	public Set<Arc> perfectMatch(Ville city) {
		Set<Arc> match = new TreeSet<>();
		List<Arc> arcs;
		Set<Element> inMatch = new TreeSet<>();
		
		/* Getting all the arcs from the graph */
		arcs = city.graphe.arcs;
		
		/* Sorting the arcs based on their weight */

		arcs.sort(Comparator.comparingInt(Arc::getWeight));

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

			Arc a = null;
			for (Arc arc : city.graphe.delta_out(path.get(i-1))) {
				if (arc.dst().equals(path.get(i))) {
					a = arc;
					break;
				}
			}
			arcs.add(a);
			//arcs.add(new Arc(path.get(i-1), path.get(i), ""));
		}
		return arcs;
	}
	
	public Ville makeGraphEulerian(Ville city, Set<Arc> match) {
		
		Ville eulerGraph = new Ville(city.name+"_Eulrn");
		Dijkstra dijkstra = new Dijkstra(city.graphe);

		IGraph graph = new AdjacencyListGraphMulti(city.graphe);

		for (Arc matchArc : match) {
			List<Arc> path = arcsOfPath(
						dijkstra.dijkstra(matchArc.src(), matchArc.dst()) );

			for (Arc arc : path) {
				graph.addArc(arc);
				graph.addArc(arc.antiArc());
			}
		}
		eulerGraph.graphe = graph;
		return eulerGraph;
	}
	
	public List<Road> algo(Ville city, Element root) {

		if (oddVertices(city).isEmpty()) {
			GogolL a = new GogolL();
			a.setCity(city);
			return a.algo(root);
		}

		Ville oddGraph = constructOddGraph(city);

		Set<Arc> match = perfectMatch(oddGraph);

		Ville eulerianGraph = makeGraphEulerian(city, match);


		assert(oddVertices(eulerianGraph.graphe).isEmpty());

		GogolL a = new GogolL();
		a.setCity(eulerianGraph);
		return a.algo(root);
		
	}


	@Override
	public void setCity(Ville city) {
		this.city = city;
	}

	@Override
	public List<Road> algo(Element beginningPlace) {
		return algo(this.city, beginningPlace);
	}

	@Override
	public List<Road> algo(String place) {
		Place beginningPlace = city.findPlace(place);
		if (beginningPlace == null) {
			System.err.println("Place " + place + " does not exist");
			return null;
		}
		return algo(beginningPlace);
	}
}
