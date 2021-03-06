import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ville {

	//IGraph graphe =new AdjacencyListGraph();
	IGraph graphe = new AdjacencyListGraphMulti();
	String name;
	public Ville(String name) {
		super();
		this.name = name;
	}
	public String villename(){
		return name;
	}
	public void addPlace(String place) {
		Place p=new Place(place);
		if(!this.getPlaces().contains(p)){
			graphe.addVertex(p);
		}
	}
	public void addRoad(Road road) {
		Arc arc=new Arc(road.getPlaceSrc(), road.getPlaceDst(), road.getName());
		graphe.addArc(arc);
		
	}
	public void addRoad(String roadName, String placeSrc, String placeDst){
		Element src,dst;
		if(findPlace(placeSrc)==null)
			src=new Place(placeSrc);
		else
			src=findPlace(placeSrc);
		if(findPlace(placeDst)==null)
			dst=new Place(placeDst);
		else
			dst=findPlace(placeDst);
		Road road=new Road(roadName,(Place)src,(Place)dst);
		addRoad(road);
	}
	public Place findPlace(String place){
		for (Element p : graphe.getVertices()) {
			if(p.toString().equals(place))
				return (Place)p;
		}
		return null;
	}
	public void removeRoad(Road road){
		removeRoad(road.getName(), road.getPlaceSrc().toString(), road.getPlaceDst().toString());
	}
	public void removeRoad(String roadName, String placeSrc, String placeDst){
		for (Road road : getRoads()) {
			if(road.getName().equals(roadName) && road.getPlaceSrc().toString().equals(placeSrc) && road.getPlaceDst().toString().equals(placeDst))
				graphe.removeArc(toArc(road));
		}
	}
	public void removePlace(Place place){
		for (Element p : graphe.getVertices()) {
			if(p.equals(place))
				graphe.removeVertex(p);
		}
		
	}
	public void removePlace(String place){
		for (Element p : graphe.getVertices()) {
			if(p.toString().equals(place))
				graphe.removeVertex(p);
		}
	}
	public ArrayList<Arc> route_in(Element place){
		return graphe.delta_in(place);
	}
	public ArrayList<Arc> route_out(Element place){
		return graphe.delta_in(place);
	}
	public ArrayList<Road> getRoads() {
		return this.toRoads(graphe.arcs);
	}
	public ArrayList<Element> getPlaces() {
		ArrayList<Element> places=new ArrayList<>();
		for (Element place : graphe.getVertices()) {
			places.add(place);
		}
		return places;
	}
	public Arc toArc(Road road) {
		for (Arc arc : graphe.arcs) {
			if(arc.label().equals(road.getName()) && arc.src().toString().equals(road.getPlaceSrc().toString()) && arc.dst().toString().equals(road.getPlaceDst().toString()))
				return arc;
		}
		return null;
	}
	public ArrayList<Arc> toArcs() {
		ArrayList<Arc> arcs = new ArrayList<>();;
		for(Road road : getRoads()) {
			arcs.add(toArc(road));
			
		}
		return arcs;
	}
	public int toVertex(String place) {
		return getPlaces().indexOf(place);
	}
	public ArrayList<Integer> toVertices() {
		ArrayList<Integer> vertices = new ArrayList<>();
		for(Element place : getPlaces()) {
			vertices.add(toVertex(place.toString()));
		}
		return vertices;
	}
	public Road toRoad(Arc arc) {		
		Road road=new Road(arc.label(), (Place)arc.src(), (Place)arc.dst());
		return road;
	}
	public ArrayList<Road> toRoads(ArrayList<Arc> arcs) {
		ArrayList<Road> roads = new ArrayList<>();
		for(Arc arc : arcs) {
			roads.add(toRoad(arc));
		}
		return roads;
	}
	public String toPlace(int vertex) {
		return getPlaces().get(vertex).toString();
	}
	public ArrayList<String> toPlaces(ArrayList<Integer> vertices) {
		ArrayList<String> places = new ArrayList<>();
		for(int vertex : vertices) {
			places.add(toPlace(vertex));
		}
		return places;
	}
	public String toString() {
		String res = "";
		res += graphe.getVertices().size() + ".\n" + graphe.arcs.size() + ".\n";
		for (Element place : getPlaces()) {
			res += place.toString() + ".\n";
		}
		for (Road road : getRoads()) {
			res += road.getName() +"      de: " + road.getPlaceSrc() +" vers: " + road.getPlaceDst() + ".\n";
		}
		return res;
	}
	public boolean placeExists(Place place){
		for (Element p : this.getPlaces()) {
			if(p.equals(place))
				return true;
		}
		return false;
	}
	public Boolean roadExists(Road road){
		for (Road route : getRoads()) {
			if(route.equals(road))
				return true;
		}
		return false;
		
	}
	int numberPlaces(){
		return this.graphe.V();
	}
	public ArrayList<Element> neighbors_in(Element place){
		return graphe.neighbours_in(place);
	}
	public ArrayList<Element> neighbors_out(Element place){
		return graphe.neighbours_out(place);
	}
	public ArrayList<Arc> neighbors_roads(Arc road){
		ArrayList<Arc> roads=new ArrayList<Arc>();
		for (Arc route : graphe.arcs) {
			if(route.src().equals(road.dst()) && !route.equals(road))
				roads.add(route);
		}
		return roads;
	}


	public String toDot() {
		return graphe.toDot();
	}

	public static String generate(int length)
	{
		    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
		    String pass = "";
		    for(int x=0;x<length;x++)
		    {
		       int i = (int)Math.floor(Math.random() * chars.length());
		       pass += chars.charAt(i);
		    }
		    //System.out.println(pass);
		    return pass;
	}
	private static Ville parseCity(Scanner s) {
		Ville ville = new Ville("ville");
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
				ville.addRoad(data[0], data[2], data[1]);
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

	public static Ville createCity(String path) {
		return createCity(new File(path));
	}
}
