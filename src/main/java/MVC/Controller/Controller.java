package MVC.Controller;

import MVC.Model.Path;
import MVC.Model.Model;
import MVC.Model.Point;
import MVC.View.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.LinkedList;

public class Controller {
    private final View view;
    private final Model model;
    private LinkedList<Point> path = new LinkedList<>();
    Point source;
    Point target;

    int difficulty = 2;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;

        source = this.model.getPoint(3);
        target = this.model.getPoint(13);
        Path path = model.getChain(source, target);
        path.print();

        //Initialise View
        this.view = view;
        view.initialise();
        view.setTitle("Group 29");
        try {
            view.setIcon("src/main/resources/MVC/View/icon.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        view.addMenuButton("start", e -> start());
        view.addMenuButton("stop", e -> stop());
        view.addDifficultyEventListener((observableValue, number, t1) -> setDifficulty(t1));
        view.setMaxDifficulty(12);

        //for demo purposes only - to be removed
        view.addMenuTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsButton("test", e -> System.out.println("test button pressed"));


        populateGraph();
//        view.displayPath(path);
        clickPoint(source);
    }

    private void test(String input) {
        System.out.println(input);
    }

    private void start(){
        System.out.println("Start");
    }

    private void stop(){
        System.out.println("Stop");
    }

    private void setDifficulty(Number difficultyNumber) {
        int difficulty = difficultyNumber.intValue();
        if (this.difficulty != difficulty) {
            this.difficulty = difficulty;
            System.out.println(difficulty);
        }
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
        Color selected, option, current, goal;
        selected = Color.GREEN;
        option = Color.SKYBLUE;
        current = Color.ORANGE;
        goal = Color.BLUE;

        view.setHighlightColor(target, goal);

        if (!path.isEmpty() && (path.getLast()==target || (path.contains(point) && !(path.getLast() ==point)))) {
//            return;?
        }

        if (path.isEmpty()) {
            path.add(point);
            view.setHighlightColor(point, current);
            view.highlightNode(point, true);
            for (Point neighbour : model.getNeighbours(point)) {
                view.highlightNode(neighbour, true);
                if(neighbour != target) {
                    view.setHighlightColor(neighbour, option);
                }
                view.highlightConnection(point, neighbour, true);
                view.setConnectionHighlightColor(point, neighbour, option);
            }
        } else if (path.getLast()==point && point != source) {
            System.out.println("testp");
            path.removeLast();
            if (!path.isEmpty()) {
                view.highlightConnection(point, path.getLast(), false);
            }
            view.highlightNode(point, false);
            for (Point neighbour : model.getNeighbours(point)) {
                if (path.isEmpty() || !path.contains(neighbour)) {
                    if(neighbour != target) {
                        view.highlightNode(neighbour, false);
                    }
                    view.highlightConnection(point, neighbour, false);
                }
            }
            if (!path.isEmpty()){
                for (Point neighbour : model.getNeighbours(path.getLast())) {
                    if (!path.contains(neighbour)) {
                        view.highlightNode(neighbour, true);
                        view.setHighlightColor(neighbour, option);
                        view.highlightConnection(path.getLast(), neighbour, true);
                        view.setConnectionHighlightColor(path.getLast(), neighbour, option);
                    }
                }
                view.setHighlightColor(path.getLast(), current);
            }
        } else if (model.getNeighbours(path.getLast()).contains(point)) {
            for (Point neighbour : model.getNeighbours(path.getLast())) {
                if (!path.contains(neighbour) && neighbour != point) {
                    view.highlightConnection(path.getLast(), neighbour, false);
                    view.highlightNode(neighbour, false);
                }
            }
            if (point == target) {
                System.out.println("goal node");
                view.setHighlightColor(path.getLast(), selected);
                view.setConnectionHighlightColor(path.getLast(), point, selected);
//                view.highlightConnection(path.getLast(), point, true);
                path.addLast(point);
            } else {
                for (Point neighbour : model.getNeighbours(point)) {
                    if (!path.contains(neighbour)) {
                        view.highlightNode(neighbour, true);
                        if(neighbour != target) {
                            view.setHighlightColor(neighbour, option);
                        }
                        view.highlightConnection(point, neighbour, true);
                        view.setConnectionHighlightColor(point, neighbour, option);
                    }
                }
                view.setConnectionHighlightColor(path.getLast(), point, selected);
                view.highlightConnection(path.getLast(), point, true);
                view.setHighlightColor(point, selected);
                view.setHighlightColor(path.getLast(), selected);
                view.setHighlightColor(point, current);
                path.addLast(point);
            }
        }
    }
}
