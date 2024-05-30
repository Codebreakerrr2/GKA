package Aufgabe2;

import Aufgabe1.GraphLesen;
import Aufgabe1.Pair;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Aufgabe2.GraphGenerator.generateUndirectedWeightedGraph;

/**
 * Class to implement Prim's and Kruskal's algorithms to find the minimum spanning tree of a graph
 */
public class MstAlgorithmen {
    /**
     * Prim's algorithm to find the minimum spanning tree of a graph
     * @param graph The graph to find the minimum spanning tree of
     * @return The minimum spanning tree of the graph
     */
    public static Pair<Graph, Integer> prim(Graph graph) {
        if (graph == null || graph.getNodeCount() == 0) {
            throw new IllegalArgumentException("Graph is null or empty");
        }
        Graph mst = new MultiGraph("MSTP");
        PriorityQueue<Edge> pqEdges = new PriorityQueue<>((Comparator.comparingDouble(edge -> (double) edge.getAttribute("Gewicht"))));
        Map<String, Boolean> visited = new HashMap<>();

        // Start with the first node of the graph
        Iterator<Node> nodeIterator = graph.nodes().iterator();

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
           if (!isNode2Visited) {
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
        Integer weight = getWeight(mst);
        return new Pair<>(mst, weight);
    }

    /**
     * Get the weight of a graph
     * @param graph The graph to get the weight of
     * @return The weight of the graph
     */
    public static int getWeight(Graph graph) {
        int weight = 0;
        for (Edge e: graph.edges().toList()) {
            Pattern pattern = Pattern.compile("\\((\\d+(\\.\\d+)?)\\)");
            Matcher matcher = pattern.matcher(e.getAttribute("ui.label").toString());
            if (matcher.find()) {
                weight += (int) Double.parseDouble(matcher.group(1));
            }
        }
        return weight;
    }

    /**
     * Kruskal's algorithm to find the minimum spanning tree of a graph
     * @param graph The graph to find the minimum spanning tree of
     * @return The minimum spanning tree of the graph
     */

    public static Pair<Graph, Integer> kruskal(Graph graph){
        if (graph == null) throw new IllegalArgumentException("Graph is null");
        Graph mst = new MultiGraph("MSTK");
        PriorityQueue<Edge> pqEdges = new PriorityQueue<>((Comparator.comparingDouble(edge -> (double) edge.getAttribute("Gewicht"))));
        graph.edges().forEach(pqEdges::add);

        // Create a map to track the parent of each node
        // Initially, each node is its own parent
        // beacause each node is a tree
        Map<String, String> parent = new HashMap<>();
        for (Node node : graph) {
            parent.put(node.getId(), node.getId());
        }
        while (!pqEdges.isEmpty()) {
            Edge e = pqEdges.poll();
            // Find the root of the trees that the two end nodes of the edge belong to
            String root1 = find(e.getNode0().getId(), parent);
            String root2 = find(e.getNode1().getId(), parent);

            // If the two end nodes of the edge are in different trees, add the edge to the MST
            if (!root1.equals(root2)) {
                // Ensure nodes exist in the graph before adding the edge
                if (mst.getNode(e.getNode0().getId()) == null) {
                    mst.addNode(e.getNode0().getId());
                }
                // Ensure nodes exist in the graph before adding the edge
                if (mst.getNode(e.getNode1().getId()) == null) {
                    mst.addNode(e.getNode1().getId());
                }
                // Add the edge to the MST
                mst.addEdge(e.getId(), e.getNode0().getId(), e.getNode1().getId());
                //style stuff
                mst.getEdge(e.getId()).setAttribute("Gewicht");
                mst.getEdge(e.getId()).setAttribute("ui.label",e.getId() +" (" + e.getAttribute("Gewicht") + ")");
                // Merge the two trees into one and make root2 the parent of root1
                union(root1, root2, parent);
            }
        }
        mst.setAttribute("ui.stylesheet", "edge { text-mode: normal; text-color: red;}");
        mst.nodes().forEach((Node node) -> {
            node.setAttribute("Name", node.getId());
            node.setAttribute("ui.label", "Nodename: " + node.getAttribute("Name"));
        });
        Integer weight = getWeight(mst);
        return new Pair<>(mst, weight);
    }

    // Find the root of the tree that the node belongs to
    private static String find(String node, Map<String, String> parent) {
        if (!parent.get(node).equals(node)) {
            //path compression
            parent.put(node, find(parent.get(node), parent));
        }
        return parent.get(node);
    }
    // Merge two trees into one
    private static void union(String root1, String root2, Map<String, String> parent) {
        parent.put(root1, root2);
    }

    public static void main(String[] args) throws IOException {
        //generateUndirectedWeightesGraphs(20, 190, 30, "src/main/java/Aufgabe2/generatedGraphs/testGraph1.gka");
        //generateUndirectedWeightesGraphs(20, 190, 30, "src/main/java/Aufgabe2/generatedGraphs/testGraph2.gka");
        //generateUndirectedWeightesGraphs(20, 190, 30, "src/main/java/Aufgabe2/generatedGraphs/testGraph3.gka");
        //generateUndirectedWeightesGraphs(20, 190, 30, "src/main/java/Aufgabe2/generatedGraphs/newGraph");


        //generateUndirectedWeightedGraph(20, 190, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph1");
        //generateUndirectedWeightedGraph(40, 780, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph2");
        //generateUndirectedWeightedGraph(80, 3160, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph3");
        //generateUndirectedWeightedGraph(160, 12720, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph4");
        //generateUndirectedWeightedGraph(320, 51040, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph5");
        // kursal ca 0.8 s und prim ca 1.5 s
        //generateUndirectedWeightedGraph(640, 204480, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph6");
        // kursal ca 1.090133901 s s und prim ca 8.1 s
        //generateUndirectedWeightedGraph(1280, 818560, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph7");
        // kruskal ca 1.1220631 s bis 1.1635982 s
        //generateUndirectedWeightedGraph(2560, 818560, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph8");
        // kruskal ca 21.6123905 bis 23
        //generateUndirectedWeightedGraph(5120, 13104640, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph9");
        // Out of memory error
        //generateUndirectedWeightedGraph(10240, 52423680, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph10");
        //generateUndirectedWeightedGraph(20480, 209704960, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph11");
        //generateUndirectedWeightedGraph(25000000, 25000000, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph12");

        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph7");
        //int weight = kruskal(graph).second;
        long startTime1 = System.nanoTime();
        //generateUndirectedWeightedGraph(40000000, 40000000, 30, "src/main/java/Aufgabe2/generatedGraphs/laufzeitGraph40x40Mio");
        Graph mst = kruskal(graph).first;
        //System.out.println("Kruskal; " + kruskal(graph).second);
        //System.out.println("Prim; " + prim(graph).second);
        long endTime1 = System.nanoTime();

        double duration = (double) (endTime1 - startTime1) / 1_000_000_000;  // convert to seconds
        //System.out.println(weight);
        System.out.println("Execution time kruskal: " + duration + " s");

        long startTime2 = System.nanoTime();
        //mst = prim(graph).first;
        //System.out.println("Kruskal; " + kruskal(graph).second);
        //System.out.println("Prim; " + prim(graph).second);
        long endTime2 = System.nanoTime();

        duration = (double) (endTime2 - startTime2) / 1_000_000_000;  // convert to seconds
        //System.out.println(weight);
        System.out.println("Execution time prim: " + duration + " s");
        //System.setProperty("org.graphstream.ui", "swing");
        //graph.display();
    }

}
