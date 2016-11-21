import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ParseEasy {
	private static Set<Arc> parseFile(Scanner s) {
		Set<Arc> res = new TreeSet<>();
		
		while (s.hasNextLine()) {
			String a = s.nextLine();
			Integer base = null;
			for (String p : a.split(" ")) {
				if (base == null) {
					base = new Integer(p);
				} else {
					res.add(new Arc(base, new Integer(p)));
				}
			}	
		}
		
		return res;
	}
	
	private static Set<Arc> setOfFile(File file) {
		try {
			Scanner s = new Scanner(file);
			return parseFile(s);
		} catch (FileNotFoundException e) {
			System.out.println("Fichier " + file.getName() + " introuvable.");
			return Collections.emptySet();
		}
	}
	
	public static Set<Arc> toSet(File file) {
		return setOfFile(file);
	}
	
	public static String toDot(File file) {
		Set<Arc> set = setOfFile(file);
		String res = "graph g {";
		for (Arc a : set) {
			res += a.src() + " -- " + a.dst() +";";
		}
		res += "}";
		return res;
	}
}
