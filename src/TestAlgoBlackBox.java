import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ygorgallina on 30/11/2016.
 */
public class TestAlgoBlackBox {

    private List<Arc> getArcs(IGraph graph) {
        List<Arc> res = new ArrayList<>();
        for (Element vertex : graph.getVertices()) {
            for (Arc arc : graph.delta_out(vertex)) {
                res.add(arc);
            }
        }
        return res;
    }

    public boolean algoSuceeded(IGraph graph, List<Road> path) {

        List<Arc> pathhsss = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            Arc a = new Arc(path.get(i).getPlaceSrc(), path.get(i).getPlaceDst(), path.get(i).getName());
            pathhsss.add(a);
            pathhsss.add(a.antiArc());
        }

        List<Arc> arcs = getArcs(graph);
        arcs.removeAll(pathhsss);
        
        return arcs.isEmpty();
    }

    private void printEndInfo(IGraph graph, List<Road> path, Algo algo) {

        System.out.println("Success? : " + algoSuceeded(graph, path));
        System.out.println("Path : " + path);
    }

    private void testAlgoForEachVertex(Ville city, Algo algo) {
        algo.setCity(city);

        for (Element root : city.getPlaces()) {

            List<Road> path = algo.algo(root);

            if (!algoSuceeded(city.graphe, path) || path.get(0) != path.get(path.size() - 1))
                printEndInfo(city.graphe, path, algo);

            assert(algoSuceeded(city.graphe, path));
        }
    }

    private void testAlgoForOddCity(Ville city, Algo algo) {
        algo.setCity(city);

        for (Element root : city.getPlaces()) {

            List<Road> path = algo.algo(root);

            if (path != null)
                printEndInfo(city.graphe, path, algo);
            assert (path.isEmpty());
        }
    }

    Ville city1 = Ville.createCity("cities/city1.txt"),
            city2 = Ville.createCity("cities/city2.txt"),
            city3 = Ville.createCity("cities/city3.txt"),
            city4 = Ville.createCity("cities/city4.txt");
    Ville cityOdd1 = Ville.createCity("cities/cityodd1.txt"),
            cityOdd2 = Ville.createCity("cities/cityodd2.txt");

    Ville data1 = Ville.createCity("cities/Ville1.txt"),
        data2 = Ville.createCity("cities/Ville2.txt");

    @Test
    public void testGraph1S() {
        testAlgoForEachVertex(city1, new GogolS());
    }
    @Test
    public void testGraph1L() {
        testAlgoForEachVertex(city1, new GogolL());
    }
    @Test
    public void testGraph1XL() {
        testAlgoForEachVertex(city1, new GogolXL());
    }


    @Test
    public void testGraph2S() {
        testAlgoForEachVertex(city2, new GogolS());
    }
    @Test
    public void testGraph2L() {
        testAlgoForEachVertex(city2, new GogolL());
    }
    @Test
    public void testGraph2XL() {
        testAlgoForEachVertex(city2, new GogolXL());
    }

    @Test
    public void testGraph3S() {
        testAlgoForEachVertex(city3, new GogolS());
    }
    @Test
    public void testGraph3L() {
        testAlgoForEachVertex(city3, new GogolL());
    }
    @Test
    public void testGraph3XL() {
        testAlgoForEachVertex(city3, new GogolXL());
    }

    @Test
    public void testGraph4S() {
        testAlgoForEachVertex(city4, new GogolS());
    }
    @Test
    public void testGraph4L() {
        testAlgoForEachVertex(city4, new GogolL());
    }
    @Test
    public void testGraph4XL() {
        testAlgoForEachVertex(city4, new GogolXL());
    }

    @Test
    public void testGraphOdd1S() {
        testAlgoForEachVertex(cityOdd1, new GogolS());
    }
    @Test
    public void testGraphOdd1L() {
        testAlgoForOddCity(cityOdd1, new GogolL());
    }
    @Test
    public void testGraphOdd1XL() {
        testAlgoForEachVertex(cityOdd1, new GogolXL());
    }

    @Test
    public void testGraphOdd2S() {
        testAlgoForEachVertex(cityOdd2, new GogolS());
    }
    @Test
    public void testGraphOdd2L() {
        testAlgoForOddCity(cityOdd2, new GogolL());
    }
    @Test
    public void testGraphOdd2XL() {
        testAlgoForEachVertex(cityOdd2, new GogolXL());
    }

    /* Sans cycle eulérien */
    @Test
    public void testGraphDATA1S() {
        testAlgoForEachVertex(data1, new GogolS());
    }
    @Test
    public void testGraphDATA1L() {
        testAlgoForOddCity(data1, new GogolL());
    }
    @Test
    public void testGraphDATA1XL() {
        testAlgoForEachVertex(data1, new GogolXL());
    }

    /* Avec cycle eulérien */
    @Test
    public void testGraphDATA2S() {
        testAlgoForEachVertex(data2, new GogolS());
    }
    @Test
    public void testGraphDATA2L() {
        testAlgoForEachVertex(data2, new GogolL());
    }
    @Test
    public void testGraphDATA2XL() {
        testAlgoForEachVertex(data2, new GogolXL());
    }

}
