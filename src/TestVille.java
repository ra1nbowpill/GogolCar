import java.io.File;

import org.junit.Test;

public class TestVille {

	@Test
	public void testCreateCity() {
		
		File f = new File("test.txt");
		
		Ville v = Ville.createCity(f);
		
		System.out.println(v.toString());
	}
	
}
