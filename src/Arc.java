
public class Arc implements Comparable<Arc> {


	private Element src, dst;
	private String label;
	private Integer weight;
	
	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}
	
	public Arc(Element src, Element dst) {
		this(src, dst, Ville.generate(10));
	}

	public Arc(Element src, Element dst, String label) {
		this(src, dst, label, null);
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
		return "(" + label + " :" +src.toString() + "-> " + dst.toString() +")"+"\n";
	}

	public Arc antiArc() {
		return new Arc(dst, src, label + " ", weight);
	}

	@Override
	public int compareTo(Arc o) {
		if (src() != o.src()) {
			return ((Place)src()).compareTo((Place)o.src());
		} else if (dst() != o.dst()) {
			return ((Place)dst()).compareTo((Place)o.dst());
		} else if (weight() != o.weight()){
			return weight().compareTo(o.weight());
		} else {
			return label().compareTo(o.label());
		}
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof Arc) {
			Arc a = (Arc) o;
			return src() == a.src() && dst() == a.dst() && weight() == a.weight();
		}
		return false;
	}
}