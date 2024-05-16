package Aufgabe2;

import Aufgabe1.GraphLesen;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;
import java.util.*;

import static Aufgabe2.GraphGenerator.generateUndirectedWeightesGraphs;

/**
 * Class to implement Prim's and Kruskal's algorithms to find the minimum spanning tree of a graph
 */
public class MstAlgorithmen {
    /**
     * Prim's algorithm to find the minimum spanning tree of a graph
     * @param graph The graph to find the minimum spanning tree of
     * @return The minimum spanning tree of the graph
     */
    public static Graph prim(Graph graph) {
        if (graph == null || graph.getNodeCount() == 0) {
            throw new IllegalArgumentException("Graph is null or empty");
        }
        Graph mst = new MultiGraph("MSTP");
        PriorityQueue<Edge> pqEdges = new PriorityQueue<>((Comparator.comparingDouble(edge -> (double) edge.getAttribute("Gewicht"))));
        Map<String, Boolean> visited = new HashMap<>();

        // Start with the first node of the graph
        Iterator<Node> nodeIterator = graph.nodes().iterator();
        if (!nodeIterator.hasNext()) {
            throw new IllegalArgumentException("Graph has no nodes");
        }

        Node firstNode = nodeIterator.next();
        visited.put(firstNode.getId(), true);

        // Add all edges of the first node to the priority queue
        pqEdges.addAll(firstNode.edges().toList());

        while (!pqEdges.isEmpty()) {
            // Get the edge with the smallest weight
            Edge e = pqEdges.poll();

            // Get the two nodes of the edge
            Node node1 = e.getNode0();
            Node node2 = e.getNode1();

            // Check if the nodes have been visited
            boolean isNode1Visited = visited.getOrDefault(node1.getId(), false);
            boolean isNode2Visited = visited.getOrDefault(node2.getId(), false);

            // If both nodes have been visited, skip this edge
            if (isNode1Visited && isNode2Visited) {
                continue;
            }

            // Ensure nodes exist in the graph before adding the edge
            if (mst.getNode(node1.getId()) == null) {
                mst.addNode(node1.getId());
            }
            if (mst.getNode(node2.getId()) == null) {
                mst.addNode(node2.getId());
            }

            // Add the edge to the MST
            mst.addEdge(e.getId(), node1.getId(), node2.getId());

            //style stuff
            mst.getEdge(e.getId()).setAttribute("Gewicht");
            mst.getEdge(e.getId()).setAttribute("ui.label",e.getId() +" (" + e.getAttribute("Gewicht") + ")");

            // Mark the nodes as visited
            visited.put(node1.getId(), true);
            visited.put(node2.getId(), true);
           //Add all edges of the nodes to the priority queue if the nodes haven't been visited yet
           if (!isNode1Visited) {
               for (Edge e1: node1.edges().toList()) {
                   if (!pqEdges.contains(e1))pqEdges.add(e1);
               }
           }
           if (visited.get(node2.getId())) {
               for (Edge e2: node2.edges().toList()) {
                   if (!pqEdges.contains(e2))pqEdges.add(e2);
               }
           }
        }
        mst.nodes().forEach((Node node) -> {
            node.setAttribute("Name", node.getId());
            node.setAttribute("ui.label", "Nodename: " + node.getAttribute("Name"));
        });
        mst.setAttribute("ui.stylesheet", "edge { text-mode: normal; text-color: red;}");
        return mst;
    }

    /**
     * Kruskal's algorithm to find the minimum spanning tree of a graph
     * @param graph The graph to find the minimum spanning tree of
     * @return The minimum spanning tree of the graph
     */

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
                mst.getEdge(e.getId()).setAttribute("Gewicht");
                mst.getEdge(e.getId()).setAttribute("ui.label",e.getId() +" (" + e.getAttribute("Gewicht") + ")");
                union(root1, root2, parent);
            }
        }
        mst.setAttribute("ui.stylesheet", "edge { text-mode: normal; text-color: red;}");
        mst.nodes().forEach((Node node) -> {
            node.setAttribute("Name", node.getId());
            node.setAttribute("ui.label", "Nodename: " + node.getAttribute("Name"));
        });
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
        //generateUndirectedWeightesGraphs(5, 9, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/newGraph");
        Graph mst = kruskal(graph);
        System.setProperty("org.graphstream.ui", "swing");
        mst.display();
        //graph.display();
    }

}
