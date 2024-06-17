package Aufgabe3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static Aufgabe3.EulergraphAlgorithmen.buildEulerianCircuit;
import static Aufgabe3.EulergraphAlgorithmen.fleury;
import static org.junit.jupiter.api.Assertions.*;

public class EulergraphAlgorithmenTest {
    private EulergraphAlgorithmen eulergraphAlgorithmen;
    private Graph graph;

    @BeforeEach
    public void setUp() {
        eulergraphAlgorithmen = new EulergraphAlgorithmen();
        graph = new SingleGraph("Test Graph");
    }

    @Test
    public void fleuryReturnsNullWhenGraphHasOddDegrees() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");

        Stack<Edge> result = eulergraphAlgorithmen.fleury(graph);

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

        Stack<Edge> result = eulergraphAlgorithmen.fleury(graph);

        assertNotNull(result, "Fleury should not return null when graph has nodes with even degrees");
        assertEquals(3, result.size(), "Fleury should return a circuit with all edges when graph has nodes with even degrees");
    }

    @Test
    public void disconnectsGraphReturnsTrueWhenEdgeRemovalDisconnectsGraph() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");

        boolean result = eulergraphAlgorithmen.disconnectsGraph(graph, graph.getEdge("BC"));

        assertTrue(result, "disconnectsGraph should return true when removal of the edge disconnects the graph");
    }

    @Test
    public void disconnectsGraphReturnsFalseWhenEdgeRemovalDoesNotDisconnectGraph() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        boolean result = eulergraphAlgorithmen.disconnectsGraph(graph, graph.getEdge("BC"));

        assertFalse(result, "disconnectsGraph should return false when removal of the edge does not disconnect the graph");
    }

    @Test
    public void graph() {
        Graph graph = new MultiGraph("Graph");
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

        Assertions.assertEquals(graph.edges().toList().size(), buildEulerianCircuit(EulergraphAlgorithmen.hierholzer(graph)).size());
    }


    @Test
    public void fleuryNichtzusammenhängenderGraph(){
        Graph graph = new MultiGraph("Graph nicht zusammenhängend");
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
        fleury(graph);
    }



    @Test
    public void hierholzerNichtzusammenhängenderGraph(){
        Graph graph = new MultiGraph("Graph nicht zusammenhängend");
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
        buildEulerianCircuit(EulergraphAlgorithmen.hierholzer(graph));
    }

    @Test
    public void fleuryEulergraph9KantenZusammenhängend(){
        Graph graph = new MultiGraph("Graph");
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
    public void hierholzerEulergraph9KantenZusammenhängend(){
        Graph graph = new MultiGraph("Graph");
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

        Assertions.assertEquals(graph.edges().toList().size(), buildEulerianCircuit(EulergraphAlgorithmen.hierholzer(graph)).size());
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
    public void hierholzerEinfacherEulergraph(){
        for (int i = 1; i <= 4; i++) {
            graph.addNode(String.valueOf(i));
        }
        graph.addEdge("a", "1", "2").setAttribute("ui.label", "a");
        graph.addEdge("b", "2", "3").setAttribute("ui.label", "b");
        graph.addEdge("c", "3", "4").setAttribute("ui.label", "c");
        graph.addEdge("d", "4", "1").setAttribute("ui.label", "d");
        System.out.println(EulergraphAlgorithmen.hierholzer(graph));
        Assertions.assertEquals(graph.edges().toList().size(), buildEulerianCircuit(EulergraphAlgorithmen.hierholzer(graph)).size());
    }

    @Test
    public void hierholzerEulergraphMitBruecken(){
        Graph graph = new MultiGraph("Euler Graph with Bridge");

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
        Assertions.assertEquals(graph.edges().toList().size(), buildEulerianCircuit(EulergraphAlgorithmen.hierholzer(graph)).size());
    }

    @Test
    public void fleuryEulergraphMitBruecken(){
        Graph graph = new MultiGraph("Euler Graph with Bridge");

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
}