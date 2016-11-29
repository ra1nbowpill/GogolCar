import java.io.File;
import java.util.Scanner;

/**
 * Created by ygorgallina on 28/11/2016.
 */
public class UI {

    /* Bienvenue
     * comment voulez-vous entrer votre ville ?
     * fichier ? a la mano?
     * parsing du fichier ou de la mano
     * quel algoritme voulez-vous executer ?
     * S? L? XL ?
     * check si ca peut fonctionner ...
     * si oui -> executer
     * sinon -> erreur ce graphe n'est pas eulérien
     * afficher le resultat a l'écran et dans un fichier*/

    /* usage java UI inputfile algotoexecute outputfile */

    public static boolean fileExists(String path) {
        return fileExists(new File(path));
    }

    public static boolean fileExists(File file) {
        return file.exists();
    }

    public static Ville createVille(String path) {
        return Ville.createCity(new File(path));
    }
    public static Ville createVille(File file) {
        return createVille(file.getPath());
    }

    /*public static void commandLine(String input, String algo, String beginningPlace, String output) {

        if (!fileExists(input)) {
            System.err.println("File " + input + " does not exist");
            System.exit(1);
        }

        Ville ville = createVille(input);

        Algo algoExec;

        switch (algo) {
            case "S" : algoExec = new GogolS(ville); break;
            case "L" : algoExec = new GogolL(ville); break;
            case "XL" : algoExec = new GogolXL(ville); break;
            default:
                algoExec = new GogolS(ville); break;
                //System.err.println("Argument \"" + algo + "\" is not an algoritm");
                //System.exit(1);
        }

        algoExec.setOutput(output);

        algoExec.algo(beginningPlace);

    }*/

    public static void printUsage() {
        System.out.println("Usage : ./GogolCar inputFile algoritme outputFile");
        System.exit(1);
    }

    public static void main(String[] args) {

        if (args.length != 0) {
            if (args[0] == "-h" || args[0] == "--help") {
                printUsage();
            } else if (args.length == 4) {
                //commandLine(args[0], args[1], args[2], args[3]);
                return;
            } else {
                printUsage();
            }
        }

        Scanner s = new Scanner(System.in);
        Ville v;
        Algo a;

        System.out.println("Bienvenue chez GogolCar");
        System.out.println("Entrez le nom du fichier contenant la ville :");

        v = createVille(s.next());

        System.out.println("Quel algorithme souhaitez-vous executer ?");

        //a = createAlgo(s.next(), v);

        System.out.println("De quelle place souhaitez vous partir?");

        //a.execute(s.next());

        System.out.println("Merci d'avoir voyagé avec GogolCar");

    }

}
