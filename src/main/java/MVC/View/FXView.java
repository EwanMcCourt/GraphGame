package MVC.View;

import MVC.Model.Leaderboard;
import MVC.Model.Player;
import javafx.application.Platform;
import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FXView implements View{
    private final Stage stage;
    private GraphDisplay graph;
    private TextArea leaderboardTextArea;
    private TextField loginTextField;
    private TextField registerTextField;
    private ArrayList topPlayers;

    public FXView(Stage stage) {
        this.stage = stage;
    }
    public void initialise() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 700, 700, Color.INDIGO);

        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());

        graph = new DisplayGraph(graphWidth, graphHeight);
//        graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        TextField text = new TextField();
        text.setMaxWidth(100);
        Button button = new Button("Populate");
        EventHandler<ActionEvent> eventHandler = e -> {
            Set<Point> points = new HashSet<>();
            for (int i=0; i<Integer.parseInt(text.getText()); i++) {
                points.add(new Point(i));
            }
            graph.populateNodes(points);
        };
        button.setOnAction(eventHandler);

        Button testButton = new Button("Test");
        testButton.setOnAction(e -> test());


        //---------Leaderboard---------






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
        difficulty.setMin(3.0);
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
        leaderboard.getChildren().addAll(leaderboardTextArea);
        menu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        options.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leaderboard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        output.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));



        root.getChildren().addAll(testButton,button, text, loginButton,loginText,registerButton, registerText,(Node) graph,leaderboardTextArea);

        stage.setScene(scene);
        stage.show();
    }

    public void populateGraph(Set<Point> points) {
        graph.populateNodes(points);
    }

    public Collection<DisplayNode> getNodes() {
        return graph.getDisplayNodes();
    }

    public void setIcon(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        Image icon = new Image(inputStream);
        stage.getIcons().add(icon);
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public void addNode() {

    }

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

    public void test() {
        for (DisplayNode p1 : graph.getDisplayNodes()) {
            for (DisplayNode p2 : graph.getDisplayNodes()) {
                graph.addConnection(p1.getPoint(), p2.getPoint(), 0);
            }
        }
    }

    public void showMoves(Point point) {
        setTempHighlightColor(point, NodeColour.CURRENT);
        tempHighlightNode(point, true);
        for (Point neighbour : point.getNeighbours()) {
                setTempConnectionHighlightColor(point, neighbour, ConnectionColour.CURRENT);
                setTempHighlightColor(neighbour, NodeColour.CURRENT);
                tempHighlightConnection(point, neighbour, true);
                tempHighlightNode(neighbour, true);
        }
    }

    public void hideMoves(Point point) {
        tempHighlightNode(point, false);
        for (Point neighbour : point.getNeighbours()) {
            tempHighlightConnection(point, neighbour, false);
            tempHighlightNode(neighbour, false);
        }
    }

    public void setStart(Point point) {
        setHighlightColor(point, NodeColour.START);
        highlightNode(point,true);
    }

    public void setGoal(Point point) {
        setHighlightColor(point, NodeColour.GOAL);
        highlightNode(point,true);
    }

    public void addLeaderboardButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        leaderboard.getChildren().add(button);
    }
    public void addLoginTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        leaderboard.getChildren().add(textField);

    }

    public void addRegisterTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        leaderboard.getChildren().add(textField);

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
        if (difficulty.getValue()>maxDifficulty){
            difficulty.setValue(maxDifficulty);
        }
        difficulty.setMax(maxDifficulty);
    }

    }




    private void showAlert(String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public void showInformationAlert(String header, String content) {
        showAlert(header, content, Alert.AlertType.INFORMATION);
    }

        }
    }
}