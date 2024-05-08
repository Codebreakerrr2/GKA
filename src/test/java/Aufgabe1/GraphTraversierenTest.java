package Aufgabe1;

import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphTraversierenTest {

    @Test
    void shortestPathFile01DirectedKantenlaenge() throws FileNotFoundException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertNotNull(graph);
        assertEquals(2, GraphTraversieren.shortestPath(graph, "a", "d").second);
    }

    @Test
    void shortestPathFile01FalsePositiveDirectedKantenlaenge() throws FileNotFoundException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertNotNull(graph);
        assertNotEquals(2, (int) GraphTraversieren.shortestPath(graph, "a", "c").second);
    }

    @Test
    void shortestPathFile01DirectedPath() throws FileNotFoundException {
        String [] array = {"a", "k", "d"};
        String [] array2 = {"a", "b", "d"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertNotNull(graph);
        assertTrue(Arrays.toString(array).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()) || Arrays.toString(array2).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()), "a should be equal to [a, k, d] or [a, b, d]");
    }

    @Test
    void shortestPathFile01FalsePositiveDirectedPath() throws FileNotFoundException {
        String [] array = {"a", "k", "a"};
        String [] array2 = {"a", "b", "a"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        assertNotNull(graph);
        assertFalse(Arrays.toString(array).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()) || Arrays.toString(array2).equals(GraphTraversieren.shortestPath(graph, "a", "d").first.toString()), "a should be equal to [a, k, d] or [a, b, d]");
    }

    @Test
    void shortestPathFile01UnirectedPathAF() throws FileNotFoundException {
        String [] array = {"a", "e", "f"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertNotNull(graph);
        assertEquals(Arrays.toString(array), GraphTraversieren.shortestPath(graph, "a", "f").first.toString(), "a should be equal to [a, e, f]");
    }
    @Test
    void shortestPathFile01UnirectedPathFA() throws FileNotFoundException {
        String [] array = {"f", "a"};
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertNotNull(graph);
        assertEquals(Arrays.toString(array), GraphTraversieren.shortestPath(graph, "f", "a").first.toString(), "a should be equal to [f, a]");
    }


    @Test
    void shortestPathFile02UndirectedKantenlaenge() throws FileNotFoundException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertNotNull(graph);
        assertEquals(2, GraphTraversieren.shortestPath(graph, "a", "h").second);
    }

    @Test
    void shortestPathFile02UndirectedFalsePositiveUndirectedKantenlaenge() throws FileNotFoundException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertNotNull(graph);
        assertNotEquals(1, GraphTraversieren.shortestPath(graph, "a", "i").second);
    }


}