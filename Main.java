
public class Main {
    public static void main(String[] args) {

        //Making a graph for a to b
        Graph<String> graph = new Graph<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a","b");

        //Prints all nodes connected to node a
        graph.getEdges("a");

    }
}
