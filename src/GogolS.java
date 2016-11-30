import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GogolS implements Algo{
	HashMap<Arc, Boolean> marked=new HashMap<Arc, Boolean>();
	List<Road> results=new ArrayList<>();
	Ville city;
	public GogolS(){
		
	}
	public GogolS(Ville ville, String place){
		for (Road road : ville.getRoads()) {
			marked.put(ville.toArc(road), false);
		}
		for (Road road : ville.getRoads()) {
			if(road.getPlaceSrc().equals(ville.findPlace(place))){
				Dfs(ville,ville.toArc(road));
				results.add(ville.toRoad(ville.toArc(road).antiArc()));
			}
		}
	}
	public void validateRoad(Ville ville, Arc route){
		if(!ville.roadExists(ville.toRoad(route)))
			throw new IllegalArgumentException( "la route "+route.toString()+" n'existe pas dans ");
	}
	private void Dfs(Ville ville, Arc route) {
        validateRoad(ville, route);
        marked.put(route, true);
        results.add(ville.toRoad(route));
	    for (Arc road : ville.neighbors_roads(route)) {
	        if (!marked.get(road)) {
	            Dfs(ville, road);
	            results.add(ville.toRoad(road.antiArc()));
	            }
	    }
    }
	public List<Road> results(){
		return results;
	}
	@Override
	public void setCity(Ville city) {
		this.city = city;
	}
	@Override
	public List<Road> algo(Element beginningPlace) {
		for (Road road : city.getRoads()) {
			marked.put(city.toArc(road), false);
		}
		for (Road road : city.getRoads()) {
			if(road.getPlaceSrc().equals(city.findPlace(beginningPlace.toString()))){
				Dfs(city,city.toArc(road));
				results.add(city.toRoad(city.toArc(road).antiArc()));
			}
		}
		return results;
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
