package MVC.Controller;

import Graph.Dijkstra;
import Graph.GraphADT;
import Graph.Node;
import MVC.Model.Model;
import MVC.View.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Controller {
    GraphADT graph;
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;
        graph = this.model.generateGraph();

        Node source = graph.getNode(3);
        Node target = graph.getNode(13);
        new Dijkstra(source, graph).getPath(target).print();

        //Initialise View
        this.view = view;
        view.initialise();
        view.setTitle("Group 29");
        try {
            view.setIcon("src/main/resources/MVC/View/icon.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populateGraph();
    }

    private void populateGraph() {
        view.populateGraph(graph.getNodes().size());
        for (Node node : graph.getNodes()) {
            for (Node neighbour : graph.getNeighbours(node)){
                view.addConnection(node.getIndex(), neighbour.getIndex(), (int) graph.getWeight(node, neighbour));
            }
        }

        // https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
        for (DisplayNode node : view.getNodes()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> clickNode(node.getIndex()));
        }
    }

    public void clickNode(int index) {
        view.setHighlightColor(index, Color.GREEN);
        view.toggleHighlightNode(index);
    }
}
