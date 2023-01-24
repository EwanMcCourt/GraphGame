import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph<Node> {

    //Creates a Hashmap - a Node and its Edges(aka, all other nodes it is connected to).
    private Map<Node, ArrayList<Node> > graph = new HashMap<>();


    //Adds a node to the hashmap and creates its own arraylist of edges for it.
    public void addNode(Node x)
    {
        graph.put(x, new ArrayList<Node>());
    }

    //Adds an edge between two nodes.
    public void addEdge(Node node1, Node node2){
        graph.get(node1).add(node2);
        graph.get(node2).add(node1);
    }

    //Prints all the edges of a certain node
    public void getEdges(Node x) {
        System.out.println(graph.get(x));
    }
}


