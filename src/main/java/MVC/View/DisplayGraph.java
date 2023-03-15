package MVC.View;

import MVC.Model.Point;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

import java.util.*;

public class DisplayGraph extends Pane implements GraphDisplay {
    private final Map<Point, DisplayNode> displayNodes = new HashMap<>();
    private final SimpleDoubleProperty centreX, centreY;
    private final SimpleIntegerProperty numNodes = new SimpleIntegerProperty();
    private final static SimpleDoubleProperty NODE_RADIUS_SCALE_FACTOR = new SimpleDoubleProperty(0.95);
    private final static SimpleDoubleProperty RING_RADIUS_SCALE_FACTOR = new SimpleDoubleProperty(0.95);
    // https://math.stackexchange.com/questions/2438719/is-there-a-formula-for-the-big-radius-of-an-evenly-spaced-ring-of-circles
    private final DoubleBinding RING_RADIUS = new DoubleBinding() {
        {bind(numNodes);}
        @Override
        protected double computeValue() {return 1.0 / (2.0 + 2.0 * Math.sin(Math.PI / numNodes.getValue()));}
    };
    private final DoubleBinding NODE_RADIUS_BOUNDARY = new DoubleBinding() {
        {bind(numNodes);}
        @Override
        protected double computeValue() {return 1.0 / (2.0 * (1.0 + (1.0 / Math.sin(Math.PI / numNodes.getValue()))));}
    };
    private final static double CENTRE_POINT_RADIUS_SCALE_FACTOR = 0.2;

    public DisplayGraph(SimpleDoubleProperty width, SimpleDoubleProperty height) {
        int minimumSize = 400;
        this.prefWidthProperty().bind(Bindings.max(width,minimumSize));
        this.prefHeightProperty().bind(Bindings.max(height,minimumSize));
        this.minWidthProperty().set(minimumSize);
        this.minHeightProperty().set(minimumSize);

        centreX = new SimpleDoubleProperty();
        centreY = new SimpleDoubleProperty();
        centreX.bind(this.widthProperty().multiply(0.5));
        centreY.bind(this.heightProperty().multiply(0.5));
    }

    public void populateNodes(Set<Point> points) {
        this.getChildren().clear();
        this.displayNodes.clear();
        Color fillColour = Color.PINK;

        SimpleDoubleProperty radius = new SimpleDoubleProperty();
        radius.bind(Bindings.min(this.widthProperty(), this.heightProperty()).multiply(RING_RADIUS).multiply(RING_RADIUS_SCALE_FACTOR));

        SimpleDoubleProperty nodeRadius = new SimpleDoubleProperty();
        nodeRadius.bind(Bindings.min(this.widthProperty(), this.heightProperty()).multiply(NODE_RADIUS_BOUNDARY).multiply(NODE_RADIUS_SCALE_FACTOR).multiply(RING_RADIUS_SCALE_FACTOR));

        Circle centrePoint = new Circle();
        centrePoint.centerXProperty().bind(centreX);
        centrePoint.centerYProperty().bind(centreY);
        centrePoint.radiusProperty().bind(nodeRadius.multiply(CENTRE_POINT_RADIUS_SCALE_FACTOR));
        centrePoint.setFill(Color.ORANGE);
//        this.getChildren().add(centrePoint);

        Iterator<Point> pointIterator = points.iterator();

        if(points.size() == 1) {
            SimpleDoubleProperty singleNodeRadius = new SimpleDoubleProperty();
            singleNodeRadius.bind(Bindings.min(this.widthProperty(), this.heightProperty()).multiply(0.5).multiply(NODE_RADIUS_SCALE_FACTOR));
            Point point = pointIterator.next();
            DisplayNode node = new DisplayNode(point, String.valueOf(point.getIndex()), centreX, centreY, singleNodeRadius, centreX, centreY);
            displayNodes.put(point,node);
            this.getChildren().add(node);
        } else {
            for(int i=0; i< points.size(); i++) {
                // https://math.stackexchange.com/questions/4459356/find-n-evenly-spaced-points-on-circle-with-radius-r
//                double theta = (i*((2*Math.PI)/points.size()))-(Math.PI/2);
                double theta = (i*((2*Math.PI)/points.size()));
                SimpleDoubleProperty nodeCentreX = new SimpleDoubleProperty();
                SimpleDoubleProperty nodeCentreY = new SimpleDoubleProperty();
                nodeCentreX.bind(centreX.add(radius.multiply(Math.cos(theta))));
                nodeCentreY.bind(centreY.add(radius.multiply(Math.sin(theta))));

                // https://math.stackexchange.com/questions/2045174/how-to-find-a-point-between-two-points-with-given-distance
                // https://stackoverflow.com/questions/34270380/bind-property-to-cosine-javafx
                DoubleBinding nodeAnchorXBinding = new DoubleBinding() {
                    {
                        bind(centreX);
                        bind(radius);
                        bind(nodeCentreX);
                        bind(nodeRadius);
                    }
                    @Override
                    protected double computeValue() {
                        double x1 = nodeCentreX.get();
                        double x2 = centreX.get();
                        double D = radius.get();
                        double d = nodeRadius.get();

                        return x1 + ((d/D)*(x2-x1));
//                        return centreX.get() + ((radius.get()-nodeRadius.get()))*Math.cos(Math.acos((centreX.get()-nodeCentreX.get())/radius.get()));
                    }
                };
                DoubleBinding nodeAnchorYBinding = new DoubleBinding() {
                    {
                        bind(centreY);
                        bind(radius);
                        bind(nodeCentreY);
                        bind(nodeRadius);
                    }
                    @Override
                    protected double computeValue() {
                        double y1 = nodeCentreY.get();
                        double y2 = centreY.get();
                        double D = radius.get();
                        double d = nodeRadius.get();

                        return y1 + ((d/D)*(y2-y1));
//                        return centreY.get() + ((radius.get()-nodeRadius.get()))*Math.cos(Math.acos((centreY.get()-nodeCentreY.get())/radius.get()));
                    }
                };

                SimpleDoubleProperty nodeAnchorX = new SimpleDoubleProperty();
                SimpleDoubleProperty nodeAnchorY = new SimpleDoubleProperty();
                nodeAnchorX.bind(nodeAnchorXBinding);
                nodeAnchorY.bind(nodeAnchorYBinding);

                Point point = pointIterator.next();

                DisplayNode node = new DisplayNode(point, String.valueOf(point.getIndex()), nodeCentreX, nodeCentreY, nodeRadius, nodeAnchorX, nodeAnchorY);
                node.setColor(fillColour);

                displayNodes.put(point, node);
                this.getChildren().addAll(node);
            }
        }
        update();
    }

