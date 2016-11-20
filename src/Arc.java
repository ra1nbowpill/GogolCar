
public class Arc implements Comparable<Arc>{

	int src, dst;
	
	public Arc(int src, int dst) {
		this.src = src;
		this.dst = dst;
	}
	
	public Arc() {
		src = -1;
		dst = -1;
	}
	
	public int src(){
		return this.src;
	}
	
	public int dst(){
		return this.dst;
	}
	
	public String toString() {
		return "(" + src + "," + dst + ")";
	}
	
	public Arc antiArc() {
		return new Arc(dst, src);
	}

	@Override
	public int compareTo(Arc o) {
		if (src != o.src) {
			return src - o.src;
		} else {
			return dst - o.dst;
		}
	}
	
	public boolean equals(Arc o) {
		return src == o.src() ? (dst == o.dst() ? true : false) : false;
	}

}
