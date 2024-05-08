package Aufgabe1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphLesenTest {

    @Test
    void readDirectedGraphWithoutWeightFile01(){
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka"));
    }

    @Test
    void readUndirectedGraphWithWeightFile04(){
        assertNotNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph04.gka"));
    }

    @Test
    void readDirectedGraphWithWeightFile05CorruptFile(){
        assertNull(GraphLesen.readGraph("src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph05.gka"));
    }
}