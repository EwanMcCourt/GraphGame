package Graph;

import java.util.LinkedList;
import java.util.List;

public class GraphPath<N extends Node<N>> {
    N start;
    N end;
    double weight=Double.POSITIVE_INFINITY;
    LinkedList<N> nodes;

    public GraphPath(LinkedList<N> nodes) {
        this.nodes = nodes;
        if(nodes.isEmpty()) {
            return;
        }
        this.start = nodes.getFirst();
        this.end = nodes.getLast();

        double weight = 0;
        for (int i=0; i<nodes.size()-1; i++) {
            double tempweight = nodes.get(i).getWeight(nodes.get(i+1));

            if(tempweight == Double.POSITIVE_INFINITY) {
                break;
            }

            weight += tempweight;
        }

        this.weight = weight;
    }

    public List<N> getNodes() {
        return nodes;
    }
}
