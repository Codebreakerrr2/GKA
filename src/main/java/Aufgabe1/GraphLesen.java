package Aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class GraphLesen {
    /**
     * @author Usman Amini, Andre Demir.
     * Die Methode liest eine Graph-Datei und erzeugt aus der Datei ein Graph-Objekt.
     * @param fileName
     * @return Graph-Objekt/Null
     * @author Usman Amini, Andre Demir.
     * Die Methode liest eine Graph-Datei und erzeugt aus der Datei ein Graph-Objekt.
     */

    public static Graph readGraph(String fileName) {
        Pattern directionPattern = Pattern.compile("\\s*(?<nameNode1>\\w+)\\s*((?<direction>->|--)\\s*(?<nameNode2>\\w+)\\s*(?<edgeName>\\(\\w+\\))?\\s*(:\\s*(?<edgeGewicht>\\d+))?)?\\s*;");
        Graph graph = new MultiGraph(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher patternMatches = directionPattern.matcher(line);

                if (!(patternMatches.matches())) return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher patternMatches = directionPattern.matcher(line);

                if (patternMatches.matches()) {

                    String nameNode1 = patternMatches.group("nameNode1");
                    String nameNode2 = patternMatches.group("nameNode2");
                    String edgeName = patternMatches.group("edgeName");
                    String edgeGewicht = patternMatches.group("edgeGewicht");
                    String direction = patternMatches.group("direction");

                    Node node1 = graph.getNode(nameNode1);
                    if (node1 == null) node1 = graph.addNode(nameNode1);
                    //node1.setAttribute("ui.label", nameNode1);
                    if (nameNode2 != null) {
                        //gucken ob node2 schon existiert
                        Node node2 = graph.getNode(nameNode2);
                        //falls nicht, erstellen
                        if (node2 == null) node2 = graph.addNode(nameNode2);
                       // node2.setAttribute("ui.label", nameNode2);
                        if (direction.equals("->")) {
                            if (edgeName == null) edgeName = nameNode1 + nameNode2;
                            graph.addEdge(edgeName, node1, node2, true);
                        } else if (direction.equals("--")) {
                            if (edgeName == null) edgeName = nameNode1 + nameNode2;
                            graph.addEdge(edgeName, node1, node2, false);
                        }
                    }
                    if (edgeGewicht != null) {
                        graph.getEdge(edgeName).setAttribute("Gewicht", Double.parseDouble(edgeGewicht));
//                            //zeigt die Gewichtung der Kanten an
//                            graph.getEdge(edgeName).setAttribute("ui.label",Double.parseDouble(edgeGewicht));
//                            //gibt den Kantennamen style
//                            graph.setAttribute("ui.stylesheet",
//                                    "node { text-alignment: above; text-size: 14; text-color: red; text-mode: normal; text-background-mode: rounded-box; text-background-color: white; text-style: bold; text-offset: 5px, 0px; }");
//                            //nicht nötig aber verbessert die Anzeigequalität
//                            graph.setAttribute("ui.quality");
//                            graph.setAttribute("ui.antialias");
//                        }

                    }
                }
            }

            }
         catch(IOException e){
                e.printStackTrace();
            }
            return graph;


        }


        public static void main (String[]args) throws IOException {
            //"C:\\Users\\Usman\\Documents\\Java Files\\GraphPraktikum\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka"
            //Graph graph= GraphLesen.readGraph("C:\\Users\\Usman\\Documents\\Java Files\\GraphPraktikum\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph10.gka");
            Graph graph2Andre = GraphLesen.readGraph("C:\\Users\\andre\\Desktop\\GraphPraktikum\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
            System.setProperty("org.graphstream.ui", "swing");
            graph2Andre.display();
            //graph.display();
        }
}
