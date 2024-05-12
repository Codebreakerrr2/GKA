package Aufgabe1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.AbstractList;
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

    public static Graph readGraph(String fileName) throws IOException {
        if (!Files.exists(java.nio.file.Path.of(fileName))) {
            throw new IOException("File not found");
        }
        //Pattern directionPattern = Pattern.compile("\\s*(?<nameNode1>[\\wäöüÄÖÜ]+)\\s*((?<direction>->|--)\\s*(?<nameNode2>[\\wäöüÄÖÜ]+)\\s*(?<edgeName>\\(\\w+\\))?\\s*(:\\s*(?<edgeGewicht>\\d+))?)?\\s*;");
        Pattern directionPattern = Pattern.compile("\\s*(?<nameNode1>\\w+)\\s*((?<direction>->|--)\\s*(?<nameNode2>\\w+)\\s*(?<edgeName>\\(\\w+\\))?\\s*(:\\s*(?<edgeGewicht>\\d+))?)?\\s*;");
        Graph graph = new MultiGraph(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
        String line;

        while ((line = br.readLine()) != null && !line.isBlank()) {
            Matcher patternMatches = directionPattern.matcher(line);

                if (!(patternMatches.matches())) return null;
            }

            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                Matcher patternMatches = directionPattern.matcher(line);
                if (patternMatches.matches()) {

                    String nameNode1 = patternMatches.group("nameNode1");
                    String nameNode2 = patternMatches.group("nameNode2");
                    String edgeName = patternMatches.group("edgeName");
                    //kein denglisch
                    String edgeGewicht = patternMatches.group("edgeGewicht");
                    String direction = patternMatches.group("direction");

                    Node node1 = graph.getNode(nameNode1);
                    if (node1 == null) node1 = graph.addNode(nameNode1);
                    //kann ja sein, dass es nur ein Knoten ohne Kante ist
                    if (nameNode2 != null) {
                        //gucken ob node2 schon existiert
                        Node node2 = graph.getNode(nameNode2);
                        //falls nicht, erstellen
                        if (node2 == null) node2 = graph.addNode(nameNode2);
                        if (direction.equals("->")) {
                            if (edgeName == null) edgeName = nameNode1 + '-' + nameNode2;

                           if(graph.getEdge(edgeName)==null){
                               graph.addEdge(edgeName, node1, node2, true);
                           }
                        } else if (direction.equals("--")) {
                            if (edgeName == null){
                                edgeName = nameNode1 + nameNode2;
                            }
                            if(graph.getEdge(edgeName)==null){
                                graph.addEdge(edgeName, node1, node2, false);
                            }
                        }
                    }
                    if (edgeGewicht != null) {
                        graph.getEdge(edgeName).setAttribute("Gewicht", Double.parseDouble(edgeGewicht));
                        graph.getEdge(edgeName).setAttribute("ui.label",edgeName +" (" + edgeGewicht + ")");
                    }   else {
                        graph.nodes().forEach((Node node) -> {
                            node.setAttribute("Name", node.getId());
                            node.setAttribute("ui.label", "Nodename: " + node.getAttribute("Name"));
                        });
                    }
                }
            }
            graph.setAttribute("ui.stylesheet", "edge { text-mode: normal; text-color: red;}");
            return graph;
        }

public static void main(String[] args) throws IOException {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph04.gka");
        graph.display();
    }
}
