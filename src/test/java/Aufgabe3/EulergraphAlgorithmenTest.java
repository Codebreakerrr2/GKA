package Aufgabe3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Stack;

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

        // Adding nodes
        for (int i = 1; i <= 9; i++) {
            graph.addNode(String.valueOf(i));
        }

        // Adding edges with labels (undirected)
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
    }

    @Test
    public void hierholzerReturnsCorrectCirclesWhenGraphHasEvenDegreesAndMultipleCircuits() {
        Graph graph = new MultiGraph("Complex Euler Graph");

        // Visualisierung aktivieren
        graph.setAttribute("ui.stylesheet", "node {fill-color: red; size: 20px; text-alignment: at-right;} edge {fill-color: black;}");
        graph.setAttribute("ui.quality", true);
        graph.setAttribute("ui.antialias", true);

        // Erster Zyklus hinzufügen
        String[] cycle1 = {"A", "B", "C", "D"};
        for (String node : cycle1) {
            graph.addNode(node).setAttribute("ui.label", node);
        }
        for (int i = 0; i < cycle1.length; i++) {
            graph.addEdge(cycle1[i] + cycle1[(i + 1) % cycle1.length], cycle1[i], cycle1[(i + 1) % cycle1.length]);
        }

        // Zweiter Zyklus hinzufügen
        String[] cycle2 = {"E", "F", "G", "H"};
        for (String node : cycle2) {
            graph.addNode(node).setAttribute("ui.label", node);
        }
        for (int i = 0; i < cycle2.length; i++) {
            graph.addEdge(cycle2[i] + cycle2[(i + 1) % cycle2.length], cycle2[i], cycle2[(i + 1) % cycle2.length]);
        }

        // Verbindungskanten zwischen den Zyklen hinzufügen
        graph.addEdge("AD2", "A", "D"); // Zusätzliche Kante im ersten Zyklus
        graph.addEdge("EH2", "E", "H"); // Zusätzliche Kante im zweiten Zyklus
        graph.addEdge("AE", "A", "E"); // Verbindungskante zwischen den Zyklen
        graph.addEdge("DH", "D", "H"); // Weitere Verbindungskante

        // Graph anzeigen
        graph.display();
    }
}