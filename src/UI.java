import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ygorgallina on 28/11/2016.
 */
public class UI {

    /* Bienvenue
     * comment voulez-vous entrer votre ville ?
     * fichier ?
     * parsing du fichier
     * quel algoritme voulez-vous executer ?
     * S? L? XL ?
     * check si ca peut fonctionner ...
     * si oui -> executer
     * sinon -> erreur ce graphe n'est pas eulérien
     * afficher le resultat a l'écran et dans un fichier*/

    /* usage java UI inputfile algotoexecute outputfile */

    private static Scanner scanner;


    private static Ville createCity(String path) {
        return Ville.createCity(new File(path));
    }
    private static Ville createCity(File file) {
        return createCity(file.getPath());
    }

    private static Algo algoOfString(String strAlgo) {
        if (strAlgo.length() > 2 || strAlgo.length() < 1) {
            strAlgo = "";
        } else {
            strAlgo = strAlgo.toUpperCase();
        }
        switch (strAlgo) {
            //TODO
            //case "S" : return new GogolS();
            case "L" : return new GogolL();
            //case "XL" : return new GogolXL();
        }
        return null;
    }

    private static Element startingPlaza(Ville city, String strPlaza) {
        return city.findPlace(strPlaza);
    }

    private static File askForCityFile() {
        String messsage = "Entrez le nom du fichier contenant votre ville :";
        File file;

        System.out.println(messsage);
        String path = scanner.next();
        file = new File(path);
        file = file.exists() ? file : null;

        while (file == null) {
            System.out.println("Le fichier " + path + " n'existe pas");

            System.out.println(messsage);
            path = scanner.next();
            file = new File(path);
            file = file.exists() ? file : null;
        }

        return file;
    }

    private static Algo askForAlgo() {
        String message = "Entrez le nom de l'agorithme a executer [\"S\", \"L\", \"XL\"] :";
        Algo algo;

        System.out.println(message);
        String strAlgo = scanner.next();
        algo = algoOfString(strAlgo);

        while (algo == null) {
            System.out.println("L'algorithme " + strAlgo + " n'existe pas");

            System.out.println(message);
            strAlgo = scanner.next();
            algo = algoOfString(strAlgo);
        }

        return algo;
    }

    private static Element askForPlaza(Ville city) {
        String message = "Entrez le nom de la place par laquelle commencer le trajet :";
        Element plaza;

        System.out.println(message);
        String strPlaza = scanner.next();
        plaza = startingPlaza(city, strPlaza);

        while (plaza == null) {
            System.out.println("La place " + strPlaza + " n'existe pas");

            System.out.println(message);
            strPlaza = scanner.next();
            plaza = startingPlaza(city, strPlaza);
        }

        return plaza;
    }

    private static boolean askYesNo(String message) {
        String strAnswer;
        Boolean answer = null;
        while (answer == null) {
            System.out.println(message);
            strAnswer = scanner.next();
            strAnswer = strAnswer.toLowerCase();
            switch (strAnswer) {
                case "o":
                case "y":
                case "oui":
                case "yes":
                    answer = true;
                    break;
                case "n":
                case "no":
                case "non":
                    answer = false;
                    break;
                default:
                    answer = null;
            }
        }
        return answer;
    }

    private static File askForOutputFile() {
        String message = "Entrez le nom du fichier où écrire le résultat :";
        File outputFile;

        System.out.println(message);
        String path = scanner.next();
        outputFile = new File(path);

        if (outputFile.exists()) {
            System.out.println("Le fichier " + outputFile + " existe déjà");
            askYesNo("Voulez-vous l'écraser ? [Oui: {o, oui}, Non: {n, non}]");
        }

        return outputFile;
    }

    private static boolean writeResultOnOutputFile(File outputFile, List<Element> result) {
        try {
            Writer writer = new FileWriter(outputFile);
            writer.write(result.toString());
            writer.close();
            return true;
        } catch(IOException e) {
            return false;
        }

    }

    private static void parseArgs(String[] args) {
        /* TODO
         * this function should fill the attributes
         * corresponding to the strings
         */
        String pathOutputFile,
                pathCityFile,
                strStartingPlaza,
                strAlgo;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    printUsage();
                    break;
                case "-o":
                case "--output":
                    pathOutputFile = args[i+1];
                    i++;
                    break;
                case "-c":
                case "--city":
                    pathCityFile = args[i+1];
                    i++;
                    break;
                case "-p":
                case "--plaza":
                    strStartingPlaza = args[i+1];
                    i++;
                    break;
                case "-a":
                case "--algo":
                    strAlgo = args[i+1];
                    i++;
                    break;
                default: break;
            }
        }
    }

    private static void printUsage() {
        String usage = "" +
                "Usage : ./GogolCar\n";

        String manual = "" +
                "-h --help\t: print this message\n" +
                "-a --algo\t: sets the algorithm to be executed\n" +
                "-p --plaza\t: sets the beginning plaza\n" +
                "-c --city\t: sets the file containing the city\n" +
                "-o --output\t: sets the output file\n";
        System.out.print(usage);
        //System.out.print(manual);
        System.exit(1);
    }


    private static void init() {
        if (scanner != null) {
            return;
        }
        scanner = new Scanner(System.in);
    }

    private static void quit() {
        scanner.close();
    }

    private static File cityFile;
    private static File outputFile;
    private static Ville city;
    private static Algo algo;
    private static Element startingPlaza;

    public static void main(String[] args) {

        if (args.length != 0) {
            parseArgs(args);
        }

        init();

        List<Element> result;

        /* Greeting message */
        System.out.println(" *** Bienvenue chez GogolCar *** ");

        if (cityFile == null)
            cityFile = askForCityFile();

        if (algo == null)
            algo = askForAlgo();

        city = createCity(cityFile);

        if (startingPlaza == null)
            startingPlaza = askForPlaza(city);

        /* Execute given algorithm on given city starting by given plaza */
        algo.setCity(city);
        result = algo.algo(startingPlaza);

        System.out.println("Voici le chemin a parcourir pour photographier toute la ville :");
        System.out.println(result);

        if (outputFile == null)
            outputFile = askForOutputFile();

        if (!writeResultOnOutputFile(outputFile, result)) {
            System.out.println("Impossible d'écrire sur le fichier " + outputFile);
        }

        /* Goodbye message */
        System.out.println("Merci d'avoir voyagé avec GogolCar");

        quit();
    }

}
