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
    void primTestGraphWithFourNodesAndFourEdgesRetursGraphWithThreeEdges(){
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
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(3, mst.first.getEdgeCount());
    }

    @Test
    void primTestEmptyGraphRetursEmptyGraph(){
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
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(19, mst.first.getEdgeCount());
    }

    @Test
    void primZusammenhängenderGraphMitMaximalemKantengewichtTest() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(10000, 124750, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(9999, mst.first.getEdgeCount());
    }

    @Test
    void primZusammenhängenderGraphMitMaximalemKantengewichtTest2() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(20, 20, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.prim(graph);
        assertEquals(19, mst.first.getEdgeCount());
    }

    @Test
    void kruskalTestGraphWith20NodesAnd190EdgesShouldReturn19EdgesForMST() throws IOException {
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(19, mst.first.getEdgeCount());
    }

    @Test
    void kruskalZusammenhängenderGraphMitMaximalemKantengewichtTest() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(500, 124750, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(499, mst.first.getEdgeCount());
    }

    @Test
    void kruskalZusammenhängenderGraphMitMaximalemKantengewichtTest2() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(20, 20, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst = MstAlgorithmen.kruskal(graph);
        assertEquals(19, mst.first.getEdgeCount());
    }

    @Test
    void kruskalPrimGleicheEdgeCount() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(20, 20, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst1 = MstAlgorithmen.kruskal(graph);
        Pair<Graph, Integer> mst2 = MstAlgorithmen.kruskal(graph);
        assertEquals(mst2.first.getEdgeCount(), mst1.first.getEdgeCount());
    }

    @Test
    void kruskalPrimGleicheEdgeWeight() throws IOException {
        GraphGenerator.generateUndirectedWeightedGraph(20, 20, Integer.MAX_VALUE, "src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Graph graph = GraphLesen.readGraph("src/main/java/Aufgabe2/generatedGraphs/testGraph1000Kanten.gka");
        Pair<Graph, Integer> mst1 = MstAlgorithmen.kruskal(graph);
        Pair<Graph, Integer> mst2 = MstAlgorithmen.kruskal(graph);
        assertEquals(mst2.second, mst1.second);
    }
}
