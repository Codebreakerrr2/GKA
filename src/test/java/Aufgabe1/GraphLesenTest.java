package Aufgabe1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GraphLesenTest {

    @Test
    void readDirectedGraphWithoutWeightFile01() throws FileNotFoundException {
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka"));
    }

    @Test
    void readUndirectedGraphWithWeightFile04() throws FileNotFoundException {
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph04.gka"));
    }

    @Test
    void readDirectedGraphWithWeightFile05CorruptFile() throws FileNotFoundException {
        assertNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph05.gka"));
    }
    @Test
    void readDirectedGraphWithoutWeightFile06CorruptFile(){
        assertThrows(FileNotFoundException.class, () -> GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph016.gka"));
    }
}