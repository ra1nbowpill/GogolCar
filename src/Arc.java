
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
	
	public int Src(){
		return this.src;
	}
	
	public int Dst(){
		return this.dst;
	}
	
	public String toString() {
		return "(" + src + "," + dst + ")";
	}

}
