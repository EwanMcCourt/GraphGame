package MVC.View;

import javafx.scene.paint.Color;

import java.util.List;

public interface GraphDisplay {
    void populateNodes(int parseInt);

    List<DisplayNode> getDisplayNodes();

    void highlight(int index);

    void unhighlight(int index);

    void toggleHighlight(int index);

    void setHighlightColor(int index, Color color);

    int getNodeNum();

    void addConnection(int index1, int index2, int weight);
}
