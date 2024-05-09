package Aufgabe1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Klasse für die Klasse "GraphLesen"
 */
class GraphLesenTest {

    /**
     * Tested, ob die Methode den Graphen ohne Gewicht korrekt einliest und nicht Null returned
     * @throws IOException wenn die Datei nicht gefunden wird
     */
    @Test
    void readDirectedGraphWithoutWeightFile01() throws IOException {
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka"));
    }

    /**
     * Tested, ob die Methode den Graphen mit Gewicht korrekt einliest und nicht Null returned
     * @throws IOException wenn die Datei nicht gefunden wird
     */
    @Test
    void readUndirectedGraphWithWeightFile04() throws IOException {
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph04.gka"));
    }

    /**
     * Tested, ob die Methode der File 05 korrekt einliest und Null returned, da Sie Fehlerhaft ist.
     * @throws IOException
     */
    @Test
    void readDirectedGraphWithWeightFile05CorruptFile() throws IOException {
        assertNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph05.gka"));
    }

    /**
     * Tested, ob die Methode den File der nicht existiert korrekt einliest und eine IOException wirft
     */
    @Test
    void testReadGraphWithNonexistentFile() {
        assertThrows(IOException.class, () -> GraphLesen.readGraph("nonexistentfile.gka"));
    }
}