package MVC.View;

import MVC.Model.Path;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class DisplayPath extends Pane {
    private final static double HORIZONTAL_PADDING = 0.15;
    private final static double VERTICAL_PADDING = 0.5;
    public DisplayPath(Path path, String label) {
        SimpleDoubleProperty shortAxis = new SimpleDoubleProperty();
        SimpleDoubleProperty longAxis = new SimpleDoubleProperty();
        SimpleDoubleProperty spacing = new SimpleDoubleProperty();
        SimpleDoubleProperty originX = new SimpleDoubleProperty();
        SimpleDoubleProperty originY = new SimpleDoubleProperty();
        SimpleDoubleProperty radius = new SimpleDoubleProperty();
        this.minHeightProperty().set(100);
        originX.bind(this.widthProperty().multiply(HORIZONTAL_PADDING/2));
        originY.bind(this.heightProperty().multiply(VERTICAL_PADDING/2));
        longAxis.bind(this.widthProperty().multiply(1-HORIZONTAL_PADDING));
        shortAxis.bind(this.heightProperty().multiply(1-VERTICAL_PADDING));
        spacing.bind(longAxis.divide((path.size()*2)-1));


        Text weight = new Text("Weight: "+path.getWeight().intValue());
        StackPane weightPane = new StackPane(weight);
//        weightPane.layoutXProperty().bind(this.widthProperty().subtract(this.widthProperty().multiply(HORIZONTAL_PADDING)).subtract(weightPane.widthProperty().divide(2)));
        weightPane.layoutXProperty().bind(this.widthProperty().subtract(originX).subtract(weightPane.widthProperty()));
        weightPane.layoutYProperty().set(0);

        Text pathLabel = new Text(label);
        StackPane labelPane = new StackPane(pathLabel);
        labelPane.layoutXProperty().bind(originX);
        labelPane.layoutYProperty().set(0);
        this.getChildren().addAll(weightPane, labelPane);

//        radius.bind(Bindings.min(spacing.divide(2),Bindings.max(shortAxis.divide(2),50)));
        radius.bind(Bindings.min(spacing.divide(2), shortAxis.divide(2)));

        for (int i=0; i<(path.size()*2)-1; i+=2){
            SimpleDoubleProperty centreX = new SimpleDoubleProperty();
            SimpleDoubleProperty centreY = new SimpleDoubleProperty();
            centreX.bind(spacing.divide(2).add(spacing.multiply(i)).add(originX));
            centreY.bind(shortAxis.divide(2).add(originY));
            StackPane pane = new StackPane();
            pane.setPickOnBounds(false);

            pane.layoutXProperty().bind(centreX.subtract(pane.widthProperty().divide(2)));
            pane.layoutYProperty().bind(centreY.subtract(pane.heightProperty().divide(2)));

            Circle node = new Circle();
            node.setFill(Color.PINK);
            node.radiusProperty().bind(radius);
            pane.getChildren().add(node);

            Text nodeLabel = new Text(String.valueOf(path.get(i/2).getIndex()));
            pane.getChildren().add(nodeLabel);

            this.getChildren().add(pane);
        }

        for (int i=1; i<(path.size()*2)-1; i+=2) {
            SimpleDoubleProperty offset = new SimpleDoubleProperty(15);
            Pane pane = new Pane();
            StackPane sPane = new StackPane();

            pane.layoutXProperty().bind(spacing.divide(2).add(spacing.multiply(i-1)).add(originX).add(radius));
            pane.layoutYProperty().bind(shortAxis.divide(2).add(originY).subtract(pane.heightProperty().divide(2)));

            sPane.layoutXProperty().bind(pane.layoutXProperty().add(spacing.subtract(radius)).subtract(sPane.widthProperty().divide(2)));
            sPane.layoutYProperty().bind(pane.layoutYProperty().subtract(offset));

            Line line = new Line();
            line.setStroke(Color.RED);
            line.endXProperty().bind(spacing.multiply(2).subtract(radius.multiply(2)));
            pane.getChildren().add(line);

            System.out.println(path.get((i+1)/2).getIndex());

            Text weightLabel = new Text(String.valueOf((int) path.get((i-1)/2).getWeight(path.get((i+1)/2))));
            sPane.getChildren().add(weightLabel);

            this.getChildren().addAll(pane, sPane);
        }
    }
}
