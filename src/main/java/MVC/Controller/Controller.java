package MVC.Controller;

import MVC.View.View;
import MVC.Model.Model;

// Integral to game function
import MVC.Model.Path;
import MVC.Model.Point;
import MVC.Model.Player;

import java.io.IOException;

import static MVC.Model.Leaderboard.savePlayers;
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
    private String loginInput, registerInput;
    private Player player;
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
        view.populateLeaderboard(model.getTopTenPlayers());
        view.addLeaderboardButton("Login", e -> login(loginInput));
        view.addLeaderboardTextField((observableValue, oldValue, newValue) -> loginInput = newValue);
        view.addLeaderboardButton("Register", e -> register(registerInput));
        view.addLeaderboardTextField((observableValue, oldValue, newValue) -> registerInput = newValue);

        populateGraph();
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
        view.clearPathView();
        view.showPathView(selectedPath, "Selected Path:");

        // Highlight Start And End Points
        view.setStart(source);
        view.setGoal(target);
    }
    private void stop(){
        gameOngoing = false;
        view.clearHighlights();
        view.clearPathView();

    }
    private void finishGame() {
        view.showPathView(selectedPath, "Your Path:");
        view.showPathView(optimalPath, "Optimal Path:");
        String message = String.format("Your path had a weight of %d, the most optimal path has a weight of %d.\nYou got a score of %d", selectedPath.getWeight().intValue(), optimalPath.getWeight().intValue(), getScore());
        view.showInformationAlert("Congratulations!", message);
        if (player != null){
            player.updateHighScore(getScore());
            savePlayers();
        }


    }
    private int getScore() {
        Double difference = Double.max(selectedPath.getWeight() - optimalPath.getWeight(), 0);
        return Integer.max((int) (round((1-(difference/optimalPath.getWeight())) * ((double) (difficulty - 2) / (double) (model.getMaxPathLength() - 2))*1000)),0);
    }
    private void login(String givenUsername) {


        player = model.loadPlayer(givenUsername);
        if (player == null){
            view.showErrorAlert("Input not valid", "This user does not exist. If you want to create a new user, please register.");
        }else{
            view.showInformationAlert("Login Successful", "You are now logged in as " + givenUsername);
        }
    }
    private void register(String givenUsername) {
        givenUsername = givenUsername.replaceAll("\\s+","");

        if (model.loadPlayers().contains(model.loadPlayer(givenUsername)) || givenUsername.isEmpty()){
            view.showErrorAlert("Input not valid", "This user already exists. Please enter a unique username.");
        }else{
            model.addPlayer(givenUsername);
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

        // Using Lambdas means that Controller is completely decoupled from JavaFX
        view.populateEventHandlers(
                clicked -> clickPoint(clicked.getPoint()),
                hover -> hoverPoint(hover.getPoint(), true),
                unhover -> hoverPoint(unhover.getPoint(), false)
                );
    }
    public void hoverPoint(Point point, Boolean hover) {
        // Check if game is active
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
        view.clearPathView();
        if(gameOngoing) {
            view.showMoves(selectedPath);
            view.showPathView(selectedPath, "Selected Path:");
        } else {
            finishGame();
        }

        // Keep start and end highlighted
        view.setStart(source);
        view.setGoal(target);
    }
}
