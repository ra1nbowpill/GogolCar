
public class Arc {

	Element src, dst;
	String label;
	
	
	public Arc(Element src, Element dst, String label) {
		this.src = src;
		this.dst = dst;
		this.label = label;
	}
	
	
	public Element src(){
		return this.src;
	}
	
	public Element dst(){
		return this.dst;
	}
	
	public String label(){
		return this.label;
	}
	public String toString() {
		return "(" + src.toString() + "," + dst.toString()+ ","+label + ")";
	}

}
