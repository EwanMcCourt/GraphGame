package Graph;

import java.util.LinkedList;

public class Path<N extends Node<N>> {
    N start;
    N end;
    double weight=Double.POSITIVE_INFINITY;
    LinkedList<N> nodes;

    public Path(LinkedList<N> nodes) {
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

    public void print() {
        System.out.println(nodes);
        if(weight == Double.POSITIVE_INFINITY) {
            System.out.format("A path from %d to %d is not possible\n", start.getIndex(), end.getIndex());
            return;
        }

        System.out.format("From %d to %d has weight %d\n", start.getIndex(), end.getIndex(), (int) weight);
        for (int i=0; i<nodes.size()-1; i++) {
            System.out.format("|%03d|\n  |\n %03d\n  V\n", nodes.get(i).getIndex(), (int) nodes.get(i).getWeight(nodes.get(i+1)));
        }
        System.out.format("|%02d|\n", nodes.get(nodes.size()-1).getIndex());
    }
}
