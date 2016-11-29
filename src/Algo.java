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

	public void setCity(Ville city);
	public List<Element> algo(Element beginningPlace);
	public List<Element> algo(String strBeginningPlace);

}
