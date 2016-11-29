
public class Place extends Element implements Comparable<Place> {
	String name;

	public Place(String name) {
		this.name = name;
	}
	
	public Place(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return  name ;
	}

	@Override
	public int compareTo(Place o) {
		return name.compareTo(o.getName());
	}

	public boolean equals(Place o) {
		return this == o
				|| getName().equals(o.getName());
	}
}
