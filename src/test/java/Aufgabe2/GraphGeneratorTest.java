package Aufgabe2;

import Aufgabe1.GraphLesen;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphGeneratorTest {
    @Test
    public void generateGraphWith4NodesAnd8Edges() throws IOException {
        GraphGenerator.generateUndirectedWeightesGraphs(4, 8, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph4_6");
        Graph graph2 = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/newGraph4_6");
        assertEquals(4, graph2.getNodeCount());
    }

    @Test
    public void generateGraphWith4NodesAnd8Edges2() throws IOException {
        GraphGenerator.generateUndirectedWeightesGraphs(4, 9, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph4_6");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/newGraph4_6");
        assertEquals(8, graph.getEdgeCount());
    }

    @Test
    public void generateGraphWithNotAllowedNumbers() throws IOException {
        assertThrows(IllegalArgumentException.class, ()-> GraphGenerator.generateUndirectedWeightesGraphs(-1, 8, 10, "src/main/java/Aufgabe2/generatedGraphs/newGraph4_6"));
    }

}
