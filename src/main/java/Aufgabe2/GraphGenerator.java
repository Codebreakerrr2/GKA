package Aufgabe2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphGenerator {
    /**
     * Generates a random undirected graph with the given number of nodes and edges and writes it to the given file.
     * @param nodeNumber Number of nodes
     * @param edgeNumber Number of edges
     * @param weightRange Maximum weight of an edge
     * @param path Path to the file
     * @throws IOException If an I/O error occurs
     */
    public static void generateUndirectedWeightedGraph(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber > nodeNumber * (nodeNumber - 1) / 2) {
            throw new IllegalArgumentException("Too many edges for the given number of nodes, maximum number of edges is " + nodeNumber * (nodeNumber - 1) / 2);
        }
        if (edgeNumber < nodeNumber - 1) {
            throw new IllegalArgumentException("Not enough edges for the given number of nodes to form a connected graph, minimum number of edges is " + (nodeNumber - 1));
        }
        Set<String> edges = new HashSet<>();
        FileWriter fileWriter = new FileWriter(path);

        // Create all nodes first and add them to the file
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            fileWriter.append(i + ";\n");
        }

        // Create a tree first to ensure the graph is connected
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange);
            String edge = (i - 1) + " -- " + i;
            edges.add(edge);
            fileWriter.append(edge + " : " + weight + ";\n");
        }

        // Add additional edges
        while (edges.size() < edgeNumber) {
            // Randomly select two nodes
            int node1 = nodes.get((int) (Math.random() * nodes.size()));
            int node2 = nodes.get((int) (Math.random() * nodes.size()));
            // Skip self loops
            if (node1 == node2) continue;
            String edge = node1 + " -- " + node2;
            String reverseEdge = node2 + " -- " + node1;
            // Skip duplicate edges
            if (!edges.contains(edge) && !edges.contains(reverseEdge)) {
                int weight = (int) (Math.random() * weightRange);
                edges.add(edge);
                fileWriter.append(edge + " : " + weight + ";\n");
            }
        }

        fileWriter.close();
    }
}
