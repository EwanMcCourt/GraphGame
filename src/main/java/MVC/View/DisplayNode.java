package MVC.View;

import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;

import java.util.*;

public class DisplayNode extends Group {
    private final Point point;
    private final Text label;
    private final SimpleDoubleProperty anchorY;
    private final SimpleDoubleProperty anchorX;
    private final Circle tempHighlightCircle, highlightCircle, nodeCircle;
    private final Map<Point,DisplayNode> neighbours = new HashMap<>();
    private final Map<Point,QuadCurve> connections = new HashMap<>();
    private final Map<Point,QuadCurve> highlightConnections = new HashMap<>();
    private final Map<Point,QuadCurve> tempHighlightConnections = new HashMap<>();
    public DisplayNode(Point point, String text, SimpleDoubleProperty centreX, SimpleDoubleProperty centreY, SimpleDoubleProperty radius, SimpleDoubleProperty anchorX, SimpleDoubleProperty anchorY) {
        this.point = point;
        this.anchorX = anchorX;
        this.anchorY = anchorY;

        StackPane pane = new StackPane();
        pane.setPickOnBounds(false);

        pane.layoutXProperty().bind(centreX.subtract(pane.widthProperty().divide(2)));
        pane.layoutYProperty().bind(centreY.subtract(pane.heightProperty().divide(2)));

        highlightCircle = new Circle();
        highlightCircle.radiusProperty().bind(radius.multiply(1.1));
        highlightCircle.setVisible(false);
        pane.getChildren().add(highlightCircle);

        tempHighlightCircle = new Circle();
        tempHighlightCircle.radiusProperty().bind(radius.multiply(1.1));
        tempHighlightCircle.setVisible(false);
        pane.getChildren().add(tempHighlightCircle);

        nodeCircle = new Circle();
        nodeCircle.radiusProperty().bind(radius);
        pane.getChildren().add(nodeCircle);

        Text label = new Text(text);
        this.label = label;
        pane.getChildren().add(label);

        this.getChildren().add(pane);
    }

    public void setColor(Color color) {
        nodeCircle.setFill(color);
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
    public Point getPoint() {
        return point;
    }
    public void addConnection(Point target, DisplayNode node, QuadCurve curve, QuadCurve highlightCurve, QuadCurve tempHighlightCurve) {
        neighbours.put(target, node);
        connections.put(target, curve);
        highlightConnections.put(target, highlightCurve);
        tempHighlightConnections.put(target, tempHighlightCurve);
    }
    public void setHighlight(Boolean highlight) {
        highlightCircle.setVisible(highlight);
    }
    public void toggleHighlight() {
        setHighlight(!highlightCircle.isVisible());
    }
    public void setHighlightColor(NodeColour color) {
        highlightCircle.setFill(color.color);
    }
    public Boolean isHighlighted() {
        return highlightCircle.isVisible();
    }
    public void highlightConnections() {
        for (QuadCurve curve : highlightConnections.values()) {
            curve.setVisible(true);
        }
    }
    public void setConnectionHighlight(Point target, Boolean highlight) {
        highlightConnections.get(target).setVisible(highlight);
    }
    public void toggleConnectionHighlight(Point target) {
        setConnectionHighlight(target, !highlightConnections.get(target).isVisible());
    }
    public void setConnectionHighlightColor(Point target, ConnectionColour color) {
        highlightConnections.get(target).setStroke(color.color);
    }
    public Boolean isConnectionHighlighted(Point target) {
        return highlightConnections.get(target).isVisible();
    }

    public void setTempHighlight(Boolean highlight) {
        tempHighlightCircle.setVisible(highlight);
    }
    public void toggleTempHighlight() {
        setHighlight(!tempHighlightCircle.isVisible());
    }
    public void setTempHighlightColor(NodeColour color) {
        tempHighlightCircle.setFill(color.color);
    }
    public Boolean isTempHighlighted() {
        return tempHighlightCircle.isVisible();
    }
    public void setTempConnectionHighlight(Point target, Boolean highlight) {
        tempHighlightConnections.get(target).setVisible(highlight);
    }
    public void toggleTempConnectionHighlight(Point target) {
        setConnectionHighlight(target, !tempHighlightConnections.get(target).isVisible());
    }
    public void setTempConnectionHighlightColor(Point target, ConnectionColour color) {
        tempHighlightConnections.get(target).setStroke(color.color);
    }
    public Boolean isTempConnectionHighlighted(Point target) {
        return tempHighlightConnections.get(target).isVisible();
    }

    public void clearHighlights() {
        highlightCircle.setVisible(false);
        for (QuadCurve curve : highlightConnections.values()) {
            curve.setVisible(false);
        }
    }
}
