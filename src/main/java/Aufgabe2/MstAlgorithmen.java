package Aufgabe2;

import Aufgabe1.GraphLesen;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static Aufgabe2.GraphGenerator.generateUndirectedWeightesGraphs;

public class MstAlgorithmen {
    public static Graph kruskal(Graph graph){
        if (graph == null) throw new IllegalArgumentException("Graph is null");
        Graph mst = new MultiGraph("MSTK");
        PriorityQueue<Edge> pqEdges = new PriorityQueue<>((Comparator.comparingDouble(edge -> (double) edge.getAttribute("Gewicht"))));
        graph.edges().forEach(pqEdges::add);

        // Create a map to track the parent of each node
        Map<String, String> parent = new HashMap<>();
        for (Node node : graph) {
            parent.put(node.getId(), node.getId());
        }

        while (!pqEdges.isEmpty()) {
            Edge e = pqEdges.poll();
            String root1 = find(e.getNode0().getId(), parent);
            String root2 = find(e.getNode1().getId(), parent);

            // If the two end nodes of the edge are in different trees, add the edge to the MST
            if (!root1.equals(root2)) {
                // Ensure nodes exist in the graph before adding the edge
                if (mst.getNode(e.getNode0().getId()) == null) {
                    mst.addNode(e.getNode0().getId());
                }
                if (mst.getNode(e.getNode1().getId()) == null) {
                    mst.addNode(e.getNode1().getId());
                }
                mst.addEdge(e.getId(), e.getNode0().getId(), e.getNode1().getId());
                union(root1, root2, parent);
            }
        }
        return mst;
    }

    // Find the root of the tree that the node belongs to
    private static String find(String node, Map<String, String> parent) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node), parent));
        }
        return parent.get(node);
    }

    // Merge two trees into one
    private static void union(String root1, String root2, Map<String, String> parent) {
        parent.put(root1, root2);
    }

    public static void main(String[] args) throws IOException {
      generateUndirectedWeightesGraphs(10, 45, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/newGraph");
        Graph mst = kruskal(graph);
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
        //graph.display();
    }

}
