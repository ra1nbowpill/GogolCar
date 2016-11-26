
public class Road {

	private String name;
	private Place placeSrc, placeDst;

	public Road(String name, Place placeSrc, Place placeDst) {
		this.name = name;
		this.placeSrc = placeSrc;
		this.placeDst = placeDst;
	}

	String getName() {return this.name;}
	Place getPlaceSrc() {return this.placeSrc;}
	Place getPlaceDst() {return this.placeDst;}
	
	public String toString() {
		return "(" + getName() + ", " + getPlaceSrc().toString() + ", " + getPlaceDst().toString() + ")";
	}

}
