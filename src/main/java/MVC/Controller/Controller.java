package MVC.Controller;

import MVC.Model.Model;
import MVC.Model.Point;
import MVC.View.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;

        Point source = this.model.getPoint(3);
        Point target = this.model.getPoint(13);
        model.getPath(source, target).print();

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
        view.populateGraph(model.getPoints());
        for (Point point : model.getPoints()) {
            for (Point neighbour : model.getNeighbours(point)){
                view.addConnection(point, neighbour, (int) model.getWeight(point, neighbour));
            }
        }

        // https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
        for (DisplayNode node : view.getNodes()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> clickPoint(node.getPoint()));
        }
    }

    public void clickPoint(Point point) {
        view.setHighlightColor(point, Color.GREEN);
        view.toggleHighlightNode(point);
    }
}
