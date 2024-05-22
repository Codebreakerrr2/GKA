package Aufgabe2;

import Aufgabe1.GraphLesen;
import Aufgabe1.Pair;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MstAlgorithmenTest {
    public MstAlgorithmenTest() throws IOException {
    }

    @Test
    public void kruskalTestEmptyGraphRetursEmptyGraph(){
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(new MultiGraph("MSTK"));
        assertEquals(0, mst.first.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithOneNodeRetursEmptyGraph(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(0, mst.first.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithTwoNodesRetursEmptyGraph(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(0, mst.first.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithTwoNodesAndOneEdgeRetursGraphWithOneEdge(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(1, mst.first.getEdgeCount());
    }

    @Test
    public void kruskalTestGraphWithThreeNodesAndTwoEdgesRetursGraphWithTwoEdges(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        graph.addEdge("BC", "B", "C");
        graph.getEdge("BC").setAttribute("Gewicht", 2.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(2, mst.first.getEdgeCount());
    }

    @Test
    public void kruskalTestGraphWithThreeNodesAndThreeEdgesRetursGraphWithTwoEdges(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        graph.addEdge("BC", "B", "C");
        graph.getEdge("BC").setAttribute("Gewicht", 2.0);
        graph.addEdge("AC", "A", "C");
        graph.getEdge("AC").setAttribute("Gewicht", 3.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(2, mst.first.getEdgeCount());
    }
    @Test
    void kruskalTestGraphWithFourNodesAndFourEdgesRetursGraphWithThreeEdges(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        graph.addEdge("BC", "B", "C");
        graph.getEdge("BC").setAttribute("Gewicht", 2.0);
        graph.addEdge("AC", "A", "C");
        graph.getEdge("AC").setAttribute("Gewicht", 3.0);
        graph.addEdge("AD", "A", "D");
        graph.getEdge("AD").setAttribute("Gewicht", 4.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(3, mst.first.getEdgeCount());
    }

    @Test
    void primTestEmptyGraphRetursEmptyGraph(){
        assertThrows(IllegalArgumentException.class, () -> MstAlgorithmen.prim(null));
    }

    @Test
    void primTestEmptyGraphRetursEmptyGraphWithNoNodes(){
        assertThrows(IllegalArgumentException.class, () -> MstAlgorithmen.prim(null));
    }

    @Test
    public void primTestGraphWithOneNodeReturnsGraphWithNoNodeBecauseNoEdges(){
        Graph graph = new MultiGraph("MSTP");
        graph.addNode("A");
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(0, mst.first.getNodeCount());
    }

    @Test
    public void primTestGraphWithTwoNodesAndOneEdgeReturnsGraphWithOneEdge(){
        Graph graph = new MultiGraph("MSTP");
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(1, mst.first.getEdgeCount());
    }

    @Test
    public void primTestGraphWithThreeNodesAndTwoEdgesReturnsGraphWithTwoEdges(){
        Graph graph = new MultiGraph("MSTP");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        graph.addEdge("BC", "B", "C");
        graph.getEdge("BC").setAttribute("Gewicht", 2.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(2, mst.first.getEdgeCount());
    }

    @Test
    public void primTestGraphWithThreeNodesAndThreeEdgesReturnsGraphWithTwoEdges() {
        Graph graph = new MultiGraph("MSTP");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        graph.addEdge("BC", "B", "C");
        graph.getEdge("BC").setAttribute("Gewicht", 2.0);
        graph.addEdge("AC", "A", "C");
        graph.getEdge("AC").setAttribute("Gewicht", 3.0);
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(2, mst.first.getEdgeCount());
    }
    @Test
    void primTestGraphWith20NodesAnd190EdgesShouldReturn19EdgesForMST() throws IOException {
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/newGraph");
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(19, mst.first.getEdgeCount());
    }

}
