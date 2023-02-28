package MVC.Controller;

import Graph.Dijkstra;
import Graph.Graph;
import MVC.Model.Model;
import MVC.View.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Controller {
    Graph graph;
    int source = 3;
    int target = 13;

    private View view;
    private Model model;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;
        graph = this.model.generateGraph();
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
        for (int index : graph.getNodes()) {
            for (int neighbour : graph.getNeighbours(index)){
                view.addConnection(index, neighbour, (int) graph.getWeight(index, neighbour));
            }
        }

        for (DisplayNode node : view.getNodes()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> clickNode(node.getIndex()));
        }
    }

    public void test(int index) {
        System.out.println("Test worked");
    }

    public void clickNode(int index) {
        view.setHighlightColor(index, Color.GREEN);
        view.toggleHighlightNode(index);
    }
}
