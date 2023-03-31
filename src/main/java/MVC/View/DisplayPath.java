package MVC.View;

import MVC.Model.Path;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

// Experimenting, Will Probably Remove
public class DisplayPath extends Pane {
    SimpleDoubleProperty shortAxis, longAxis, spacing, originX, originY, radius;
    private final static double HORIZONTAL_PADDING = 0.15;
    private final static double VERTICAL_PADDING = 0.5;
    public DisplayPath(Path path) {
        System.out.println("DisplayPath");
        path.print();
        shortAxis = new SimpleDoubleProperty();
        longAxis = new SimpleDoubleProperty();
        spacing = new SimpleDoubleProperty();
        originX = new SimpleDoubleProperty();
        originY = new SimpleDoubleProperty();
        radius = new SimpleDoubleProperty();
        this.minHeightProperty().set(100);
        originX.bind(this.widthProperty().multiply(HORIZONTAL_PADDING/2));
        originY.bind(this.heightProperty().multiply(VERTICAL_PADDING/2));
        longAxis.bind(this.widthProperty().multiply(1-HORIZONTAL_PADDING));
        shortAxis.bind(this.heightProperty().multiply(1-VERTICAL_PADDING));
        spacing.bind(longAxis.divide((path.size()*2)-1));

//        radius.bind(Bindings.min(spacing.divide(2),Bindings.max(shortAxis.divide(2),50)));
        radius.bind(Bindings.min(spacing.divide(2),shortAxis.divide(2)));

        for (int i=0; i<(path.size()*2)-1; i+=2){
            Circle node = new Circle();
            node.setFill(Color.PINK);
            node.centerXProperty().bind(spacing.divide(2).add(spacing.multiply(i)).add(originX));
            node.centerYProperty().bind(shortAxis.divide(2).add(originY));
            node.radiusProperty().bind(radius);
            this.getChildren().add(node);
        }

        for (int i=1; i<(path.size()*2)-1; i+=2) {
            Line line = new Line();
            line.setStroke(Color.RED);
            line.startYProperty().bind(shortAxis.divide(2).add(originY));
            line.endYProperty().bind(shortAxis.divide(2).add(originY));
            line.startXProperty().bind(spacing.divide(2).add(spacing.multiply(i-1)).add(originX).add(radius));
            line.endXProperty().bind(spacing.divide(2).add(spacing.multiply(i+1)).add(originX).subtract(radius));
            this.getChildren().add(line);
        }
    }
}
