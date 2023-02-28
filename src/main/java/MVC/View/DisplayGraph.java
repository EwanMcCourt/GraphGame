package MVC.View;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;
import java.util.List;

public class DisplayGraph extends Pane implements GraphDisplay {
    private List<DisplayNode> displayNodes = new ArrayList<>();
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty height;
    private SimpleDoubleProperty centreX;
    private SimpleDoubleProperty centreY;
    SimpleIntegerProperty numNodes = new SimpleIntegerProperty();

    SimpleDoubleProperty NODE_RADIUS_SCALE_FACTOR = new SimpleDoubleProperty(0.95);
    SimpleDoubleProperty RING_RADIUS_SCALE_FACTOR = new SimpleDoubleProperty(0.95);
    DoubleBinding RING_RADIUS = new DoubleBinding() {
        {
            bind(numNodes);
        }
        @Override
        protected double computeValue() {
            if(displayNodes.size() == 0) {
                return 1.0;
            }
            return 1.0 / (2.0 + 2.0 * Math.sin(Math.PI / numNodes.getValue()));
        }
    };
    DoubleBinding NODE_RADIUS_BOUNDARY = new DoubleBinding() {
        {
            bind(numNodes);
        }
        @Override
        protected double computeValue() {
            if(displayNodes.size() == 0) {
                return 1.0;
            }
            return 1.0 / (2.0 * (1.0 + (1.0 / Math.sin(Math.PI / numNodes.getValue()))));
        }
    };
    double CENTRE_POINT_RADIUS_SCALE_FACTOR = 0.2;

    public DisplayGraph(SimpleDoubleProperty width, SimpleDoubleProperty height) {
        this.width = width;
        this.height = height;
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(width);

        centreX = new SimpleDoubleProperty();
        centreY = new SimpleDoubleProperty();
        centreX.bind(this.widthProperty().multiply(0.5));
        centreY.bind(this.heightProperty().multiply(0.5));
        System.out.println("CentreX: "+centreX.getValue());
        System.out.println("CentreY: "+centreY.getValue());
    }

    public void populateNodes(int nodeNum) {
        this.getChildren().clear();
        this.displayNodes.clear();
        Color fillColour = Color.RED;

        // https://math.stackexchange.com/questions/2438719/is-there-a-formula-for-the-big-radius-of-an-evenly-spaced-ring-of-circles
//        double NODE_RADIUS_SCALE_FACTOR = 0.95;
//        double RING_RADIUS_SCALE_FACTOR = 0.95;
//        double RING_RADIUS = 1/(2+2*Math.sin(Math.PI/nodeNum));
//        double NODE_RADIUS_BOUNDARY = 1/(2*(1+(1/Math.sin(Math.PI/nodeNum))));
//        double CENTRE_POINT_RADIUS_SCALE_FACTOR = 0.2;

        SimpleDoubleProperty radius = new SimpleDoubleProperty();
        radius.bind(Bindings.min(this.widthProperty(), this.heightProperty()).multiply(RING_RADIUS).multiply(RING_RADIUS_SCALE_FACTOR));
        System.out.println("Radius: "+radius.getValue());

        SimpleDoubleProperty nodeRadius = new SimpleDoubleProperty();
        nodeRadius.bind(Bindings.min(this.widthProperty(), this.heightProperty()).multiply(NODE_RADIUS_BOUNDARY).multiply(NODE_RADIUS_SCALE_FACTOR).multiply(RING_RADIUS_SCALE_FACTOR));

        System.out.println("Node Radius: "+nodeRadius.getValue());

        Circle centrePoint = new Circle();
        centrePoint.centerXProperty().bind(centreX);
        centrePoint.centerYProperty().bind(centreY);
        centrePoint.radiusProperty().bind(nodeRadius.multiply(CENTRE_POINT_RADIUS_SCALE_FACTOR));
        centrePoint.setFill(Color.ORANGE);
//        this.getChildren().add(centrePoint);

        if(nodeNum == 1) {
            System.out.println("One Node");
            Circle centreCircle = new Circle();
            centreCircle.centerXProperty().bind(centreX);
            centreCircle.centerYProperty().bind(centreY);
            centreCircle.radiusProperty().bind(nodeRadius.multiply(0.5));
            centreCircle.setFill(fillColour);
//            this.getChildren().add(centreCircle);
        } else {
            for(int i=0; i<nodeNum; i++) {
                // https://math.stackexchange.com/questions/4459356/find-n-evenly-spaced-points-on-circle-with-radius-r
//                double theta = (i*((2*Math.PI)/nodeNum))-(Math.PI/2);
                double theta = (i*((2*Math.PI)/nodeNum));
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

//                Circle nodeAnchorCircle = new Circle();
//                nodeAnchorCircle.radiusProperty().bind(radius.multiply(0.1));
//                nodeAnchorCircle.centerXProperty().bind(nodeAnchorX);
//                nodeAnchorCircle.centerYProperty().bind(nodeAnchorY);
//                nodeAnchorCircle.setFill(Color.GREEN);
//                root.getChildren().add(nodeAnchorCircle);

                DisplayNode node = new DisplayNode(i, String.valueOf(i), nodeCentreX, nodeCentreY, nodeRadius, nodeAnchorX, nodeAnchorY);

//                // https://www.tutorialspoint.com/javafx/javafx_event_handling.htm
//                //Creating the mouse event handler
//                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        controller
//                    }
//                };
//                //Adding event Filter
//                Circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

                displayNodes.add(node);
                this.getChildren().add(node);
            }
        }
        update();
    }

    public List<DisplayNode> getDisplayNodes() {
        return displayNodes;
    }

    private void update() {
        numNodes.set(displayNodes.size());
    }

    public int getNodeNum() {
        return displayNodes.size();
    }

    public void addConnection(int index1, int index2, int weight) {
        QuadCurve curve = createCurve(index1, index2);

        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(getWeightColor(weight));

        QuadCurve highlightCurve = createCurve(index1, index2);
        highlightCurve.setFill(Color.TRANSPARENT);
        highlightCurve.setStroke(Color.SKYBLUE);
        highlightCurve.setStrokeWidth(5.0);
        highlightCurve.setVisible(false);

        displayNodes.get(index1).addConnection(index2, displayNodes.get(index2), curve, highlightCurve);
        displayNodes.get(index2).addConnection(index1, displayNodes.get(index1), curve, highlightCurve);

        this.getChildren().addAll(highlightCurve, curve);
    }

    private QuadCurve createCurve(int index1, int index2) {
        QuadCurve curve = new QuadCurve();

        curve.controlXProperty().bind(centreX);
        curve.controlYProperty().bind(centreY);

        curve.startXProperty().bind(displayNodes.get(index1).getAnchorX());
        curve.startYProperty().bind(displayNodes.get(index1).getAnchorY());

        curve.endXProperty().bind(displayNodes.get(index2).getAnchorX());
        curve.endYProperty().bind(displayNodes.get(index2).getAnchorY());

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

    public void highlight(int index) {
        displayNodes.get(index).setHighlight(true);
    }
    public void unhighlight(int index) {
        displayNodes.get(index).setHighlight(false);
    }
    public void toggleHighlight(int index) {
        displayNodes.get(index).toggleHighlight();
    }
    public void setHighlightColor(int index, Color color) {
        displayNodes.get(index).setHighlightColor(color);
    }
}
