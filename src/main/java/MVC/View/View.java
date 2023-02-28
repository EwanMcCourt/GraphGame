package MVC.View;

import MVC.Controller.Controller;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public interface View {
    void initialise();
    void setIcon(String filename) throws IOException;
    void populateGraph(int nodeNum);
    List<DisplayNode> getNodes();
    void setTitle(String title);
    void addNode();
    void removeNode();
    void highlightNode(int index);
    void unHighlightNode(int index);
    void toggleHighlightNode(int index);
    void setHighlightColor(int index, Color color);
    void addConnection(int index1, int index2, int weight);

    void setController(Controller controller);
}