    public Collection<DisplayNode> getDisplayNodes() {
        return displayNodes.values();
    }

    private void update() {
        numNodes.set(displayNodes.size());
    }

    public void addConnection(Point point1, Point point2, int weight) {
        QuadCurve curve = createCurve(point1, point2);

        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(getWeightColor(weight));

        QuadCurve highlightCurve = createCurve(point1, point2);
        highlightCurve.setFill(Color.TRANSPARENT);
        highlightCurve.setStroke(Color.SKYBLUE);
        highlightCurve.setStrokeWidth(5.0);
        highlightCurve.setVisible(false);

        displayNodes.get(point1).addConnection(point2, displayNodes.get(point2), curve, highlightCurve);
        displayNodes.get(point2).addConnection(point1, displayNodes.get(point1), curve, highlightCurve);

        this.getChildren().addAll(highlightCurve, curve);
    }

    private QuadCurve createCurve(Point point1, Point point2) {
        QuadCurve curve = new QuadCurve();

        curve.controlXProperty().bind(centreX);
        curve.controlYProperty().bind(centreY);

        curve.startXProperty().bind(displayNodes.get(point1).getAnchorX());
        curve.startYProperty().bind(displayNodes.get(point1).getAnchorY());

        curve.endXProperty().bind(displayNodes.get(point2).getAnchorX());
        curve.endYProperty().bind(displayNodes.get(point2).getAnchorY());

        return curve;
    }

    public Color getWeightColor(int weight) {
        ArrayList<Color> colors = new ArrayList<>();
        if (weight > 10) {
            return Color.BLACK;
        }
        double opacity = 0.5;
        colors.add(Color.rgb(120, 250, 110, opacity));
        colors.add(Color.rgb(114,236,73, opacity));
        colors.add(Color.rgb(164,221,28, opacity));
        colors.add(Color.rgb(182,205,0, opacity));
        colors.add(Color.rgb(198,188,0, opacity));
        colors.add(Color.rgb(202,169,0, opacity));
        colors.add(Color.rgb(224,149,0, opacity));
        colors.add(Color.rgb(235,127,0, opacity));
        colors.add(Color.rgb(244,102,0, opacity));
        colors.add(Color.rgb(254,70,0, opacity));
        colors.add(Color.rgb(255,5,5, opacity));

        return colors.get(weight);
    }

    public void highlight(Point point, Boolean active) {
        displayNodes.get(point).setHighlight(active);
    }
    public void setHighlightColor(Point point, Color color) {
        displayNodes.get(point).setHighlightColor(color);
    }

    public Boolean isHighlighted(Point point) {
        return displayNodes.get(point).isHighlighted();
    }
    public void highlightConnection(Point point1, Point point2, Boolean active) {
        displayNodes.get(point1).setConnectionHighlight(point2, active);
    }
    public void setConnectionHighlightColor(Point point1, Point point2, Color color) {
        displayNodes.get(point1).setConnectionHighlightColor(point2, color);
    }
    public Boolean isConnectionHighlighted(Point point1, Point point2) {
        return displayNodes.get(point1).isConnectionHighlighted(point2);
    }
}
