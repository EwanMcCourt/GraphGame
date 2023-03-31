package MVC.Controller;

import MVC.Model.*;
import MVC.View.View;
import javafx.scene.control.TextField;

// Integral to game function

import java.io.IOException;
import java.util.List;

import static java.lang.Math.round;

public class Controller {
    private final View view;
    private final Model model;
    private Path selectedPath;
    private Path optimalPath;
    private Point source;
    private Point target;
    private Boolean gameOngoing = false;
    private int difficulty = 3;
    private String loginInput;
    private String registerInput;

    public Controller(View view, Model model) {
        //Initialise Model
        this.model = model;

        source = this.model.getPoint(3);
        target = this.model.getPoint(13);
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
        view.addMenuButton("Start", e -> start());
        view.addMenuButton("Stop", e -> stop());
        view.addDifficultyEventListener((observableValue, number, t1) -> setDifficulty(t1));
        view.setMaxDifficulty(model.getMaxPathLength());



        view.addLeaderboardButton("Login", e -> login(loginInput) );
        view.addLoginTextField((observableValue, oldValue, newValue) -> setLoginTextField(newValue));
        view.addLeaderboardButton("Register", e -> register(registerInput) );
        view.addRegisterTextField((observableValue, oldValue, newValue) -> setRegisterTextField(newValue));

        //for demo purposes only - to be removed
        view.addMenuTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsTextField((observableValue, oldValue, newValue) -> test(newValue));
        view.addOptionsButton("test", e -> System.out.println("test button pressed"));


        populateGraph();
    }

    private void test(String input) {
        System.out.println(input);
    }

    private void setLoginTextField(String input) {

        loginInput = input;
    }
    private void setRegisterTextField(String input) {

        registerInput = input;
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
        view.setStart(source);
        view.setGoal(target);

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
        return (int) (round((1-(difference/optimalPath.getWeight())) * ((double) (difficulty - 2) / (double) (model.getMaxPathLength() - 2))*1000));
    }

    private void login(String givenUsername) {

        Player player;

        player = Leaderboard.loadPlayer(givenUsername);
        if (player == null){
            view.showErrorAlert("Input not valid", "This user does not exist. If you want to create a new user, please register.");
        }else{
            view.showInformationAlert("Login Successful", "You are now logged in as " + givenUsername);

        }
    }

    private void register(String givenUsername) {
        Player player;
        List<Player> players;
        givenUsername =givenUsername.replaceAll("\\s+","");

        player = Leaderboard.loadPlayer(givenUsername);
        players = Leaderboard.loadPlayers();

        if (players.contains(player) || givenUsername.isEmpty()){
            view.showErrorAlert("Input not valid", "This user already exists. Please enter a unique username.");
        }else{
            Leaderboard.addPlayer(givenUsername);
            view.showInformationAlert("Registration Successful", "You have now registered the account: " + givenUsername);
        }
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
        }
    }

    public void clickPoint(Point point) {
        Color selected, option, current, goal;
        selected = Color.GREEN;
        option = Color.SKYBLUE;
        current = Color.ORANGE;
        goal = Color.BLUE;

        view.setHighlightColor(target, goal);

        if (!path.isEmpty() && (path.getLast()==target || (path.contains(point) && !(path.getLast()==point)))) {
            return;
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
