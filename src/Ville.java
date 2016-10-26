import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ville {

	private ArrayList<Road> roads =new ArrayList<>();
	private ArrayList<String> places = new ArrayList<>();
	
	public void addPlace(String place) {
		places.add(place);
	}
	
	public void addRoad(Road road) {
		roads.add(road);
		if (!places.contains(road.getPlaceSrc())) {
			places.add(road.getPlaceSrc());
		}
		if (!places.contains(road.getPlaceDst())) {
			places.add(road.getPlaceDst());
		}
	}
		
	public void addRoad(String roadName, String placeSrc, String placeDst){
		Road road = new Road(roadName, placeSrc, placeDst);
		addRoad(road);
	}
	
	public ArrayList<Road> getRoads() {return roads;}
	public ArrayList<String> getPlaces() {return places;}
	
	public String toString() {
		String res = "";
		res += places.size() + ".\n" + roads.size() + ".\n";
		for (String place : places) {
			res += place + ".\n";
		}
		for (Road road : roads) {
			res += road.getName() +";" + road.getPlaceSrc() +";" + road.getPlaceDst() + ".\n";
		}
		return res;
	}
		
	private static Ville parseCity(Scanner s) {
		Ville ville = new Ville();
		Integer nbPlaces = -1, nbRoads = -1;
		
		s.useDelimiter("\\.");
		if (s.hasNext()) {
			nbPlaces = Integer.parseInt(s.next().trim());
		} else {
			System.out.println("Bad format in number of places.");
		}
		if (s.hasNext()) {
			nbRoads = Integer.parseInt(s.next().trim());
		} else {
			System.out.println("Bad format in number of roads.");
		}
		for (int i = 0; i < nbPlaces; i++){
			if (!s.hasNext()) {System.out.println("Bad format in place declaration."); break;}
			ville.addPlace(s.next().trim());
		}
		for (int i = 0; i < nbRoads; i++) {
			if (!s.hasNext()) {System.out.println("Bad format in roads declaration."); break;}
			
			String data[] = s.next().trim().split(";", 3);
			if (data.length == 3) {
				ville.addRoad(data[0], data[1], data[2]);
			} else {
				System.out.println("Bad format in road declarations");
			}
		}
		return ville;
	}
	
	public static Ville createCity(File file) {
		try {
			Scanner s = new Scanner(file);
			return parseCity(s);
		} catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable.");
			return null;
		}
	}

}
