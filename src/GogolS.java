import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.lang.model.element.NestingKind;

public class GogolS {
	HashMap<Place, Boolean> marked=new HashMap<Place, Boolean>();
    private int count;
    public GogolS(Ville ville,String place){
    	Element p1=ville.findPlace(place);
    	for (Element p : ville.getPlaces()) {
			marked.put((Place)p, false);
		}
    	Dfs(ville,(Place)p1,place);
    	for (Element p : ville.getPlaces()) {
			if(!marked.get(p))
				System.out.println(p.toString()+"déconnécté");
		}
    }
	private void validatePlace(Ville ville, Place place) {
		if(!ville.placeExists(place))
			throw new IllegalArgumentException( " n'existe pas dans " + ville.villename());
    }
	private void Dfs(Ville ville, Place place,String p1) {
        count++;
        validatePlace(ville, place);
        marked.put(place, true);
        System.out.println(place.toString());
        for (Element p : ville.graphe.neighbours_out(place)) {
        	p= (Place) p;
        	p1=p.toString();
            if (!marked.get(p)) {
                Dfs(ville, (Place)p,p.toString());
                }
        }
        System.out.println(place.toString());
    }
}
