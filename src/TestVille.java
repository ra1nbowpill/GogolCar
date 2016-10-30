import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class TestVille {

	File f = new File("test.txt");

	@Test
	public void testCreateCity() {
	
		Ville v = Ville.createCity(f);

		System.out.println(v);
	}

	@Test
	public void testCityContent() {

		Ville v = Ville.createCity(f);
		ArrayList<Arc> arcs = v.toArcs();
		ArrayList<Integer> vertices = v.toVertices();
		ArrayList<Road> roads = v.toRoads(arcs);
		ArrayList<String> places = v.toPlaces(vertices);

		System.out.println("Ville :");
		System.out.println(v);
		System.out.println("Arcs :");
		System.out.println(arcs);
		System.out.println("Sommets :");
		System.out.println(vertices);
		System.out.println("Rues :");
		System.out.println(roads);
		System.out.println("Places :");
		System.out.println(places);

	}

}
