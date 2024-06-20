package Aufgabe3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static Aufgabe1.GraphLesen.readGraph;
import static Aufgabe3.EulergraphAlgorithmen.*;
import static org.junit.jupiter.api.Assertions.*;

public class EulergraphAlgorithmenTest {
    private EulergraphAlgorithmen eulergraphAlgorithmen;
    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = new MultiGraph("Test Graph");
    }

    @Test
    public void fleuryReturnsNullWhenGraphHasOddDegrees() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");

        Stack<Edge> result = fleury(graph);

        assertNull(result, "Fleury should return null when graph has nodes with odd degrees");
    }

    @Test
    public void fleuryReturnsCorrectCircuitWhenGraphHasEvenDegrees() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        Stack<Edge> result = fleury(graph);

        assertEquals(3, result.size(), "Fleury should return a circuit with all edges when graph has nodes with even degrees");
    }

    @Test
    public void hierholzerReturnsNullWhenGraphHasOddDegrees() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        assertThrows(OddNodeDegreeException.class, () -> hierholzer(graph));
    }

    @Test
    public void hierholzerReturnsCorrectCircuitWhenGraphHasEvenDegrees() throws OddNodeDegreeException, DisconnectedGraphException {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        assertEquals(3, hierholzer(graph).size(), "Fleury should return a circuit with all edges when graph has nodes with even degrees");
    }

    @Test
    public void disconnectsGraphReturnsTrueWhenEdgeRemovalDisconnectsGraph() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        assertTrue(disconnectsGraph(graph, graph.getEdge("BC")), "disconnectsGraph should return true when removal of the edge disconnects the graph");
    }

    @Test
    public void disconnectsGraphReturnsFalseWhenEdgeRemovalDoesNotDisconnectGraph() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        assertFalse(disconnectsGraph(graph, graph.getEdge("BC")), "disconnectsGraph should return false when removal of the edge does not disconnect the graph");
    }


    @Test
    public void fleuryNichtZusammenhängenderGraph(){
        for (int i = 1; i <= 8; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "c");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "d");
        graph.addEdge("e", "5", "6").setAttribute("ui.label", "e");
        graph.addEdge("f", "6", "7").setAttribute("ui.label", "f");
        graph.addEdge("g", "7", "8").setAttribute("ui.label", "g");
        graph.addEdge("h", "8", "5").setAttribute("ui.label", "h");
        Assertions.assertNull(fleury(graph));
    }



    @Test
    public void hierholzerNichtZusammenhängenderGraph() {
        for (int i = 1; i <= 8; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "c");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "d");
        graph.addEdge("e", "5", "6").setAttribute("ui.label", "e");
        graph.addEdge("f", "6", "7").setAttribute("ui.label", "f");
        graph.addEdge("g", "7", "8").setAttribute("ui.label", "g");
        graph.addEdge("h", "8", "5").setAttribute("ui.label", "h");
        Assertions.assertThrows(DisconnectedGraphException.class, () -> hierholzer(graph));
    }

    @Test
    public void fleuryEulergraph9NodesZusammenhängend(){
        for (int i = 1; i <= 9; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "1", "3").setAttribute("ui.label", "c");
        graph.addEdge("d", "1", "8").setAttribute("ui.label", "d");
        graph.addEdge("e", "8", "7").setAttribute("ui.label", "e");
        graph.addEdge("f", "7", "6").setAttribute("ui.label", "f");
        graph.addEdge("g", "6", "9").setAttribute("ui.label", "g");
        graph.addEdge("h", "9", "5").setAttribute("ui.label", "h");
        graph.addEdge("i", "4", "5").setAttribute("ui.label", "i");
        graph.addEdge("j", "4", "9").setAttribute("ui.label", "j");
        graph.addEdge("k", "7", "9").setAttribute("ui.label", "k");
        graph.addEdge("l", "4", "7").setAttribute("ui.label", "l");
        graph.addEdge("m", "3", "4").setAttribute("ui.label", "m");
        graph.addEdge("n", "3", "7").setAttribute("ui.label", "n");
        graph.addEdge("o", "1", "7").setAttribute("ui.label", "o");
        Assertions.assertEquals(graph.edges().toList().size(), EulergraphAlgorithmen.fleury(graph).size());
    }

    @Test
    public void hierholzerEulergraph9NodesZusammenhängend() throws OddNodeDegreeException, DisconnectedGraphException {
        for (int i = 1; i <= 9; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "1", "3").setAttribute("ui.label", "c");
        graph.addEdge("d", "1", "8").setAttribute("ui.label", "d");
        graph.addEdge("e", "8", "7").setAttribute("ui.label", "e");
        graph.addEdge("f", "7", "6").setAttribute("ui.label", "f");
        graph.addEdge("g", "6", "9").setAttribute("ui.label", "g");
        graph.addEdge("h", "9", "5").setAttribute("ui.label", "h");
        graph.addEdge("i", "4", "5").setAttribute("ui.label", "i");
        graph.addEdge("j", "4", "9").setAttribute("ui.label", "j");
        graph.addEdge("k", "7", "9").setAttribute("ui.label", "k");
        graph.addEdge("l", "4", "7").setAttribute("ui.label", "l");
        graph.addEdge("m", "3", "4").setAttribute("ui.label", "m");
        graph.addEdge("n", "3", "7").setAttribute("ui.label", "n");
        graph.addEdge("o", "1", "7").setAttribute("ui.label", "o");
        Assertions.assertEquals(graph.edges().toList().size(), hierholzer(graph).size());
    }

    @Test
    public void fleury5Nodes() throws IOException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph1.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), EulergraphAlgorithmen.fleury(multiGraph).size());
    }

    @Test
    public void hierholzer5Nodes() throws IOException, OddNodeDegreeException, DisconnectedGraphException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph1.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), hierholzer(multiGraph).size());
    }


    @Test
    public void fleury10Nodes44Kanten() throws IOException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph2.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), EulergraphAlgorithmen.fleury(multiGraph).size());
    }

    @Test
    public void hierholzer10Nodes44Kanten() throws IOException, OddNodeDegreeException, DisconnectedGraphException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph2.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), hierholzer(multiGraph).size());
    }

    @Test
    public void fleury20Nodes60Kanten() throws IOException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph3.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), Objects.requireNonNull(fleury(multiGraph)).size());
    }

    @Test
    public void hierholzer20Nodes60Kanten() throws IOException, OddNodeDegreeException, DisconnectedGraphException {
        Graph multiGraph = readGraph("src/main/java/Aufgabe3/generatedGraphs/testGraph3.txt");
        Assertions.assertEquals(multiGraph.edges().toList().size(), hierholzer(multiGraph).size());
    }



    @Test
    public void fleuryEinfacherEulergraph(){
        for (int i = 1; i <= 4; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "c");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "d");
        System.out.println(EulergraphAlgorithmen.fleury(graph));
        Assertions.assertEquals(graph.edges().toList().size(), EulergraphAlgorithmen.fleury(graph).size());
    }

    @Test
    public void hierholzerEinfacherEulergraph() throws OddNodeDegreeException, DisconnectedGraphException {
        for (int i = 1; i <= 4; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "c");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "d");
        List<Edge> path = hierholzer(graph);
        Assertions.assertEquals(graph.edges().toList().get(0).getNode0().getId(), path.get(path.size()-1).getNode1().getId());

    }

    @Test
    public void hierholzerEulergraphMitBruecken() throws OddNodeDegreeException, DisconnectedGraphException {
        graph.setAttribute("ui.stylesheet", "node {fill-color: red; size: 20px; text-alignment: at-right;} edge {fill-color: black;}");
        graph.setAttribute("ui.quality", true);
        graph.setAttribute("ui.antialias", true);

        graph.addNode("A").setAttribute("ui.label", "A");
        graph.addNode("B").setAttribute("ui.label", "B");
        graph.addNode("C").setAttribute("ui.label", "C");
        graph.addNode("D").setAttribute("ui.label", "D");
        graph.addNode("E").setAttribute("ui.label", "E");
        graph.addNode("F").setAttribute("ui.label", "F");
        graph.addNode("G").setAttribute("ui.label", "G");
        graph.addNode("H").setAttribute("ui.label", "H");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("DA", "D", "A");
        graph.addEdge("EF", "E", "F");
        graph.addEdge("FG", "F", "G");
        graph.addEdge("GH", "G", "H");
        graph.addEdge("HE", "H", "E");
        graph.addEdge("AE", "A", "E");
        graph.addEdge("EA", "E", "A");
        Assertions.assertEquals(graph.edges().toList().size(), hierholzer(graph).size());
    }

    @Test
    public void fleuryEulergraphMitBruecken(){
        graph.setAttribute("ui.stylesheet", "node {fill-color: red; size: 20px; text-alignment: at-right;} edge {fill-color: black;}");
        graph.setAttribute("ui.quality", true);
        graph.setAttribute("ui.antialias", true);

        graph.addNode("A").setAttribute("ui.label", "A");
        graph.addNode("B").setAttribute("ui.label", "B");
        graph.addNode("C").setAttribute("ui.label", "C");
        graph.addNode("D").setAttribute("ui.label", "D");
        graph.addNode("E").setAttribute("ui.label", "E");
        graph.addNode("F").setAttribute("ui.label", "F");
        graph.addNode("G").setAttribute("ui.label", "G");
        graph.addNode("H").setAttribute("ui.label", "H");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("DA", "D", "A");
        graph.addEdge("EF", "E", "F");
        graph.addEdge("FG", "F", "G");
        graph.addEdge("GH", "G", "H");
        graph.addEdge("HE", "H", "E");
        graph.addEdge("AE", "A", "E");
        graph.addEdge("EA", "E", "A");
        Assertions.assertEquals(graph.edges().toList().size(), EulergraphAlgorithmen.fleury(graph).size());
    }

    @Test
    public void eachNodeHasEvenDegreeReturnsTrueWhenAllNodesHaveEvenDegree() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("DA", "D", "A");
        boolean result = eachNodeHasEvenDegree(graph);
        assertTrue(result, "eachNodeHasEvenDegree should return true when all nodes have even degree");
    }

    @Test
    public void eachNodeHasEvenDegreeReturnsFalseWhenNotAllNodesHaveEvenDegree(){
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        boolean result = eachNodeHasEvenDegree(graph);
        assertFalse(result, "eachNodeHasEvenDegree should return false when not all nodes have even degree");
    }

    @Test
    public void eulerianGraphGeneratorProducesCorrectGraphs0() throws IOException {
        Graph graph = EulergraphAlgorithmen.generateEulerianGraph(6, 10, 10);
        Assertions.assertTrue(eachNodeHasEvenDegree(graph));
    }

    @Test
    public void eulerianGraphGeneratorProducesCorrectGraphs1() throws IOException {
        Graph graph = EulergraphAlgorithmen.generateEulerianGraph(10, 40, 10);
        Assertions.assertTrue(eachNodeHasEvenDegree(graph));
    }

    @Test
    public void eulerianGraphGeneratorProducesCorrectGraphs2() throws IOException {
        Graph graph = EulergraphAlgorithmen.generateEulerianGraph(20, 60, 10);
        Assertions.assertTrue(eachNodeHasEvenDegree(graph));
    }

    @Test
    public void eulerianGraphGeneratorProducesCorrectGraphs3() throws IOException {
        Graph graph = EulergraphAlgorithmen.generateEulerianGraph(30, 80, 10);
        Assertions.assertTrue(eachNodeHasEvenDegree(graph));
    }

    @Test
    public void fleuryProducesCorrectPath() throws IOException {
        for (int i = 0; i < 100 ; i++){
            Graph graph = EulergraphAlgorithmen.generateEulerianGraph(100, 400, 10);
            List<Edge> path = fleury(graph);
            System.out.println(path.get(0));
            System.out.println(path.get(path.size()-1));
            Assertions.assertTrue(graph.edges().toList().get(0).getNode0().getId().equals(path.get(path.size()-1).getNode0().getId()) || graph.edges().toList().get(0).getNode0().getId().equals(path.get(path.size()-1).getNode1().getId()));
        }
    }

    @Test
    public void hierholzerProducesCorrectPath() throws IOException, OddNodeDegreeException, DisconnectedGraphException {
        for (int i = 0; i < 100 ; i ++){
            Graph graph = EulergraphAlgorithmen.generateEulerianGraph(1000, 4000, 10);
            List<Edge> path = hierholzer(graph);
            System.out.println(path.get(0));
            System.out.println(path.get(path.size()-1));
            Assertions.assertTrue(graph.edges().toList().get(0).getNode0().getId().equals(path.get(path.size()-1).getNode0().getId()) || graph.edges().toList().get(0).getNode0().getId().equals(path.get(path.size()-1).getNode1().getId()));
        }
    }
}