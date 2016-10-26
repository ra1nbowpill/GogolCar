
public class Road {

	private String name;
	private String placeSrc, placeDst;
	
	public Road(String name, String placeSrc, String placeDst) {
		this.name = name;
		this.placeSrc = placeSrc;
		this.placeDst = placeDst;
	}
	
	String getName() {return this.name;}
	String getPlaceSrc() {return this.placeSrc;}
	String getPlaceDst() {return this.placeDst;}
	
}
