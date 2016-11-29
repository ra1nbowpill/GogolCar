
public class Arc {

	/**
	 * @return the src
	 */
	public Element getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(Element src) {
		this.src = src;
	}

	/**
	 * @return the dst
	 */
	public Element getDst() {
		return dst;
	}

	/**
	 * @param dst the dst to set
	 */
	public void setDst(Element dst) {
		this.dst = dst;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

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
	public String toString() {
		return "(" + src.toString() + "," + dst.toString()+ ","+label + ")";
	}

	public Arc antiArc() {
		return new Arc(dst, src, "anti" + label);
	}

}
