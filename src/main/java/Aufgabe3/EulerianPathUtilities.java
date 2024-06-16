package Aufgabe3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import static Aufgabe3.EulergraphAlgorithmen.dfs;

public class EulerianPathUtilities {

    public static boolean eachNodeHasEvenDegree(Graph graph) {
        for (Node node : graph) {
            if ((node.getDegree()) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isBridge(Node u, Node v, Graph graph) {
        // Temporär die Kante entfernen
        Edge edge = u.getEdgeBetween(v);
        graph.removeEdge(edge);

        boolean[] visited = new boolean[graph.getNodeCount()];
        int count1 = dfsCount(u, visited, graph);

        // Kante wieder hinzufügen
        graph.addEdge(edge.getId(), u.getId(), v.getId());

        visited = new boolean[graph.getNodeCount()];
        int count2 = dfsCount(u, visited, graph);

        return count1 > count2;
    }

    private static int dfsCount(Node node, boolean[] visited, Graph graph) {
        int nodeIndex = node.getIndex();
        visited[nodeIndex] = true;
        int count = 1;

        for (Edge edge : node.edges().toList()) {
            Node nextNode = edge.getOpposite(node);
            int nextNodeIndex = nextNode.getIndex();
            if (!visited[nextNodeIndex]) {
                count += dfsCount(nextNode, visited, graph);
            }
        }
        return count;
    }
}