package Aufgabe3.junk;

import Aufgabe1.GraphTraversieren;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.*;

import static Aufgabe3.EulergraphAlgorithmen.eachNodeHasEvenDegree;

public class Junk {
    /**
     * Findet einen gemeinsamen Knoten zwischen zwei Kreisen
     * @param eulerianCircuit Eulerkreis
     * @param currentCircle aktueller Kreis
     * @return Node commonNode
     */
    private static Node findCommonNode(List<Edge> eulerianCircuit, List<Edge> currentCircle) {
        Set<Node> eulerianNodes = new HashSet<>();
        for (Edge edge : eulerianCircuit) {
            eulerianNodes.add(edge.getNode0());
            eulerianNodes.add(edge.getNode1());
        }

        for (Edge edge : currentCircle) {
            if (eulerianNodes.contains(edge.getNode0())) {
                return edge.getNode0();
            }
            if (eulerianNodes.contains(edge.getNode1())) {
                return edge.getNode1();
            }
        }

        return null;
    }

    /**
     * Hierholzer Algorithmus
     *
     * @param inputGraph Graph
     * @return Map<Integer, List < Edge>> circles
     */
    public static Map<Integer, List<Edge>> hierholzer(Graph inputGraph) {
        if (!GraphTraversieren.traverseGraph(inputGraph, inputGraph.getNode(0).getId())) {
            System.out.println("Graph is not connected");
            return null;
        }
        if (!eachNodeHasEvenDegree(inputGraph)) {
            return null;
        }
        Graph newGraph = Graphs.clone(inputGraph);
        int circleCounter = 0;
        Map<Integer, List<Edge>> circles = new HashMap<>();
        Set<Edge> visited = new HashSet<>();
        circles.put(circleCounter, new ArrayList<>());
        Node startNode = newGraph.getNode(0);
        Node currNode = newGraph.getNode(0);
        Edge currEdge = currNode.leavingEdges().toList().get(0);
        visited.add(currEdge);
        while (newGraph.edges().toList().size() != 0) {
            if (currEdge.getOpposite(currNode) == startNode) {
                circles.get(circleCounter).add(currEdge);
                newGraph.removeEdge(currEdge.getIndex());
                for (Edge e : circles.get(circleCounter)) {
                    // Wenn der Knoten noch Kanten hat, die nicht besucht wurden
                    if (e.getNode0().getDegree() != 0) {
                        for (Edge edge : e.getNode0().edges().toList()) {
                            // Wenn die Kante noch nicht besucht wurde, setze den aktuellen Knoten und die Kante
                            if (!visited.contains(edge)) {
                                currNode = e.getNode0();
                                currEdge = edge;
                                visited.add(edge);
                            } else {
                                // Wenn die Kante bereits besucht wurde, fahre fort uns suche weiter
                                continue;
                            }
                            break;
                        }
                        break;
                    }
                    // Wenn der Knoten 0 keine Kanten mehr hat, überprüfe Knoten 1
                    if (e.getNode1().getDegree() != 0) {
                        for (Edge edge : e.getNode1().edges().toList()) {
                            if (!visited.contains(edge)) {
                                currNode = e.getNode1();
                                currEdge = edge;
                                visited.add(edge);
                            } else {
                                continue;
                            }
                            break;
                        }
                    }
                }
                if (visited.size() != inputGraph.edges().toList().size()) {
                    circleCounter++;
                    circles.put(circleCounter, new ArrayList<>());
                }
            } else {
                circles.get(circleCounter).add(currEdge);
                currNode = currEdge.getOpposite(currNode);
                newGraph.removeEdge(currEdge);
                if (!currNode.edges().toList().isEmpty()) {
                    if (!visited.contains(currNode.leavingEdges().toList().get(0))) {
                        currEdge = currNode.leavingEdges().toList().get(0);
                        visited.add(currEdge);
                    }
                }
            }
        }

        return circles;
    }

    /**
     * Erzeugt einen Eulerkreis aus einer Liste von Kreisen
     * @param circles Map<Integer, List < Edge>> circles
     * @return List<Edge> eulerianCircuit
     */
    public static List<Edge> buildEulerianCircuit(Map<Integer, List<Edge>> circles) {
        if (circles == null || circles.isEmpty()) {
            return null; // Kein Eulerkreis möglich
        }

        List<Edge> eulerianCircuit = new ArrayList<>();

        // Starten mit dem ersten Kreis
        List<Edge> firstCircle = circles.get(0);
        eulerianCircuit.addAll(firstCircle);

        for (int i = 1; i < circles.size(); i++) {
            List<Edge> currentCircle = circles.get(i);

            // Finde einen Knoten, der im aktuellen Eulerkreis und im neuen Kreis existiert
            Node commonNode = findCommonNode(eulerianCircuit, currentCircle);

            if (commonNode != null) {
                // Zerlege den Eulerkreis an der Stelle des gemeinsamen Knotens
                List<Edge> tempCircuit = new ArrayList<>();
                boolean inserted = false;
                for (Edge edge : eulerianCircuit) {
                    tempCircuit.add(edge);
                    if (!inserted && (edge.getNode0().equals(commonNode) || edge.getNode1().equals(commonNode))) {
                        // Füge den aktuellen Kreis in den Eulerkreis ein
                        tempCircuit.addAll(currentCircle);
                        inserted = true;
                    }
                }
                eulerianCircuit = tempCircuit;
            } else {
                // Falls kein gemeinsamer Knoten gefunden wurde, etwas stimmt nicht
                return null;
            }
        }

        return eulerianCircuit;
    }

}
