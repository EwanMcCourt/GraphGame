package MVC.View;

import MVC.Model.Path;
import MVC.Model.Leaderboard;
import MVC.Model.Player;
import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class FXView implements View{
    private final Stage stage;
    private DisplayGraph graph;
    private VBox options, leaderboard;
    private HBox menu;
    private Slider difficulty;
    private TextArea leaderboardTextArea;
    private ArrayList topPlayers;

    public FXView(Stage stage) {
        this.stage = stage;
    }
    public void initialise() {
        // initialise layouts
        BorderPane root = new BorderPane();
        options =  new VBox();
        leaderboard =  new VBox();
        menu = new HBox();
//        Scene scene = new Scene(root, 700, 700, Color.INDIGO);
        Scene scene = new Scene(root, Color.INDIGO);

        // Create graph display
        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());
        graph = new DisplayGraph(graphWidth, graphHeight);

        //---------Leaderboard---------
        TextField loginText = new TextField();
        loginText.setMaxWidth(100);
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> login(loginText.getText()));

        TextField registerText = new TextField();
        registerText.setMaxWidth(100);
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> register(registerText.getText()));


        leaderboardTextArea = new TextArea();
        leaderboardTextArea.setEditable(false);
        leaderboardTextArea.setMouseTransparent(true);
        leaderboardTextArea.setFocusTraversable(false);
        leaderboardTextArea.appendText("LEADERBOARD:");
        topPlayers = Leaderboard.getTopTenPlayers();
        for (int i =0; i<topPlayers.size(); i++){
            leaderboardTextArea.appendText("\n"+topPlayers.get(i));

        }
        leaderboardTextArea.setPrefSize(5,500);//change to dynamically size
        leaderboardTextArea.setMaxWidth(100);
        leaderboardTextArea.setMaxHeight(500);
        //---------Leaderboard---------

        //Difficulty Slider
        difficulty = new Slider();
        difficulty.setShowTickMarks(true);
        difficulty.setBlockIncrement(1.0);
        difficulty.setSnapToTicks(true);
        difficulty.setMajorTickUnit(5);
        difficulty.setMinorTickCount(4);
        // https://stackoverflow.com/questions/38681664/how-to-make-javafx-slider-to-move-in-discrete-steps
        difficulty.valueProperty().addListener((observableValue, number, t1) ->
                difficulty.setValue(Math.round(t1.doubleValue())));
        Text difficultyLabel = new Text("Difficulty");
        Text currentDifficulty = new Text();
        currentDifficulty.textProperty().bind(difficulty.valueProperty().asString());

        //add to layouts
        options.getChildren().addAll(difficultyLabel, difficulty, currentDifficulty);
        leaderboard.getChildren().addAll(leaderboardTextArea,loginButton,loginText,registerButton, registerText);
        menu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        options.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leaderboard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //set positions
        root.setTop(menu);
        root.setCenter(graph);
        root.setRight(leaderboard);
        root.setLeft(options);

        stage.setScene(scene);
        stage.show();
    }

    public void populateGraph(Set<Point> points) {
        graph.populateNodes(points);
    }

    public Collection<DisplayNode> getNodes() {
        return graph.getDisplayNodes();
    }

    // Set Application Icon
    public void setIcon(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        Image icon = new Image(inputStream);
        stage.getIcons().add(icon);
    }

    // Set Application Icon
    public void setTitle(String title) {
        stage.setTitle(title);
    }

    // Probably remove
    public void addNode() {

    }
    // Probably remove
    public void removeNode() {

    }

    public void highlightNode(Point point, Boolean active) {
        graph.highlight(point, active);
    }
    public void setHighlightColor(Point point, Color color) {
        graph.setHighlightColor(point, color);
    }
    public Boolean isHighlighted(Point point1) {
        return graph.isHighlighted(point1);
    }
    public void highlightConnection(Point point1, Point point2, Boolean active) {
        graph.highlightConnection(point1, point2, active);
    }
    public void setConnectionHighlightColor(Point point1, Point point2, Color color) {
        graph.setConnectionHighlightColor(point1, point2, color);
    }
    public Boolean isConnectionHighlighted(Point point1, Point point2) {
        return graph.isConnectionHighlighted(point1, point2);
    }

    public void addConnection(Point point1, Point point2, int weight) {
        graph.addConnection(point1, point2, weight);
    }

    public void displayPath(Path path) {
        Color connectionColor = Color.RED;
        Color pointColor = Color.RED;

        for (int i = 0; i< path.size()-1; i++) {
            setConnectionHighlightColor(path.get(i), path.get(i+1), connectionColor);
            setHighlightColor(path.get(i), pointColor);
            highlightConnection(path.get(i), path.get(i+1), true);
            highlightNode(path.get(i), true);
        }
        setHighlightColor(path.getLast(), pointColor);
        highlightNode(path.getLast(), true);
    }

    public void addMenuButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        menu.getChildren().add(button);
    }

    public void addMenuTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        menu.getChildren().add(textField);
    }

    public void addOptionsButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        options.getChildren().add(button);
    }

    public void addOptionsTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        options.getChildren().add(textField);
    }

    public void setMaxDifficulty(int maxDifficulty) {
        int newDifficulty = maxDifficulty-2;
        if (difficulty.getValue()>newDifficulty){
            difficulty.setValue(newDifficulty);
        }
        difficulty.setMax(newDifficulty);
    }

    public void addDifficultyEventListener(ChangeListener<Number> eventHandler) {
        difficulty.valueProperty().addListener(eventHandler);
    }

    public void login(String givenUsername) {

        Player player;
        Alert alert;

        player = Leaderboard.loadPlayer(givenUsername);
        if (player == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input not valid");
            alert.setContentText("This user does not exist. If you want to create a new user, please register.");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login Successful");
            alert.setContentText("You are now logged in as " + givenUsername);
            alert.show();

        }
    }

    public void register(String givenUsername) {
        Player player;
        Alert alert;
        List<Player> players;
        givenUsername =givenUsername.replaceAll("\\s+","");
        System.out.println(givenUsername);
        player = Leaderboard.loadPlayer(givenUsername);
        players = Leaderboard.loadPlayers();

        if (players.contains(player) || givenUsername.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input not valid");
            alert.setContentText("This user already exists. Please enter a unique username.");
            alert.showAndWait();
        }else{
            Leaderboard.addPlayer(givenUsername);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Registration Successful");
            alert.setContentText("You have now registered the account: " + givenUsername);
            alert.show();

        }
    }
}