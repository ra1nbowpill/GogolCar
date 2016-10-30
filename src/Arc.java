
public class Arc {

	int src, dst;
	
	public Arc(int src, int dst) {
		this.src = src;
		this.dst = dst;
	}
	
	public Arc() {
		src = -1;
		dst = -1;
	}
	
	public String toString() {
		return "(" + src + "," + dst + ")";
	}

}
