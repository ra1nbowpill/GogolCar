/**
 * Created by ygorgallina on 26/11/2016.
 */
public class WeighedArc extends Arc {
    private Integer weight;

    public WeighedArc(Element vertex1, Element vertex2, String label, Integer weight) {
        super(vertex1, vertex2, label);
        this.weight = weight;
    }

    public Integer weight() {
        return weight;
    }
}
