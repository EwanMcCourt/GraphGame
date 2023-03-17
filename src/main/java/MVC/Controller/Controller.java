package MVC.Controller;

import MVC.Model.Path;
import MVC.Model.Model;
import MVC.Model.Point;
import MVC.View.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.round;

public class Controller {
    private final View view;
    private final Model model;
    private final LinkedList<Point> path = new LinkedList<>();
    private Path selectedPath;
    private Path optimalPath;
    private Point source;
    private Point target;

    private Boolean gameOngoing = false;

    int difficulty = 3;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;

//        source = this.model.getPoint(3);
//        target = this.model.getPoint(13);
//        Path path = model.getPath(source, target);
//        path.print();

        //Initialise View
        this.view = view;
        view.initialise();
        view.setTitle("Group 29");
        try {
            view.setIcon("src/main/resources/MVC/View/icon.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        view.addMenuButton("Start", e -> start());
        view.addMenuButton("Stop", e -> stop());
        view.addDifficultyEventListener((observableValue, number, t1) -> setDifficulty(t1));
        view.setMaxDifficulty(model.getMaxPathLength());

        //for demo purposes only - to be removed
        view.addMenuTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsButton("test", e -> System.out.println("test button pressed"));


        populateGraph();
    }

    private void test(String input) {
        System.out.println(input);
    }

    private void start(){
        gameOngoing = true;

        // Clear Previous Game
        view.clearHighlights();

        // Pick Random Start And End Based On Difficulty
        optimalPath = model.getRandomPathBySize(difficulty);
        source = optimalPath.getFirst();
        target = optimalPath.getLast();

        // Initialise And Display New Path
        selectedPath = new Path();
        selectedPath.addLast(source);
        view.displayPath(selectedPath);
        view.showMoves(selectedPath);

        // Highlight Start And End Points
        view.setHighlightColor(source, NodeColour.START);
        view.highlightNode(source,true);
        view.setHighlightColor(target, NodeColour.GOAL);
        view.highlightNode(target,true);

        optimalPath.print();
    }

    private void stop(){
        gameOngoing = false;
        view.clearHighlights();
    }

    private void finishGame() {
        System.out.format("Congratulations! Your path had a weight of %f, the most optimal path has a weight of %f.\nYou got a score of %d",selectedPath.getWeight(), optimalPath.getWeight(), getScore());
    }
    private int getScore() {
        Double difference = Double.max(selectedPath.getWeight() - optimalPath.getWeight(), 0);
        return (int) (round((1-(difference/optimalPath.getWeight())) * (Double.valueOf(difficulty-2)/ Double.valueOf(model.getMaxPathLength()-2))*1000));
    }

    private void setDifficulty(Number difficultyNumber) {
        int difficulty = difficultyNumber.intValue();
        if (this.difficulty != difficulty) {
            this.difficulty = difficulty;
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
            node.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> hoverPoint(node.getPoint(),true));
            node.addEventFilter(MouseEvent.MOUSE_EXITED, e -> hoverPoint(node.getPoint(),false));
        }
    }

    public void hoverPoint(Point point, Boolean hover) {
        if (!gameOngoing) {
            return;
        }

        if (hover) {
            view.showMoves(point);
        } else {
            view.hideMoves(point);
        }
    }

    public void clickPoint(Point point) {
        // Check if game is active
        if (!gameOngoing) {
            return;
        }

        // Checking valid Point to click
        if(!selectedPath.isEmpty()) {
            // Check if clicked point is already in the selected path
            if(selectedPath.getPoints().contains(point) && point != selectedPath.getLast()) {
                return;
            }
            // Check if clicked point is neighbour of last point
            if(!selectedPath.getLast().getNeighbours().contains(point) && point != selectedPath.getLast()) {
                return;
            }
            // Check if clicked point is source point
            if(point == source) {
                return;
            }
            // Check if clicked point is goal node
            if(point == target) {
                gameOngoing = false;
                view.hideMoves(point);
            }
        }

        // Check whether adding or removing clicked point
        if (!selectedPath.isEmpty() && point == selectedPath.getLast()) {
            // Removing a Point
            selectedPath.removeLast();
        } else {
            // Adding a Point
            selectedPath.addLast(point);
        }

        // Updating display
        view.clearHighlights();
        view.displayPath(selectedPath);
        if(gameOngoing) {
            view.showMoves(selectedPath);
        } else {
            finishGame();
        }

        // Keep start and end highlighted
        view.setHighlightColor(source, NodeColour.START);
        view.highlightNode(source,true);
        view.setHighlightColor(target, NodeColour.GOAL);
        view.highlightNode(target,true);
    }
}
