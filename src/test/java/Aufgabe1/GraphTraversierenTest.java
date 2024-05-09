package Aufgabe1;

import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Klasse für die Klasse "GraphTraversieren"
 */
class GraphTraversierenTest {
    /**
     * Tested, ob die Methode die richtige Kantenlänge, bei einem Graphen mit directed Edges zurückgibt
     * @throws IOException wenn die Datei nicht gefunden wird
     */
    @Test
    void shortestPathFile01DirectedKantenlaenge() throws IOException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertEquals(2, GraphTraversieren.shortestPath(graph, "a", "d").second);
    }

    /**
     * Tested, ob die Methode den richtigen Pfad, bei einem Graphen mit directed Edges zurückgibt.
     * Lässt mehrere Möglichkeiten für den Pfad zu
     * @throws IOException wenn die Datei nicht gefunden wird
     */

    @Test
    void shortestPathFile01DirectedPath() throws IOException {
        String [] array = {"a", "k", "d"};
        String [] array2 = {"a", "b", "d"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertNotNull(graph);
        assertTrue(Arrays.toString(array).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()) || Arrays.toString(array2).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()), "a should be equal to [a, k, d] or [a, b, d]");
    }

    /**
     * Tested, ob die Methode den richtigen Pfad, bei einem Graphen mit unirected Edges zurückgibt.
     * @throws IOException
     */
    @Test
    void shortestPathFile01UnirectedPathAF() throws IOException {
        String [] array = {"a", "e", "f"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertEquals(Arrays.toString(array), GraphTraversieren.shortestPath(graph, "a", "f").first.toString(), "a should be equal to [a, e, f]");
    }

    /**
     * Tested, ob die Methode den richtigen Pfad, bei einem Graphen mit unirected Edges zurückgibt.
     * @throws IOException
     */
    @Test
    void shortestPathFile01UnirectedPathFA() throws IOException {
        String [] array = {"f", "a"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertEquals(Arrays.toString(array), GraphTraversieren.shortestPath(graph, "f", "a").first.toString(), "a should be equal to [f, a]");
    }

    /**
     * Tested, ob die Methode die richtige Kantenlänge, bei einem Graphen mit unirected Edges zurückgibt.
     * @throws IOException
     */
    @Test
    void shortestPathFile02UndirectedKantenlaenge() throws IOException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertEquals(2, GraphTraversieren.shortestPath(graph, "a", "h").second);
    }
}