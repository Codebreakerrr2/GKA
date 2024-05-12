package Aufgabe2;

import java.io.FileWriter;
import java.io.IOException;

public class GraphGenerator {
    public static void generateUndirectedWeightesGraphs(int nodeNumber, int edgeNumber, int weightRange, String path) throws IOException {
        if (nodeNumber < 0 || edgeNumber < 0 || weightRange < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        FileWriter fileWriter = new FileWriter(path);
        for (int i = 0; i < edgeNumber; i++) {
            int node1 = (int) (Math.random() * nodeNumber);
            int node2 = (int) (Math.random() * nodeNumber);
            int weight = (int) (Math.random() * weightRange);
            fileWriter.append(String.valueOf(node1)).append(" -- ").append(String.valueOf(node2)).append(" : ").append(String.valueOf(weight)).append(";\n");
        }
        fileWriter.close();
    }

   /*public static void main(String[] args) throws IOException {
       for (int i = 0; i< 10; i++){
           generateUndirectedWeightesGraphs(5, 10, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph0" + i + ".gka");
       }
   }*/
}
