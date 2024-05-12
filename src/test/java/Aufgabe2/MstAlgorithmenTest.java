package Aufgabe2;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MstAlgorithmenTest {
    @Test
    public void kruskalTestEmptyGraphRetursEmptyGraph(){
        Graph mst = MstAlgorithmen.kruskal(new MultiGraph("MSTK"));
        assertEquals(0, mst.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithOneNodeRetursEmptyGraph(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(0, mst.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithTwoNodesRetursEmptyGraph(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(0, mst.getNodeCount());
    }

    @Test
    public void kruskalTestGraphWithTwoNodesAndOneEdgeRetursGraphWithOneEdge(){
        Graph graph = new MultiGraph("MSTK");
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB", "A", "B");
        graph.getEdge("AB").setAttribute("Gewicht", 1.0);
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(1, mst.getEdgeCount());
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
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(2, mst.getEdgeCount());
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
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(2, mst.getEdgeCount());
    }
    @Test void kruskalTestGraphWithFourNodesAndFourEdgesRetursGraphWithThreeEdges(){
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
        Graph mst = MstAlgorithmen.kruskal(graph);
        assertEquals(3, mst.getEdgeCount());
    }
}
