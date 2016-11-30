import java.util.ArrayList;
import java.util.HashMap;

public class GogolSS {
	HashMap<Arc, Boolean> marked=new HashMap<Arc, Boolean>();
	ArrayList<String> results=new ArrayList<>();
	public GogolSS(Ville ville, String place){
		for (Road road : ville.getRoads()) {
			marked.put(ville.toArc(road), false);
		}
		for (Road road : ville.getRoads()) {
			if(road.getPlaceSrc().equals(ville.findPlace(place))){
				Dfs(ville,ville.toArc(road));
				results.add(ville.toArc(road).antiArc().toString());
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
        results.add(route.toString());
	    for (Arc road : ville.neighbors_roads(route)) {
	        if (!marked.get(road)) {
	            Dfs(ville, road);
	            results.add(road.antiArc().toString());
	            }
	    }
    }
	public ArrayList<String> results(){
		return results;
	}
}
