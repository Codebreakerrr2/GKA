package Aufgabe3;

import Aufgabe1.GraphTraversieren;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static Aufgabe1.GraphLesen.readGraph;

public class EulergraphAlgorithmen {

    /**
     * Überprüft, ob das Entfernen einer Kante den Graphen trennt
     * @param graph Graph
     * @param edge Kante
     * @return boolean
     */
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

    /**
     * Überprüft, ob der Graph zusammenhängend ist
     * @param node Startknoten
     * @param visited Set<Node> besuchte Knoten
     * @return boolean
     */
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
     * Überprüft, ob jeder Knoten im Graphen einen geraden Grad hat
     * @param graph Graph
     * @return boolean
     */
    public static boolean eachNodeHasEvenDegree(Graph graph) {
        for (Node node : graph) {
            if ((node.getDegree()) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fleury Algorithmus
     * @param inputGraph Graph
     * @return Stack<Edge> circuits
     */

    public static Stack<Edge> fleury(Graph inputGraph) {
        if (!GraphTraversieren.traverseGraph(inputGraph, inputGraph.getNode(0).getId())) {
            System.out.println("Graph is not connected");
            return null;
        }
        if (!eachNodeHasEvenDegree(inputGraph)) {
            System.out.println("Graph does not have even degrees all over");
            return null;
        }
        Graph newGraph = Graphs.clone(inputGraph);
        Node currentNode = newGraph.getNode(0);
        Edge currentEdge;
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

    /**
     * Generates a random Eulerian graph with the given number of nodes and edges
     * @param nodeNumber Number of nodes
     * @param edgeNumber Number of edges
     * @param weightRange Maximum weight for an edge
     * @throws IOException If an I/O error occurs
     */

    public static Graph generateEulerianGraph(int nodeNumber, int edgeNumber, int weightRange) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber < nodeNumber - 1 || edgeNumber % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of edges for an Eulerian graph, must be even and at least " + (nodeNumber - 1));
        }
        Set<String> edges = new HashSet<>();
        Graph graph = new MultiGraph("Eulerian Graph");

        // Create all nodes first and add them to the Graph
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            graph.addNode(String.valueOf(i));
        }

        // Creates a cyclic structure first to ensure the graph has an even degree for all nodes
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange) + 1;
            String edge = nodes.get(i - 1) + " -- " + nodes.get(i);
            edges.add(edge);
            graph.addEdge(String.valueOf(i - 1) + "-" + i, String.valueOf(i - 1), String.valueOf(i)).setAttribute("ui.label", weight);
        }
        // Closing the cycle to ensure connected Graph
        String closingEdge = nodes.get(nodeNumber - 1) + " -- " + nodes.get(0);
        int closingWeight = (int) (Math.random() * weightRange) + 1;
        edges.add(closingEdge);
        graph.addEdge(String.valueOf(nodeNumber - 1) + "-" + 0, String.valueOf(nodeNumber - 1), String.valueOf(0)).setAttribute("ui.label", closingWeight);

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
                graph.addEdge(node1 + "-" + node2, String.valueOf(node1), String.valueOf(node2)).setAttribute("ui.label", weight);
                graph.addEdge(node2 + "-" + node1, String.valueOf(node2), String.valueOf(node1)).setAttribute("ui.label", weight);
                // Ensure we don't exceed edge count
            }
        }
        return graph;
    }

    public static Graph generateEulerianGraphWithFileOutput(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber < nodeNumber - 1 || edgeNumber % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of edges for an Eulerian graph, must be even and at least " + (nodeNumber - 1));
        }
        Set<String> edges = new HashSet<>();
        Graph graph = new MultiGraph("Eulerian Graph");
        FileWriter fileWriter = new FileWriter(path);

        // Create all nodes first and add them to the file
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            fileWriter.append(i + ";\n");
            graph.addNode(String.valueOf(i));
        }

        // Creates a cyclic structure first to ensure the graph has an even degree for all nodes
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange) + 1;
            String edge = nodes.get(i - 1) + " -- " + nodes.get(i);
            edges.add(edge);
            fileWriter.append(edge + " : " + weight + ";\n");
            graph.addEdge(String.valueOf(i - 1) + "-" + i, String.valueOf(i - 1), String.valueOf(i)).setAttribute("ui.label", weight);
        }
        // Closing the cycle to ensure connected Graph
        String closingEdge = nodes.get(nodeNumber - 1) + " -- " + nodes.get(0);
        int closingWeight = (int) (Math.random() * weightRange) + 1;
        edges.add(closingEdge);
        fileWriter.append(closingEdge + " : " + closingWeight + ";\n");
        graph.addEdge(String.valueOf(nodeNumber - 1) + "-" + 0, String.valueOf(nodeNumber - 1), String.valueOf(0)).setAttribute("ui.label", closingWeight);

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
                graph.addEdge(node1 + "-" + node2, String.valueOf(node1), String.valueOf(node2)).setAttribute("ui.label", weight);
                graph.addEdge(node2 + "-" + node1, String.valueOf(node2), String.valueOf(node1)).setAttribute("ui.label", weight);
                // Ensure we don't exceed edge count
            }
        }
        fileWriter.close();
        return graph;
    }

    public static List<Edge> hierholzer(Graph inputGraph) throws DisconnectedGraphException, OddNodeDegreeException {
        // Any node can be the start node
        Node current = inputGraph.getNode(0);
        // Check if the graph is connected
        if (!GraphTraversieren.traverseGraph(inputGraph, current.getId())) {
            throw new DisconnectedGraphException("Graph is not connected");
        }
        // Check if all nodes have even degree
        if (!eachNodeHasEvenDegree(inputGraph)) {
            throw new OddNodeDegreeException("Graph does not have even degrees all over");
        }
        // A stack to store the nodes
        Stack<Node> stack = new Stack<>();

        // A list to store the circuit
        List<Edge> circuit = new ArrayList<>();

        // Add the startNode to the stack
        stack.push(current);

        // While the stack is not empty
        while (!stack.isEmpty()) {
            // Get the current node from the stack
            current = stack.peek();

            // Get all edges of the current node
            Iterator<Edge> edges = current.leavingEdges().iterator();

            // Iterate over all edges of the current node
            while (edges.hasNext()) {
                Edge edge = edges.next();
                // If the edge has not been visited, mark it as visited and add the opposite node to the stack
                if (!edge.hasAttribute("visited")) {
                    edge.setAttribute("visited", true);
                    stack.push(edge.getOpposite(current));
                    break;
                }
            }

            // If no more edges are unvisited, pop the node from the stack and add the edge to the circuit
            if (current == stack.peek()) {
                stack.pop();
                // Add the edge to the circuit if the stack is not empty
                if (!stack.isEmpty()) {
                    circuit.add(current.getEdgeBetween(stack.peek()));
                }
            }
        }
        Collections.reverse(circuit);
        return circuit.isEmpty() ? null : circuit;
    }

    public static void main(String[] args) throws IOException {
        Graph graph = new MultiGraph("Eulerian Graph");
        for (int i = 1; i <= 13; i++) {
            graph.addNode(String.valueOf(i));
            graph.getNode(String.valueOf(i)).setAttribute("ui.label", i);
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "1 - 2 (a)");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "2 - 3 (b)");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "3 - 4 (c)");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "4 - 1 (d)");

        graph.addEdge("e", "4", "5").setAttribute("ui.label", "4 - 5 (e)");
        graph.addEdge("f", "5", "6").setAttribute("ui.label", "5 - 6 (f)");
        graph.addEdge("g", "6", "7").setAttribute("ui.label", "6 - 7 (g)");
        graph.addEdge("h", "7", "4").setAttribute("ui.label", "7 - 4 (h)");

        graph.addEdge("i", "7", "8").setAttribute("ui.label", "7 - 8 (i)");
        graph.addEdge("j", "8", "9").setAttribute("ui.label", "8 - 9 (j)");
        graph.addEdge("k", "9", "10").setAttribute("ui.label", "9 - 10 (k)");
        graph.addEdge("l", "10", "7").setAttribute("ui.label", "10 - 7 (l)");

        graph.addEdge("m", "7", "11").setAttribute("ui.label", "7 - 11 (m)");
        graph.addEdge("n", "11", "12").setAttribute("ui.label", "11 - 12 (n)");
        graph.addEdge("o", "12", "13").setAttribute("ui.label", "12 - 13 (o)");
        graph.addEdge("p", "13", "7").setAttribute("ui.label", "13 - 7 (p)");
        //EulergraphAlgorithmen.generateEulerianGraphWithFileOutput(1000, 4000, 10, "src/main/java/Aufgabe3/generatedGraphs/graph.txt");
        //Graph graph = readGraph("src/main/java/Aufgabe3/generatedGraphs/graph.txt");
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
    }
}