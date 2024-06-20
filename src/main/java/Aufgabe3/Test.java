package Aufgabe3;

import Aufgabe1.GraphTraversieren;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.Graphs;

import java.io.IOException;
import java.util.Stack;

import static Aufgabe3.EulergraphAlgorithmen.eachNodeHasEvenDegree;

public class Test {

        private final Graph graph;
        private final Graph inputGraph;
        private final Path path;
        private Node startNode;
        private final Stack<Node> stack;

        public Test(Graph inputGraph) {
            this.graph = Graphs.clone(inputGraph);
            this.inputGraph = inputGraph;
            this.startNode = this.graph.getNode(0);
            this.stack = new Stack<>();
            this.path = new Path();
        }

        /**
         * Computes a eulerian path by finding all cycles within a given graph
         * @param node -> currently observed Node
         */
        private void compute(Node node) {
            if (this.stack.isEmpty()) return;

            Node neighborNode = node.neighborNodes().findFirst().orElse(null);
            this.stack.add(neighborNode);
            Edge edge = node.getEdgeBetween(neighborNode);
            this.graph.removeEdge(edge);
            // Push the startNode onto the stack if it is a neighbor of neighborNode.
            // In this case the current cycle will be closed and the next node of the stack
            // will be the startNode of the next cycle to explore
            if (neighborNode.neighborNodes().anyMatch(n -> n.equals(this.startNode))) {
                this.stack.add(this.startNode);
                graph.removeEdge(neighborNode.getEdgeBetween(this.startNode));
                addToPath();
                this.startNode = this.stack.peek();
                if (this.graph.getEdgeCount() > 0) compute(this.startNode);
            } else {
                compute(neighborNode);
            }
        }

        /**
         * Removes all nodes having degree 0 and adds the edge between the
         * respective Node and the upper Node to the path.
         */
        private void addToPath() {
            while (this.stack.peek().getDegree() == 0 && this.stack.size() > 1) {
                Node node0 = this.inputGraph.getNode(this.stack.pop().getId());
                Node node1 = this.inputGraph.getNode(this.stack.peek().getId());
                this.path.add(node0.getEdgeBetween(node1));
            }
        }

        public void compute() throws DisconnectedGraphException, OddNodeDegreeException {
            if (!GraphTraversieren.traverseGraph(this.graph, String.valueOf(graph.getNode(0))))
                throw new DisconnectedGraphException("The passed graph must be connected");
            if (!eachNodeHasEvenDegree(this.graph))
                throw new OddNodeDegreeException("Each node of the graph must have an even edge degree");

            this.stack.add(this.startNode);
            this.path.setRoot(this.inputGraph.getNode(this.startNode.getId()));
            compute(this.startNode);
        }

        public Path getPath() {
            return this.path;
        }

    public static void main(String[] args) throws IOException, OddNodeDegreeException, DisconnectedGraphException {
        Graph graph = EulergraphAlgorithmen.generateEulerianGraph(1000, 1000, 10);
        Test hierholzer = new Test(graph);
        hierholzer.compute();
        System.out.println(hierholzer.getPath());
    }
}

