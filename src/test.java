
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ville ville=new Ville();
		ville.addPlace("Place 1");
		ville.addPlace("Place 2");
		ville.addPlace("Place 3");
		ville.addPlace("Place 4");
		ville.addPlace("Place 5");
		ville.addPlace("Place 6");
		ville.addRoad("Rue 1", "Place 1", "Place 2");
		ville.addRoad("Rue 2", "Place 2", "Place 3");
		ville.removeRoad("Rue 1", "Place 1", "Place 2");
		System.out.println(ville.toString());
	}

}
