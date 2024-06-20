package Aufgabe3;

import Aufgabe1.GraphTraversieren;
import Aufgabe3.DisconnectedGraphException;
import Aufgabe3.OddNodeDegreeException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

import static Aufgabe3.EulergraphAlgorithmen.eachNodeHasEvenDegree;

public class Hierholzer {
    Graph inputGraph;
    Node currNode;
    Node startNode;
    Edge currEdge;
    Set<Edge> visitedEdges;
    Set<Node> visitedNodes;
    Map<Integer, List<Edge>> circles;
    List<Edge> currentCircle;

    public Hierholzer(Graph inputGraph) {
        this.inputGraph = inputGraph;
        this.visitedEdges = new HashSet<>();
        this.circles = new HashMap<>();
        this.currentCircle = new ArrayList<>();
        this.visitedNodes = new HashSet<>();
    }

    public Map<Integer, List<Edge>> hierholzer(Graph inputGraph) throws DisconnectedGraphException, OddNodeDegreeException {
        if (!GraphTraversieren.traverseGraph(inputGraph, inputGraph.getNode(0).getId())) {
            throw new DisconnectedGraphException("Der Graph ist nicht zusammenhängend.");
        }
        if (!eachNodeHasEvenDegree(inputGraph)) {
            throw new OddNodeDegreeException("Der Graph hat mindestens einen Knoten mit ungeradem Grad.");
        }

        int circleCounter = 0;
        this.circles.put(circleCounter, new ArrayList<>());
        this.currNode = inputGraph.getNode(0);
        this.visitedNodes.add(currNode);

        while (visitedEdges.size() < inputGraph.edges().toList().size()) {
            this.currEdge = findUnvisitedEdge(this.currNode, this.visitedEdges);
            if (currEdge != null) {
                this.circles.get(circleCounter).add(currEdge);
                this.visitedEdges.add(currEdge);
                this.currNode = currEdge.getOpposite(this.currNode);
                this.visitedNodes.add(this.currNode);
            } else {
                // No unvisited edges found, start a new cycle
                Edge unvisitedEdge = findUnvisitedGlobalEdge(this.circles, this.visitedEdges);
                if (unvisitedEdge == null) break; // No unvisited edges left, end process
                this.currEdge = unvisitedEdge;
                this.visitedEdges.add(this.currEdge);
                this.currentCircle = new ArrayList<>();
                this.circles.put(++circleCounter, this.currentCircle);
                this.currentCircle.add(this.currEdge);
                this.currNode = this.currEdge.getOpposite(this.currNode);
                this.visitedNodes.add(this.currNode);
            }
        }

        return this.circles;
    }

    private static Edge findUnvisitedEdge(Node node, Set<Edge> visited) {
        for (Edge edge : node.edges().toList()) {
            if (!visited.contains(edge)) {
                return edge;
            }
        }
        return null;
    }

    private Edge findUnvisitedGlobalEdge(Map<Integer, List<Edge>> map, Set<Edge> visited) {
        for (Map.Entry<Integer, List<Edge>> entry : map.entrySet()) {
            for (Edge edge : entry.getValue()) {
                for (Edge e : edge.getNode0().edges().toList()) {
                    if (!visited.contains(e)) {
                        if (visitedNodes.contains(e.getNode0())) {
                            this.currNode = e.getNode0();
                            return e;
                        }
                        if (visitedNodes.contains(e.getNode1())) {
                            this.currNode = e.getNode1();
                            return e;
                        }
                    }
                }
                for (Edge e : edge.getNode1().edges().toList()) {
                    if (!visited.contains(e)) {
                        if (visitedNodes.contains(e.getNode0())) {
                            this.currNode = e.getNode0();
                            return e;
                        }
                        if (visitedNodes.contains(e.getNode1())) {
                            this.currNode = e.getNode1();
                            return e;
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<Edge> buildEulerianCircuit(Map<Integer, List<Edge>> circles) {
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

    private Node findCommonNode(List<Edge> eulerianCircuit, List<Edge> currentCircle) {
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
}
