package MVC.View;

import MVC.Model.Path;
import MVC.Model.Leaderboard;
import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FXView implements View{
    private final Stage stage;
    private GraphDisplay graph;
    private VBox options, leaderboard, output;
    private HBox menu;
    private Slider difficulty;
    public FXView(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialise() {
        // initialise layouts
        BorderPane root = new BorderPane();
        options =  new VBox();
        leaderboard =  new VBox();
        output =  new VBox();
        menu = new HBox();
        Scene scene = new Scene(root, Color.INDIGO);

        // Adjust Sizing
        output.minHeightProperty().set(100);

        // Create graph display
        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());
        graph = new DisplayGraph(graphWidth, graphHeight);

        // Create Leaderboard
        TextArea leaderboardTextArea = new TextArea();
        leaderboardTextArea.setEditable(false);
        leaderboardTextArea.setMouseTransparent(true);
        leaderboardTextArea.setFocusTraversable(false);
        leaderboardTextArea.appendText("LEADERBOARD:");
        ArrayList topPlayers = Leaderboard.getTopTenPlayers();
        for (int i = 0; i< topPlayers.size(); i++){
            leaderboardTextArea.appendText("\n"+ topPlayers.get(i));

        }
        leaderboardTextArea.setPrefSize(5,500);//change to dynamically size
        leaderboardTextArea.setMaxWidth(100);
        leaderboardTextArea.setMaxHeight(500);

        //Difficulty Slider
        difficulty = new Slider();
        difficulty.setMin(3.0);
        difficulty.setShowTickMarks(true);
        difficulty.setBlockIncrement(1.0);
        difficulty.setSnapToTicks(true);
        difficulty.setMajorTickUnit(5);
        difficulty.setMinorTickCount(4);
        // https://stackoverflow.com/questions/38681664/how-to-make-javafx-slider-to-move-in-discrete-steps
        difficulty.valueProperty().addListener((observableValue, number, t1) -> difficulty.setValue(Math.round(t1.doubleValue())));
        Text difficultyLabel = new Text("Difficulty");
        Text currentDifficulty = new Text();
        currentDifficulty.textProperty().bind(difficulty.valueProperty().asString());

        //add to layouts
        options.getChildren().addAll(difficultyLabel, difficulty, currentDifficulty);
        leaderboard.getChildren().addAll(leaderboardTextArea);

        // Borders (Mostly For Debugging)
        menu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        options.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leaderboard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        output.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //set positions
        root.setTop(menu);
        root.setCenter((Node) graph);
        root.setRight(leaderboard);
        root.setLeft(options);
        root.setBottom(output);

        // Display The Stage
        stage.setScene(scene);
        stage.show();
    }
    @Override // Set Application Icon
    public void setIcon(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        Image icon = new Image(inputStream);
        stage.getIcons().add(icon);
    }
    @Override // Set Application Title
    public void setTitle(String title) {
        stage.setTitle(title);
    }
    @Override
    public void populateGraph(Set<Point> points) {
        graph.populateNodes(points);
    }
    @Override
    public void populateEventHandlers(Consumer<? super DisplayNode> clicked, Consumer<? super DisplayNode> hover, Consumer<? super DisplayNode> unhover) {
        for (DisplayNode node : getNodes()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> clicked.accept(node));
            node.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> hover.accept(node));
            node.addEventFilter(MouseEvent.MOUSE_EXITED, e -> unhover.accept(node));
        }
    }
    @Override
    public Collection<DisplayNode> getNodes() {
        return graph.getDisplayNodes();
    }
    @Override
    public void addConnection(Point point1, Point point2, int weight) {
        graph.addConnection(point1, point2, weight);
    }
    @Override
    public void highlightNode(Point point, Boolean active) {
        graph.highlight(point, active);
    }
    @Override
    public void setHighlightColor(Point point, NodeColour color) {
        graph.setHighlightColor(point, color);
    }
    @Override
    public Boolean isHighlighted(Point point1) {
        return graph.isHighlighted(point1);
    }
    @Override
    public void highlightConnection(Point point1, Point point2, Boolean active) {
        graph.highlightConnection(point1, point2, active);
    }
    @Override
    public void setConnectionHighlightColor(Point point1, Point point2, ConnectionColour color) {
        graph.setConnectionHighlightColor(point1, point2, color);
    }
    @Override
    public Boolean isConnectionHighlighted(Point point1, Point point2) {
        return graph.isConnectionHighlighted(point1, point2);
    }
    @Override
    public void tempHighlightNode(Point point, Boolean active) {
        graph.tempHighlight(point, active);
    }
    @Override
    public void setTempHighlightColor(Point point, NodeColour color) {
        graph.setTempHighlightColor(point, color);
    }
    @Override
    public Boolean isTempHighlighted(Point point1) {
        return graph.isTempHighlighted(point1);
    }
    @Override
    public void tempHighlightConnection(Point point1, Point point2, Boolean active) {
        graph.tempHighlightConnection(point1, point2, active);
    }
    @Override
    public void setTempConnectionHighlightColor(Point point1, Point point2, ConnectionColour color) {
        graph.setTempConnectionHighlightColor(point1, point2, color);
    }
    @Override
    public Boolean isTempConnectionHighlighted(Point point1, Point point2) {
        return graph.isTempConnectionHighlighted(point1, point2);
    }
    @Override
    public void clearHighlights() {
        graph.clearHighlights();
    }
    @Override
    public void displayPath(Path path) {
        if (path.isEmpty()) {
            return;
        }

        for (int i = 0; i < path.size() - 1; i++) {
            setConnectionHighlightColor(path.get(i), path.get(i + 1), ConnectionColour.SELECTED);
            setHighlightColor(path.get(i), NodeColour.SELECTED);
            highlightConnection(path.get(i), path.get(i + 1), true);
            highlightNode(path.get(i), true);
        }
        setHighlightColor(path.getLast(), NodeColour.SELECTED);
        highlightNode(path.getLast(), true);
    }
    @Override
    public void showMoves(Path path) {
        if(path.isEmpty()) {
            return;
        }

        Point lastPoint = path.getLast();
        for (Point point : lastPoint.getNeighbours()) {
            if(!path.getPoints().contains(point)) {
                setConnectionHighlightColor(lastPoint, point, ConnectionColour.AVAILABLE);
                setHighlightColor(point, NodeColour.AVAILABLE);
                highlightConnection(lastPoint, point, true);
                highlightNode(point, true);
            }
        }
    }
    @Override
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
    @Override
    public void hideMoves(Point point) {
        tempHighlightNode(point, false);
        for (Point neighbour : point.getNeighbours()) {
            tempHighlightConnection(point, neighbour, false);
            tempHighlightNode(neighbour, false);
        }
    }
    @Override
    public void setStart(Point point) {
        setHighlightColor(point, NodeColour.START);
        highlightNode(point,true);
    }
    @Override
    public void setGoal(Point point) {
        setHighlightColor(point, NodeColour.GOAL);
        highlightNode(point,true);
    }
    @Override
    public void showPathView(Path path, String label) {
        output.getChildren().add(new DisplayPath(path, label));
    }
    @Override
    public void clearPathView() {
        output.getChildren().clear();
    }
    @Override
    public void addLeaderboardButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        leaderboard.getChildren().add(button);
    }
    @Override
    public void addLoginTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        leaderboard.getChildren().add(textField);

    }
    @Override
    public void addRegisterTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        leaderboard.getChildren().add(textField);

    }
    @Override
    public void addMenuButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        menu.getChildren().add(button);
    }
    @Override
    public void addMenuTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        menu.getChildren().add(textField);
    }
    @Override
    public void addOptionsButton(String label, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(label);
        button.setOnAction(eventHandler);
        options.getChildren().add(button);
    }
    @Override
    public void addOptionsTextField(ChangeListener<String> eventHandler) {
        TextField textField = new TextField();
        textField.textProperty().addListener(eventHandler);
        options.getChildren().add(textField);
    }
    @Override
    public void setMaxDifficulty(int maxDifficulty) {
        if (difficulty.getValue() > maxDifficulty) {
            difficulty.setValue(maxDifficulty);
        }
        difficulty.setMax(maxDifficulty);
    }
    @Override
    public void addDifficultyEventListener(ChangeListener<Number> eventHandler) {
        difficulty.valueProperty().addListener(eventHandler);
    }
    private void showAlert(String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    @Override
    public void showInformationAlert(String header, String content) {
        showAlert(header, content, Alert.AlertType.INFORMATION);
    }
    @Override
    public void showErrorAlert(String header, String content) {
        showAlert(header, content, Alert.AlertType.ERROR);
    }
}