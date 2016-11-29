import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.lang.model.element.NestingKind;

public class GogolS {
	HashMap<Place, Boolean> marked=new HashMap<Place, Boolean>();
    public GogolS(Ville ville,String place){
    	Element p1=ville.findPlace(place);
    	for (Element p : ville.getPlaces()) {
			marked.put((Place)p, false);
		}
    	Dfs(ville,(Place)p1);
    	for (Element p : ville.getPlaces()) {
			if(!marked.get(p))
				System.out.println(p.toString()+"déconnécté");
		}
    }
	private void validatePlace(Ville ville, Place place) {
		if(!ville.placeExists(place))
			throw new IllegalArgumentException( " n'existe pas dans " + ville.villename());
    }
	private void Dfs(Ville ville, Place place) {
        Boolean bool1=false,bool2=false;
        validatePlace(ville, place);
        marked.put(place, true);
        System.out.println(place.toString());
        for (Element p : ville.graphe.neighbours_out(place)) {
			if(!marked.get(p))
				bool1=true;
		}
        if(bool1){
	        for (Element p : ville.graphe.neighbours_out(place)) {
	        	p= (Place) p;
	            if (!marked.get(p)) {
	                Dfs(ville, (Place)p);
	                System.out.println(place.toString());
	                bool2=true;
	                }
	        }
	        if(!bool2)
	        	System.out.println(place.toString());
        }
    }
}
