package Aufgabe1;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphSpeichernTest {

    @Test
    void leererGraphSaveTest() {
        Graph graph= new MultiGraph("1");
        String path="C:\\Users\\Usman\\Documents\\Java Files\\GKA\\src\\main\\java\\Aufgabe1\\graphDateien\\leererGraphSaveTest";
        GraphSpeichern.saveGraphToFile4(graph,path);
    }

    @Test
    void directedGraphSaveTest() {
        Graph graph= GraphLesen.readGraph("C:\\Users\\Usman\\Documents\\Java Files\\GKA\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        String path="C:\\Users\\Usman\\Documents\\Java Files\\GKA\\src\\main\\java\\Aufgabe1\\graphDateien\\directedGraphSaveTest";
        GraphSpeichern.saveGraphToFile4(graph,path);



    }
    @Test
    void UndirectedGraphSaveTest() {

        Graph graph= GraphLesen.readGraph("C:\\Users\\Usman\\Documents\\Java Files\\GKA\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph02.gka");
        String path="C:\\Users\\Usman\\Documents\\Java Files\\GKA\\src\\main\\java\\Aufgabe1\\graphDateien\\UndirectedGraphSaveTest";
        GraphSpeichern.saveGraphToFile4(graph,path);


    }
}