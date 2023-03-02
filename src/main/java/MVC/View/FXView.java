package MVC.View;

import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FXView implements View{
    private final Stage stage;
    private GraphDisplay graph;

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

        root.getChildren().addAll(testButton, button, text, (Node) graph);

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
}