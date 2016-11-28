
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ville ville=new Ville("nantes");
		ville.addPlace("Place 1");
		ville.addPlace("Place 2");
		ville.addPlace("Place 3");
		ville.addPlace("Place 4");
		ville.addPlace("Place 5");
		ville.addPlace("Place 6");
		ville.addPlace("Place 7");
		ville.addPlace("Place 8");
		ville.addRoad("Rue 1", "Place 1", "Place 2");
		ville.addRoad("Rue 2", "Place 2", "Place 3");
		ville.addRoad("Rue 3", "Place 3", "Place 4");
		ville.addRoad("Rue 4", "Place 3", "Place 5");
		ville.addRoad("Rue 5", "Place 5", "Place 6");
		ville.addRoad("Rue 6", "Place 4", "Place 7");
		ville.addRoad("Rue 7", "Place 7", "Place 8");
		GogolS gogolS= new GogolS(ville,"Place 1");
	}

}
