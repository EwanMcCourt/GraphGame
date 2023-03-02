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
    private final SimpleDoubleProperty centreY, centreX, radius, anchorY, anchorX;
    private final Circle highlightCircle, nodeCircle;
    private Boolean highlight = false;
    private final Map<Point,DisplayNode> neighbours = new HashMap<>();
    private final Map<Point,QuadCurve> connections = new HashMap<>();
    private final Map<Point,QuadCurve> highlightConnections = new HashMap<>();
    private final Map<Point,Boolean> highlightConnection = new HashMap<>();
    public DisplayNode(Point point, String text, SimpleDoubleProperty centreX, SimpleDoubleProperty centreY, SimpleDoubleProperty radius, SimpleDoubleProperty anchorX, SimpleDoubleProperty anchorY) {
        this.point = point;
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
        this.anchorX = anchorX;
        this.anchorY = anchorY;

        StackPane pane = new StackPane();
        pane.setPickOnBounds(false);

        pane.layoutXProperty().bind(centreX.subtract(pane.widthProperty().divide(2)));
        pane.layoutYProperty().bind(centreY.subtract(pane.heightProperty().divide(2)));

//        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        highlightCircle = new Circle();
        highlightCircle.radiusProperty().bind(radius.multiply(1.1));
        highlightCircle.setVisible(highlight);
        pane.getChildren().add(highlightCircle);

        nodeCircle = new Circle();
        nodeCircle.radiusProperty().bind(radius);
        pane.getChildren().add(nodeCircle);

        Text label = new Text(text);
        this.label = label;
        pane.getChildren().add(label);

        this.getChildren().add(pane);

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
    public Point getPoint() {
        return point;
    }
    public void addConnection(Point target, DisplayNode node, QuadCurve curve, QuadCurve highlightCurve) {
        neighbours.put(target, node);
        connections.put(target, curve);
        highlightConnections.put(target, highlightCurve);
        highlightConnection.put(target, false);
    }

    public void highlightConnection(Point target) {
        highlightConnection.put(target, true);
        highlightConnections.get(target).setVisible(highlightConnection.get(target));
    }
    public void highlightConnections() {
        for (QuadCurve curve : highlightConnections.values()) {
            curve.setVisible(highlight);
        }
    }

    public void print() {
        System.out.println("CentreX: "+centreX.getValue()+" centreY: "+centreY.getValue()+" radius: "+radius.getValue()+" anchorX: "+anchorX.getValue()+" anchorY: "+anchorY.getValue());
    }
}
