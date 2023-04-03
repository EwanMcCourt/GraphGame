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
    private final SimpleDoubleProperty anchorY;
    private final SimpleDoubleProperty anchorX;
    private final Circle tempHighlightCircle, highlightCircle, nodeCircle;
    private final Map<Point,QuadCurve> highlightConnections = new HashMap<>();
    private final Map<Point,QuadCurve> tempHighlightConnections = new HashMap<>();
    public DisplayNode(Point point, String text, SimpleDoubleProperty centreX, SimpleDoubleProperty centreY, SimpleDoubleProperty radius, SimpleDoubleProperty anchorX, SimpleDoubleProperty anchorY) {
        this.point = point;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        double HIGHLIGHT_SCALE_FACTOR = 1.1;

        StackPane pane = new StackPane();
        pane.setPickOnBounds(false);

        pane.layoutXProperty().bind(centreX.subtract(pane.widthProperty().divide(2)));
        pane.layoutYProperty().bind(centreY.subtract(pane.heightProperty().divide(2)));

        highlightCircle = new Circle();
        highlightCircle.radiusProperty().bind(radius.multiply(HIGHLIGHT_SCALE_FACTOR));
        highlightCircle.setVisible(false);
        pane.getChildren().add(highlightCircle);

        tempHighlightCircle = new Circle();
        tempHighlightCircle.radiusProperty().bind(radius.multiply(HIGHLIGHT_SCALE_FACTOR));
        tempHighlightCircle.setVisible(false);
        pane.getChildren().add(tempHighlightCircle);

        nodeCircle = new Circle();
        nodeCircle.radiusProperty().bind(radius);
        pane.getChildren().add(nodeCircle);

        Text label = new Text(text);
        pane.getChildren().add(label);

        this.getChildren().add(pane);
    }
    public void setColor(Color color) {
        nodeCircle.setFill(color);
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
    public void addConnection(Point target, QuadCurve highlightCurve, QuadCurve tempHighlightCurve) {
        highlightConnections.put(target, highlightCurve);
        tempHighlightConnections.put(target, tempHighlightCurve);
    }
    public void setHighlight(Boolean highlight) {
        highlightCircle.setVisible(highlight);
    }
    public void setHighlightColor(NodeColour color) {
        highlightCircle.setFill(color.color);
    }
    public void setConnectionHighlight(Point target, Boolean highlight) {
        highlightConnections.get(target).setVisible(highlight);
    }
    public void setConnectionHighlightColor(Point target, ConnectionColour color) {
        highlightConnections.get(target).setStroke(color.color);
    }
    public void setTempHighlight(Boolean highlight) {
        tempHighlightCircle.setVisible(highlight);
    }
    public void setTempHighlightColor(NodeColour color) {
        tempHighlightCircle.setFill(color.color);
    }
    public void setTempConnectionHighlight(Point target, Boolean highlight) {
        tempHighlightConnections.get(target).setVisible(highlight);
    }
    public void setTempConnectionHighlightColor(Point target, ConnectionColour color) {
        tempHighlightConnections.get(target).setStroke(color.color);
    }
    public void clearHighlights() {
        highlightCircle.setVisible(false);
        for (QuadCurve curve : highlightConnections.values()) {
            curve.setVisible(false);
        }
    }
}
