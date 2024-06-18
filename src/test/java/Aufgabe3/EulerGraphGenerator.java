package Aufgabe3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EulerGraphGenerator {

    public static void generateEulerianGraph2(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
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

        // Creates a cyclic structure first to ensure the graph has an even degree for all nodes
        for (int i = 1; i < nodeNumber; i++) {
            int weight = (int) (Math.random() * weightRange) + 1;
            String edge = nodes.get(i - 1) + " -- " + nodes.get(i);
            edges.add(edge);
            fileWriter.append(edge + " : " + weight + ";\n");
        }
        // Closing the cycle to ensure connected Graph
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
}
