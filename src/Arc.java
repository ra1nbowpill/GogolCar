
public class Arc {

	private Element src, dst;
	private String label;
	private Integer weight;
	
	
	public Arc(Element src, Element dst, String label) {
		this.src = src;
		this.dst = dst;
		this.label = label;
	}
	
	public Arc(Element src, Element dst, String label,Integer weight) {
		this.src = src;
		this.dst = dst;
		this.label = label;
		this.weight = weight;
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

	public Integer weight() {
		return weight;
	}

	public String toString() {
		return "(" + src.toString() + "," + dst.toString()+ ","+label + ")";
	}

	public Arc antiArc() {
		return new Arc(dst, src, "anti" + label);
	}

}
