package Aufgabe1;

import com.google.common.base.Preconditions;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.*;

public class GraphTraversieren {
/*    public static HashSet<Node> traverseGraph(Graph graph, String startNode) {
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
    }*/

    /**
     * Eine Methode, um den kürzesten Pfad von einer Node zu einer anderen Node zu finden
     * @param graph der Graph, der traversiert werden soll
     * @param startNodeId die ID der Startnode
     * @param endNodeId die ID der Endnode
     * @return ein Pair, bestehend aus einer Liste von Nodes, die den Pfad darstellen und der Länge des Pfades (Kantenlänge)
     */
    public static Pair<List<Node>, Integer> shortestPath(Graph graph, String startNodeId, String endNodeId) {
        Preconditions.checkNotNull(endNodeId);
        Preconditions.checkNotNull(startNodeId);
        Node startNode = graph.getNode(startNodeId);
        Node endNode = graph.getNode(endNodeId);
        //Initialisieren der Queue
        Queue<Node> toVisit = new LinkedList<>();
        //Speichert die Vorgänger der Nodes
        HashMap<Node, Node> predecessors = new HashMap<>();
        //Speichert die Distanzen der Nodes zur Startnode
        HashMap<Node, Integer> distances = new HashMap<>();
        //Startnode in die Queue einfügen
        toVisit.add(startNode);
        //Startnode hat Distanz 0
        distances.put(startNode, 0);
        //solange die Queue nicht leer ist
        while (!toVisit.isEmpty()) {
            //Kopf der Queue holen und entfernen
            Node currentNode = toVisit.poll();
            //Wenn die aktuelle Node die Endnode ist, breche ab
            if (currentNode.equals(endNode)) {
                break;
            }

            currentNode.leavingEdges().forEach((Edge outgoingEdge)->{
                Node targetNode= outgoingEdge.getTargetNode();
                //Wenn der Targetnode noch nicht besucht wurde
                if (!distances.containsKey(targetNode)) {
                    toVisit.add(targetNode);
                    predecessors.put(targetNode, currentNode);
                    distances.put(targetNode, distances.get(currentNode) + 1);
                }
            });
        }
        List<Node> path = new ArrayList<>();
        //Aktuelle Node Endnode setzen und solange der Vorgänger der aktuellen Node nicht null ist
        Node currentNode = endNode;
        while (currentNode != null) {
            //Fügt die aktuelle Node an den Kopf der Liste hinzu
            path.add(0, currentNode);
            //Setzt die aktuelle Node auf den Vorgänger der aktuellen Node
            currentNode = predecessors.get(currentNode);
        }
        //Ein Pair mit Path zur endnode und die Distanz von start zur endnode
        return new Pair<>(path, distances.get(endNode));
    }
}
