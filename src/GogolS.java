import java.lang.reflect.Array;
import java.util.*;

import javax.lang.model.element.NestingKind;

public class GogolS implements Algo {

	private Ville city;

	private HashMap<Element, Boolean> marked=new HashMap<>();
	private ArrayList<Element> results=new ArrayList<>();

	public GogolS() {}

    public GogolS(Ville ville,String place){
    	Element p1=ville.findPlace(place);
    	for (Element p : ville.getPlaces()) {
			marked.put(p, false);
		}
    	Dfs(ville,p1);
    	for (Element p : ville.getPlaces()) {
			if(!marked.get(p)) {
				//results.add(p.toString()+"déconnécté");
				System.err.println("The city is not connex");
				break;
			}

		}
    }

	private void validatePlace(Ville ville, Element place) {
		if(!ville.placeExists((Place)place))
			throw new IllegalArgumentException( " n'existe pas dans " + ville.villename());
    }

	private void Dfs(Ville ville, Element place) {
        Boolean bool1=false,bool2=false;
        validatePlace(ville, place);
        marked.put(place, true);
        results.add(place);
        for (Element p : ville.graphe.neighbours_out(place)) {
			if(!marked.get(p)) {
				bool1 = true;
			}
		}
        if(bool1){
	        for (Element p : ville.graphe.neighbours_out(place)) {
	            if (!marked.get(p)) {
	                Dfs(ville, p);
	                results.add(place);
	                bool2=true;
				}
	        }
	        if(!bool2)
	        	results.add(place);
        }
    }

	public ArrayList<Element> results(){
		return results;
	}

	@Override
	public void setCity(Ville city) {
		this.city = city;
	}

	@Override
	public List<Element> algo(Element beginningPlace) {
		marked = new HashMap<>();
		for (Element p : city.getPlaces()) {
			marked.put(p, false);
		}
		Dfs(city, beginningPlace);
		return results;
	}

	@Override
	public List<Element> algo(String place) {
		Place beginningPlace = city.findPlace(place);
		if (beginningPlace == null) {
			System.err.println("Place " + place + " does not exist");
			return null;
		}
		return algo(beginningPlace);
	}
}
