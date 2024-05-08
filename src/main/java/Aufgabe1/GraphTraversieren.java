package Aufgabe1;

import com.google.common.base.Preconditions;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

public class GraphTraversieren {
    public static HashSet<Node> traverseGraph(Graph graph, String startNode) {
        Node node = graph.getNode(startNode);
        if (node == null) {
            throw new IllegalArgumentException("Node not found");
        }
        Queue<Node> toVisit = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();
        toVisit.add(node);
        visited.add(node);
        while (!toVisit.isEmpty()) {
            Node currentNode = toVisit.poll();
            for (Node neighbor : currentNode.neighborNodes().collect(Collectors.toSet())) {
                System.out.println("Current Node: " +currentNode + "\nNeighbors of current Node: " + currentNode.neighborNodes().toList() + "\nVisited Nodes: " + visited + "\n");
                if (!visited.contains(neighbor)) {
                    toVisit.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return visited;
    }

    public static Pair<List<Node>, Integer> shortestPath(Graph graph, String startNodeId, String endNodeId) {
        Preconditions.checkNotNull(endNodeId);
        Preconditions.checkNotNull(startNodeId);
        Node startNode = graph.getNode(startNodeId);
        Node endNode = graph.getNode(endNodeId);


        Queue<Node> toVisit = new LinkedList<>();
        HashMap<Node, Node> predecessors = new HashMap<>();
        HashMap<Node, Integer> distances = new HashMap<>();

        toVisit.add(startNode);
        distances.put(startNode, 0);

        while (!toVisit.isEmpty()) {
            Node currentNode = toVisit.poll();

            if (currentNode.equals(endNode)) {
                break;
            }
            currentNode.leavingEdges().forEach((Edge outgoingEdge)->{
                Node targetNode= outgoingEdge.getTargetNode();
                if (!distances.containsKey(targetNode)) {
                    toVisit.add(targetNode);
                    predecessors.put(targetNode, currentNode);
                    distances.put(targetNode, distances.get(currentNode) + 1);
                }
            });
        }

        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(0, currentNode);
            currentNode = predecessors.get(currentNode);
        }
        return new Pair<>(path, distances.get(endNode));
    }

    public static void main(String[] args) {
        Graph graph = GraphLesen.readGraph("C:\\Users\\Usman\\Documents\\Java Files\\GraphPraktikum\\branchAndre\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph04.gka");
        Pair<List<Node>, Integer> pair = shortestPath(graph, "v2", "v7");
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
        System.out.println(pair.second);
        System.out.println(pair.first);
    }
}
