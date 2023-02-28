package MVC.View;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;

import java.util.*;

public class DisplayNode extends Group {
    private int index;
    private Text label;
    private SimpleDoubleProperty centreY;
    private SimpleDoubleProperty centreX;
    private SimpleDoubleProperty anchorY;
    private SimpleDoubleProperty anchorX;
    private Circle highlightCircle;
    private Boolean highlight = false;

    private Map<Integer,DisplayNode> neighbours = new HashMap<>();
    private Map<Integer,QuadCurve> connections = new HashMap<>();
    private Map<Integer,QuadCurve> highlightConnections = new HashMap<>();
    private Map<Integer,Boolean> highlightConnection = new HashMap<>();
    public DisplayNode(int index, String text, SimpleDoubleProperty centreX, SimpleDoubleProperty centreY, SimpleDoubleProperty radius, SimpleDoubleProperty anchorX, SimpleDoubleProperty anchorY) {
        this.index = index;
        this.centreX = centreX;
        this.centreY = centreY;
        this.anchorX = anchorX;
        this.anchorY = anchorY;

        StackPane pane = new StackPane();

        pane.layoutXProperty().bind(centreX.subtract(radius));
        pane.layoutYProperty().bind(centreY.subtract(radius));

        highlightCircle = new Circle();
        highlightCircle.radiusProperty().bind(radius.multiply(1.1));
//        highlightCircle.setFill(Color.TRANSPARENT);
        highlightCircle.setVisible(highlight);
        pane.getChildren().add(highlightCircle);

        Circle nodeCircle = new Circle();
        nodeCircle.radiusProperty().bind(radius);
        nodeCircle.setFill(Color.PINK);
        pane.getChildren().add(nodeCircle);

        Text label = new Text(text);
        this.label = label;
        pane.getChildren().add(label);

        this.getChildren().add(pane);

//        Circle nodeCircle = new Circle();
//        nodeCircle.radiusProperty().bind(radius);
//        nodeCircle.centerXProperty().bind(centreX);
//        nodeCircle.centerYProperty().bind(centreY);
//        group.getChildren().add(nodeCircle);

        Circle nodeAnchorCircle = new Circle();
        nodeAnchorCircle.radiusProperty().bind(radius.multiply(0.1));
        nodeAnchorCircle.centerXProperty().bind(anchorX);
        nodeAnchorCircle.centerYProperty().bind(anchorY);
        nodeAnchorCircle.setFill(Color.GREEN);
//        this.getChildren().add(nodeAnchorCircle);

        Circle centreCircle = new Circle(5.0, Color.BLUE);
        centreCircle.centerXProperty().bind(centreX);
        centreCircle.centerYProperty().bind(centreY);
    }

    public void setLabel(String text) {
        this.label.setText(text);
    }

    public String getLabel() {
        return label.getText();
    }

    public SimpleDoubleProperty getAnchorX() {
        return anchorX;
    }

    public SimpleDoubleProperty getAnchorY() {
        return anchorY;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
        highlightCircle.setVisible(highlight);
        highlightConnections();
    }
    public void toggleHighlight() {
        setHighlight(!highlight);
    }
    public void setHighlightColor(Color color) {
        highlightCircle.setFill(color);
    }
    public int getIndex() {
        return index;
    }
    public void addConnection(int target, DisplayNode node, QuadCurve curve, QuadCurve highlightCurve) {
        neighbours.put(target, node);
        connections.put(target, curve);
        highlightConnections.put(target, highlightCurve);
        highlightConnection.put(target, false);
    }

    public void highlightConnection(int target) {
        highlightConnection.put(target, true);
        highlightConnections.get(target).setVisible(highlightConnection.get(target));
    }
    public void highlightConnections() {
        for (QuadCurve curve : highlightConnections.values()) {
            curve.setVisible(highlight);
        }
    }
}
