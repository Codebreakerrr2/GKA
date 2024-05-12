package Aufgabe1;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Aufgabe1.GraphSpeichern.saveGraphToFile;
import static org.junit.jupiter.api.Assertions.*;

class GraphSpeichernTest {

    /**
     * Tested, ob die Datei mit einem gerichteten Graph erstellt wird
     * @throws IOException
     */
    @Test
    void saveGraphToFileTestDirected() throws IOException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        saveGraphToFile(graph, "src\\main\\java\\Aufgabe1\\Dateien_1_gka\\neueGraphDatei.gka");
        assertTrue(Files.exists(Path.of("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\neueGraphDatei.gka")));
    }

    /**
     * Tested, ob die Datei mit einem ungerichteten Graph erstellt wird
     * @throws IOException
     */
    @Test
    void saveGraphToFileTestUnirected() throws IOException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        saveGraphToFile(graph, "src\\main\\java\\Aufgabe1\\Dateien_1_gka\\neueGraphDatei.gka");
        assertTrue(Files.exists(Path.of("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\neueGraphDatei.gka")));
    }

    /**
     * Verzeichnis existiert nicht
     * @throws IOException
     */
    @Test
    void saveGraphToFileTestUnirectedException() throws IOException {
        Graph graph = GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        assertThrows(RuntimeException.class, ()-> saveGraphToFile(graph, "src\\main\\java\\Aufgabe1\\Dateien_1_gka2\\neueGraphDatei.gka"));
    }
}