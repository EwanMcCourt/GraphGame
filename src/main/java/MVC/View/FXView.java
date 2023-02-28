package MVC.View;

import MVC.Controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FXView implements View{
    private Stage stage;
    private Controller controller;
    private List<DisplayNode> displayGraph = new ArrayList<>();
    private DisplayGraph graph;

    public FXView(Stage stage) {
        this.stage = stage;
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void initialise() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 700, 700, Color.INDIGO);

        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                controller.clickNode(1);
            }
        };

        graph = new DisplayGraph(graphWidth, graphHeight);

        TextField text = new TextField();
        text.setMaxWidth(100);
        Button buttonback = new Button("Backwards");
        buttonback.setOnAction(e -> stage.setScene(scene));
        Button button = new Button("Populate");
        button.setOnAction(e -> graph.populateNodes(Integer.parseInt(text.getText())));

        Button testButton = new Button("Test");
        testButton.setOnAction(e -> test());

        root.getChildren().addAll(testButton, button, text, graph);

        stage.setScene(scene);
        stage.show();
    }

    public void populateGraph(int nodeNum) {
        graph.populateNodes(nodeNum);
    }

    public List<DisplayNode> getNodes() {
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

    public void highlightNode(int index) {
        graph.highlight(index);
    }

    public void unHighlightNode(int index) {
        graph.unhighlight(index);
    }

    public void toggleHighlightNode(int index) {
        graph.toggleHighlight(index);
    }

    public void setHighlightColor(int index, Color color) {
        graph.setHighlightColor(index, color);
    }

    public void addConnection(int index1, int index2, int weight) {
        graph.addConnection(index1, index2, weight);
    }


    public void addNodes(int nodeNum) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 1000, Color.SKYBLUE);

        Color fillColour = Color.RED;

        // https://math.stackexchange.com/questions/2438719/is-there-a-formula-for-the-big-radius-of-an-evenly-spaced-ring-of-circles
        double NODE_RADIUS_SCALE_FACTOR = 1.0;
        double RING_RADIUS_SCALE_FACTOR = 1.0;
        double RING_RADIUS = 1/(2+2*Math.sin(Math.PI/nodeNum));
        double NODE_RADIUS_BOUNDARY = 1/(2*(1+(1/Math.sin(Math.PI/nodeNum))));
        double CENTRE_POINT_RADIUS_SCALE_FACTOR = 0.2;

        SimpleDoubleProperty centreX = new SimpleDoubleProperty();
        SimpleDoubleProperty centreY = new SimpleDoubleProperty();
        centreX.bind(scene.widthProperty().multiply(0.5));
        centreY.bind(scene.heightProperty().multiply(0.5));
        System.out.println("CentreX: "+centreX.getValue());
        System.out.println("CentreY: "+centreY.getValue());

        SimpleDoubleProperty radius = new SimpleDoubleProperty();
        radius.bind(Bindings.min(scene.widthProperty(), scene.heightProperty()).multiply(RING_RADIUS).multiply(RING_RADIUS_SCALE_FACTOR));
        System.out.println("Radius: "+radius.getValue());

        SimpleDoubleProperty nodeRadius = new SimpleDoubleProperty();
        nodeRadius.bind(Bindings.min(scene.widthProperty(), scene.heightProperty()).multiply(NODE_RADIUS_BOUNDARY).multiply(NODE_RADIUS_SCALE_FACTOR).multiply(RING_RADIUS_SCALE_FACTOR));

        System.out.println("Node Radius: "+nodeRadius.getValue());

        Circle centrePoint = new Circle();
        centrePoint.centerXProperty().bind(centreX);
        centrePoint.centerYProperty().bind(centreY);
        centrePoint.radiusProperty().bind(nodeRadius.multiply(CENTRE_POINT_RADIUS_SCALE_FACTOR));
        centrePoint.setFill(Color.ORANGE);
        root.getChildren().add(centrePoint);

        if(nodeNum == 1) {
            System.out.println("One Node");
            Circle centreCircle = new Circle();
            centreCircle.centerXProperty().bind(centreX);
            centreCircle.centerYProperty().bind(centreY);
            centreCircle.radiusProperty().bind(nodeRadius.multiply(0.5));
            centreCircle.setFill(fillColour);
            root.getChildren().add(centreCircle);
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

                root.getChildren().add(node);
            }
        }

        stage.setScene(scene);
        stage.show();
    }

    public void test() {
        for (int i=0; i<graph.getNodeNum();i++) {
            for (int j = 1+i; j <graph.getNodeNum(); j++) {
                graph.addConnection(i, j, 0);
            }
        }
    }
}