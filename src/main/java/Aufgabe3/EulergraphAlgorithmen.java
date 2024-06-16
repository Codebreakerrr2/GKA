package Aufgabe3;

import Aufgabe1.Pair;
import Aufgabe2.GraphGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static Aufgabe1.GraphLesen.readGraph;
import static Aufgabe3.EulerianPathUtilities.eachNodeHasEvenDegree;
import static Aufgabe3.EulerianPathUtilities.isBridge;

public class EulergraphAlgorithmen {

    public static boolean disconnectsGraph(Graph graph, Edge edge) {
        Node node0 = edge.getNode0();
        Node node1 = edge.getNode1();

        // Temporarily remove the edge
        graph.removeEdge(edge);

        // Use DFS to check connectivity
        Set<Node> visited = new HashSet<>();
        dfs(node0, visited);

        // Add the edge back to the graph
        graph.addEdge(edge.getId(), node0.getId(), node1.getId());

        // If node1 is not visited, removing the edge disconnects the graph
        return !visited.contains(node1);
    }

    public static void dfs(Node node, Set<Node> visited) {
        visited.add(node);
        for (Edge edge : node.edges().toList()) {
            Node oppositeNode = edge.getOpposite(node);
            if (!visited.contains(oppositeNode)) {
                dfs(oppositeNode, visited);
            }
        }
    }

    /**
     * Hierholzer Algorithmus
     *
     * @param graph Graph
     * @return Map<Integer, List < Edge>> circles
     */
    public static Map<Integer, List<Edge>> hierholzer(Graph graph) {
        Graph copy = Graphs.clone(graph);
        if (!eachNodeHasEvenDegree(graph)) {
            return null;
        }
        int circleCounter = 0;
        Map<Integer, List<Edge>> circles = new HashMap<>();
        Set<Edge> visited = new HashSet<>();
        circles.put(circleCounter, new ArrayList<>());
        Node startNode = copy.getNode(0);
        Node currNode = copy.getNode(0);
        Edge currEdge = currNode.leavingEdges().toList().get(0);
        visited.add(currEdge);
        while (copy.edges().toList().size() != 0) {
            if (currEdge.getOpposite(currNode) == startNode) {
                circles.get(circleCounter).add(currEdge);
                copy.removeEdge(currEdge.getIndex());
                for (Edge e : circles.get(circleCounter)) {
                    if (e.getNode0().getDegree() != 0) {
                        for (Edge edge : e.getNode0().edges().toList()) {
                            if (!visited.contains(edge)) {
                                currNode = e.getNode0();
                                currEdge = edge;
                                visited.add(edge);
                            } else {
                                continue;
                            }
                            break;
                        }
                        break;
                    }
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
                if (visited.size() != graph.edges().toList().size()) {
                    circleCounter++;
                    circles.put(circleCounter, new ArrayList<>());
                }
            } else {
                circles.get(circleCounter).add(currEdge);
                currNode = currEdge.getOpposite(currNode);
                copy.removeEdge(currEdge);
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

    public static Stack<Edge> fleury(Graph inputGraph) {
        if (!eachNodeHasEvenDegree(inputGraph)) {
            System.out.println("Graph does not have even degrees all over");
            return null;
        }

        Graph newGraph = Graphs.clone(inputGraph);
        Node currentNode = newGraph.getNode(0);
        Edge currentEdge = null;
        Stack<Edge> circuits = new Stack<>();

        while (newGraph.edges().toList().size() != 0) {
            List<Edge> edges = currentNode.edges().toList();
            boolean edgeFound = false;

            for (Edge e : edges) {
                if (!disconnectsGraph(newGraph, e)) {
                    currentEdge = e;
                    circuits.push(currentEdge);
                    newGraph.removeEdge(currentEdge.getId());
                    currentNode = currentEdge.getOpposite(currentNode);
                    edgeFound = true;
                    break;
                }
            }

            if (!edgeFound && !edges.isEmpty()) {
                // Wenn keine nicht trennende Kante gefunden wurde, wähle die erste verbleibende Kante
                currentEdge = edges.get(0);
                circuits.push(currentEdge);
                newGraph.removeEdge(currentEdge.getId());
                currentNode = currentEdge.getOpposite(currentNode);
            }
        }

        return circuits;
    }

    public static void generateEulerianGraph(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber < nodeNumber - 1 || edgeNumber % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of edges for an Eulerian graph, must be even and at least " + (nodeNumber - 1));
        }
        Set<String> edges = new HashSet<>();
        FileWriter fileWriter = new FileWriter(path);

        // Create all nodes first and add them to the file
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            fileWriter.append(i + ";\n");
        }

        // Create a cyclic structure first to ensure the graph has an even degree for all nodes
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange) + 1;
            String edge = nodes.get(i - 1) + " -- " + nodes.get(i);
            edges.add(edge);
            fileWriter.append(edge + " : " + weight + ";\n");
        }
        // Closing the cycle
        String closingEdge = nodes.get(nodeNumber - 1) + " -- " + nodes.get(0);
        int closingWeight = (int) (Math.random() * weightRange) + 1;
        edges.add(closingEdge);
        fileWriter.append(closingEdge + " : " + closingWeight + ";\n");

        // Add additional edges in pairs to maintain even degree
        while (edges.size() < edgeNumber) {
            int node1 = nodes.get((int) (Math.random() * nodes.size()));
            int node2 = nodes.get((int) (Math.random() * nodes.size()));
            if (node1 == node2) continue;
            String edge = node1 + " -- " + node2;
            String reverseEdge = node2 + " -- " + node1;
            if (!edges.contains(edge) && !edges.contains(reverseEdge)) {
                int weight = (int) (Math.random() * weightRange) + 1;
                edges.add(edge);
                edges.add(reverseEdge); // Add reverse pair to ensure even degree
                fileWriter.append(edge + " : " + weight + ";\n");
                fileWriter.append(reverseEdge + " : " + weight + ";\n");
                // Ensure we don't exceed edge count
                if (edges.size() >= edgeNumber) break;
            }
        }

        fileWriter.close();
    }

