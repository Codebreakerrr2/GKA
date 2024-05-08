package Aufgabe1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GraphSpeichern {
    /**
     * @param graph
     * @author Usman Amini, Andre Demir.
     * Die Methode speichert einen Graphen in einer Datei.
     */
  /*  public static void saveGraphToFile(Graph graph, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Edge edge : graph.edges().toList()) {
                String node1 = edge.getNode0().getId();
                String node2 = edge.getNode1().getId();
                String direction = edge.isDirected() ? "->" : "--";
                String edgeName = edge.hasAttribute("ui.label") ? edge.getAttribute("ui.label").toString() : "";
                String edgeWeight = edge.hasAttribute("Gewicht") ? edge.getAttribute("Gewicht").toString() : "";
                if (!edgeName.equals("")) {
                    writer.write(node1 + " " + direction + " " + node2 + " : " + edgeName + ";\n");
                }
                if (edgeName.equals("")) {
                    writer.write(node1 + " " + direction + " " + node2 + ";\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

   /* public static void saveGraphToFile2(Graph graph, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            graph.nodes().forEach((node) -> {
                String node1 = node.getId();
                if (node.getOutDegree() == 0) {
                    try {
                        writer.write(node.getId() + ";\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    node.leavingEdges().forEach((Edge edge) -> {
                        String node2 = edge.getTargetNode().getId();
                        String edgeName = edge.getId();


                        String direction = graph.getEdge(edgeName).isDirected() ? "->" : "--";
                        String edgeWeight = graph.getEdge(edgeName).hasAttribute("Gewicht") ? ": " + graph.getEdge(edgeName).getAttribute("Gewicht").toString() : "";
                        try {
                            writer.write(node1 + " " + direction + " " + node2 + "(" + edgeName + ")" + edgeWeight + ";\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    //works
  /*  public static void saveGraphToFile3(Graph graph, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            HashSet<String> writtenEdges = new HashSet<>();
            graph.nodes().forEachOrdered((node) -> {
                String node1 = node.getId();
                if (node.getOutDegree() == 0) {
                    try {
                        writer.write(node.getId() + ";\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    node.neighborNodes().forEachOrdered((neighbor) -> {
                        if (node.hasEdgeToward(neighbor)) {
                            String node2 = neighbor.getId();
                            String edgeName = node.getEdgeToward(neighbor) == null ? "" : node.getEdgeToward(neighbor).getId();
                            String direction = "";
                            String edgeWeight = "";
                            if (!edgeName.equals("")) {
                                direction = graph.getEdge(edgeName).isDirected() ? "->" : "--";
                                edgeWeight = graph.getEdge(edgeName).hasAttribute("Gewicht") ? ": " + graph.getEdge(edgeName).getAttribute("Gewicht").toString() : "";
                                edgeName="("+node.getEdgeToward(neighbor).getId()+")";
                            }
                            String edgeLine = node1 + " " + direction + " " + node2 + edgeName + edgeWeight + ";\n";
                            if (!writtenEdges.contains(edgeLine)) {
                                try {
                                    writer.write(edgeLine);
                                    writtenEdges.add(edgeLine);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public static void saveGraphToFile4(Graph graph, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
        graph.nodes().forEach((Node node)->{
            if(node.getDegree()==0){
                    try {
                        writer.write(node.getId()+";\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }}
        );
        Predicate<Edge> filterDirected= (Edge edge)->edge.isDirected();
        Predicate<Edge> filterUnirected= (Edge edge)->!edge.isDirected();

            graph.edges().filter(filterDirected).forEach((Edge edge)->{
            Node node1=edge.getSourceNode();
            Node node2= edge.getTargetNode();
            String node1Name= node1.getId();
            String node2Name=node2.getId();
            String edgeName=edge.getId();
            String gewicht="";
            if(edge.getAttribute("Gewicht")!=null)  gewicht=":"+edge.getAttribute("Gewicht").toString();

            try {
                writer.write(node1Name+" -> "+node2Name+" ("+edgeName+") "+gewicht+" ;\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
            graph.edges().filter(filterUnirected).forEach((Edge edge)->{
            Node node1=edge.getSourceNode();
            Node node2= edge.getTargetNode();
            String node1Name= node1.getId();
            String node2Name=node2.getId();
            String edgeName=edge.getId();
            String gewicht="";
            if(edge.getAttribute("Gewicht")!=null)  gewicht=":"+edge.getAttribute("Gewicht").toString();

            try {
                writer.write(node1Name+" -- "+node2Name+" ("+edgeName+") "+gewicht+" ;\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

    }catch (IOException e) {
            throw new RuntimeException(e);
        }
}

    public static void main(String[] args) {
        Graph graph = GraphLesen.readGraph("C:\\Users\\Usman\\Documents\\Java Files\\GraphPraktikum\\branchAndre\\src\\main\\java\\Aufgabe1\\Dateien_1_gka\\graph01.gka");
        saveGraphToFile4(graph, "C:\\Users\\Usman\\Documents\\Java Files\\GraphPraktikum\\branchAndre\\src\\main\\java\\Aufgabe1\\graphDateien\\graph22.txt");
    }
}
