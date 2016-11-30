import java.util.List;

public interface Algo {

	/*
	*
	* Each Gogol.. algorithm shall implements this interface
	* Each Gogol.. class should be a bean
	* which means it's constructor must take no argument
	* and the parameters must be set after the new statement
	*
	* */

	void setCity(Ville city);
	List<Road> algo(Element beginningPlace);
	List<Road> algo(String strBeginningPlace);

}