    public static void generateSimpleEulerianGraph(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber < nodeNumber || edgeNumber > nodeNumber * (nodeNumber - 1) / 2) {
            throw new IllegalArgumentException("Invalid number of edges for a simple Eulerian graph, minimum is " + nodeNumber + ", maximum is " + (nodeNumber * (nodeNumber - 1) / 2));
        }
        if (edgeNumber % 2 != 0) {
            throw new IllegalArgumentException("Number of edges must be even for an Eulerian graph");
        }

        Set<String> edges = new HashSet<>();
        FileWriter fileWriter = new FileWriter(path);

        // Create all nodes first and add them to the file
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            fileWriter.append(i + ";\n");
        }

        // Create a cyclic structure first to ensure the graph has an even degree for all nodes
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange) + 1;
            String edge = nodes.get(i - 1) + " -- " + nodes.get(i);
            edges.add(edge);
            fileWriter.append(edge + " : " + weight + ";\n");
        }
        // Closing the cycle
        String closingEdge = nodes.get(nodeNumber - 1) + " -- " + nodes.get(0);
        int closingWeight = (int) (Math.random() * weightRange) + 1;
        edges.add(closingEdge);
        fileWriter.append(closingEdge + " : " + closingWeight + ";\n");

        // Add additional edges in pairs to maintain even degree and ensure simplicity (no multiple edges)
        while (edges.size() < edgeNumber) {
            int node1 = nodes.get((int) (Math.random() * nodes.size()));
            int node2 = nodes.get((int) (Math.random() * nodes.size()));
            if (node1 == node2 || node1 == (node2 + 1) % nodeNumber || node2 == (node1 + 1) % nodeNumber) continue;
            String edge = node1 + " -- " + node2;
            String reverseEdge = node2 + " -- " + node1;
            if (!edges.contains(edge) && !edges.contains(reverseEdge)) {
                int weight = (int) (Math.random() * weightRange) + 1;
                edges.add(edge);
                fileWriter.append(edge + " : " + weight + ";\n");
                // Ensure we don't exceed edge count
                if (edges.size() >= edgeNumber) break;
            }
        }

        fileWriter.close();
    }

    public static void main(String args[]) throws IOException {
        System.setProperty("org.graphstream.ui", "swing");
        generateEulerianGraph(5,10 , 10, "src/main/java/Aufgabe3/eulerianGraph.txt");
        for (int i = 0; i < 100; i++){
            //generateSimpleEulerianGraph(8, 8, 10, "src/main/java/Aufgabe3/simpleEulerianGraph.txt");
            Graph multiGraph = readGraph("src/main/java/Aufgabe3/eulerianGraph.txt");
            Graph simpleGraph = readGraph("src/main/java/Aufgabe3/simpleEulerianGraph.txt");


            // Verwenden eines MultiGraph anstelle eines SingleGraph
            System.out.println(buildEulerianCircuit(hierholzer(multiGraph)));
            System.out.println(fleury(multiGraph));
        }
        //multiGraph.display();
    }
}