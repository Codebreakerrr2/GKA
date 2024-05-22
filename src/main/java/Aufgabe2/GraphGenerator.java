package Aufgabe2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphGenerator {
    public static void generateUndirectedWeightesGraphs(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (edgeNumber > nodeNumber * (nodeNumber - 1) / 2) {
            throw new IllegalArgumentException("Too many edges for the given number of nodes, maximum number of edges is " + nodeNumber * (nodeNumber - 1) / 2);
        }
        Set<String> edges = new HashSet<>();
        FileWriter fileWriter = new FileWriter(path);

        // Create all nodes first and add them to the file
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNumber; i++) {
            nodes.add(i);
            fileWriter.append(i + ";\n");
        }

        while (edges.size() < edgeNumber && nodes.size() > 1) {
            int node1 = nodes.get((int) (Math.random() * nodes.size()));
            int node2 = nodes.get((int) (Math.random() * nodes.size()));
            if (node1 == node2) continue; // Skip self-loops
            String edge = node1 + " -- " + node2;
            String reverseEdge = node2 + " -- " + node1;
            if (!edges.contains(edge) && !edges.contains(reverseEdge)) {
                int weight = (int) (Math.random() * weightRange);
                edges.add(edge);
                fileWriter.append(edge + " : " + weight + ";\n");
            }
        }

        fileWriter.close();
    }

   public static void main(String[] args) throws IOException {
       //for (int i = 0; i< 10; i++){
           generateUndirectedWeightesGraphs(5, 10, 10, "src/main/java/Aufgabe2/generatedGraphs/testGraph.gka");
       //}
   }
}
