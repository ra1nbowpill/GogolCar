import org.junit.Test;

import java.io.File;
import java.util.*;

public class TestGogolL {

    private Set<Arc> arcsToEdge(Collection<Arc> coll) {
        Set<Arc> withEdges = new TreeSet<>();
        for (Arc arc : coll) {
            withEdges.add(arc);
            withEdges.add(arc.antiArc());
        }
        return withEdges;
    }

    private List<Arc> getArcs(IGraph graph) {
        List<Arc> res = new ArrayList<>();
        for (Element vertex : graph.getVertices()) {
            for (Arc arc : graph.delta_out(vertex)) {
                res.add(arc);
            }
        }
        return res;
    }

    public boolean algoSuceeded(IGraph graph, List<Element> path) {

        List<Arc> pathhsss = new ArrayList<>();
        for (int i = 1; i < path.size(); i++) {
            Arc a = new Arc(path.get(i-1), path.get(i));
            pathhsss.add(a);
            pathhsss.add(a.antiArc());
        }

        List<Arc> arcs = getArcs(graph);
        arcs.removeAll(pathhsss);
        return arcs.isEmpty();
    }

    private void testAlgoForEachVertex(Ville city) {
        testAlgoForEachVertex(city, false);
    }

    private void printEndInfo(IGraph graph, List<Element> path, GogolL a) {
        Set<Arc> antiArbo = a.publicAntiTree(path.get(0));

        System.out.println("Sucess : " + algoSuceeded(graph, path));
        System.out.println("Path : " + path);
        System.out.println("AntiArbo : " + antiArbo);
        a.DEBUG = true;
        System.out.println("Order : " + a.numberize(antiArbo));
        a.DEBUG = false;
    }

    private void testAlgoForEachVertex(Ville city, boolean debug) {
        for (Element root : city.getPlaces()) {
            Algo a = new GogolL();
            a.setCity(city);
            List<Element> path = a.algo(root);
            if (!algoSuceeded(city.graphe, path) || path.get(0) != path.get(path.size() - 1))
                printEndInfo(city.graphe, path, (GogolL)a);
            assert(algoSuceeded(city.graphe, path));
            assert(path.get(0) == path.get(path.size() - 1));
        }
    }

    /*public IGraph graphOfPath(String path) {
        Collection<Arc> euler = arcsToEdge(ParseEasy.toSet(new File(path)));
        return new AdjacencyListGraph(euler);
    }*/

    public Ville cityOfPath(String path) {
        return Ville.createCity(new File(path));
    }

	/* Graphs with no odd degree vertices */

	@Test
    public void testGraph1() {
        Ville city = cityOfPath("cities/city1.txt");

        testAlgoForEachVertex(city);
    }


    @Test
    public void testGraph2() {
        Ville city = cityOfPath("cities/city2.txt");

        testAlgoForEachVertex(city);
    }

    @Test
    public void testGraph3() {
        Ville city = cityOfPath("cities/city3.txt");

        testAlgoForEachVertex(city);
    }

    @Test
    public void testGraph4() {
        Ville city = cityOfPath("cities/city4.txt");

        testAlgoForEachVertex(city);
    }

    @Test
    public void testGraph5() {
        Ville city = cityOfPath("cities/cityodd1.txt");

        Algo a = new GogolL();
        a.setCity(city);
        List<Element> path = a.algo(city.getPlaces().get(0));
        assert(path == null);
    }
}
