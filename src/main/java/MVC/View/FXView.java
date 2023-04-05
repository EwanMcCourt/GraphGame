package MVC.View;

import MVC.Model.Path;
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

import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.function.Consumer;

public class FXView implements View{
    private final Stage stage;
    private GraphDisplay graph;
    private VBox options, leaderboard, output;
    private HBox menu;
    private TextArea leaderboardTextArea;
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

        // Create graph display
        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());
        graph = new DisplayGraph(graphWidth, graphHeight);

        // Create Leaderboard
        leaderboardTextArea = new TextArea();
        leaderboardTextArea.setEditable(false);
        leaderboardTextArea.setMouseTransparent(true);
        leaderboardTextArea.setFocusTraversable(false);
        leaderboardTextArea.setPrefSize(5,500);//change to dynamically size
        leaderboardTextArea.setMaxWidth(200);
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

        // Adjust Sizing
        output.minHeightProperty().set(100);

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
    public void setIcon(String filename) {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
        stage.getIcons().add(icon);
    }
    @Override // Set Application Title
    public void setTitle(String title) {
        stage.setTitle(title);
    }
    @Override
    public void clearLeaderboard() {
        leaderboardTextArea.clear();
    }
    @Override
    public void populateLeaderboard(List<String> topPlayers) {
        leaderboardTextArea.appendText("LEADERBOARD:");
        for (String topPlayer : topPlayers) {
            leaderboardTextArea.appendText("\n" + topPlayer);
        }
    }
    @Override
    public void populateGraph(Set<Point> points) {
        graph.populateNodes(points);
    }
    @Override
    public void populateEventHandlers(Consumer<? super DisplayNode> clicked, Consumer<? super DisplayNode> hover, Consumer<? super DisplayNode> unhover) {
        for (DisplayNode node : graph.getDisplayNodes()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> clicked.accept(node));
            node.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> hover.accept(node));
            node.addEventFilter(MouseEvent.MOUSE_EXITED, e -> unhover.accept(node));
        }
    }
    @Override
    public void addConnection(Point point1, Point point2, int weight) {
        graph.addConnection(point1, point2, weight);
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
            graph.setConnectionHighlightColor(path.get(i), path.get(i + 1), ConnectionColour.SELECTED);
            graph.setHighlightColor(path.get(i), NodeColour.SELECTED);
            graph.highlightConnection(path.get(i), path.get(i + 1), true);
            graph.highlight(path.get(i), true);
        }
        graph.setHighlightColor(path.getLast(), NodeColour.SELECTED);
        graph.highlight(path.getLast(), true);
    }
    @Override
    public void showMoves(Path path) {
        if(path.isEmpty()) {
            return;
        }

        Point lastPoint = path.getLast();
        for (Point point : lastPoint.getNeighbours()) {
            if(!path.getPoints().contains(point)) {
                graph.setConnectionHighlightColor(lastPoint, point, ConnectionColour.AVAILABLE);
                graph.setHighlightColor(point, NodeColour.AVAILABLE);
                graph.highlightConnection(lastPoint, point, true);
                graph.highlight(point, true);
            }
        }
    }
    @Override
    public void showMoves(Point point) {
        graph.setTempHighlightColor(point, NodeColour.CURRENT);
        graph.tempHighlight(point, true);
        for (Point neighbour : point.getNeighbours()) {
            graph.setTempConnectionHighlightColor(point, neighbour, ConnectionColour.CURRENT);
            graph.setTempHighlightColor(neighbour, NodeColour.CURRENT);
            graph.tempHighlightConnection(point, neighbour, true);
            graph.tempHighlight(neighbour, true);
        }
    }
    @Override
    public void hideMoves(Point point) {
        graph.tempHighlight(point, false);
        for (Point neighbour : point.getNeighbours()) {
            graph.tempHighlightConnection(point, neighbour, false);
            graph.tempHighlight(neighbour, false);
        }
    }
    @Override
    public void setStart(Point point) {
        graph.setHighlightColor(point, NodeColour.START);
        graph.highlight(point,true);
    }
    @Override
    public void setGoal(Point point) {
        graph.setHighlightColor(point, NodeColour.GOAL);
        graph.highlight(point,true);
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
    public void addLeaderboardTextField(ChangeListener<String> eventHandler) {
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
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        if (!stage.getIcons().isEmpty()) {
            alertStage.getIcons().add(stage.getIcons().get(0));
        }
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