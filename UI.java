import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class UI extends Application implements View {
    List<List<Integer>> data = new ArrayList<>();
    HashMap<Integer, Button> nodes = new HashMap<>();
    public void run(){
        launch();
    }
    public void set_nodes(){
        data.add(Arrays.asList(0, 1, 7));
        data.add(Arrays.asList(0, 11, 5));
        data.add(Arrays.asList(0, 25, 4));
    }


    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();
        set_nodes();
        System.out.println(data.size());
        // Create a button for each node in the node lists
        for (int i = 0; i < data.size(); i++) {
            int node1 = data.get(i).get(0);
            int node2 = data.get(i).get(1);
            int weight = data.get(i).get(2);


            if (!nodes.containsKey(node1)) {
                Button button1 = new Button("Node" + node1);
                button1.setOnAction(event -> System.out.println("Weight for Nodes " + node1 + " and " + node2 + ": " + weight ));
                nodes.put(node1, button1); // add the button to the nodes map
                root.add(button1, nodes.size() - 1, 0); // add the button to the root pane
            }
            if (!nodes.containsKey(node2)) {
                Button button2 = new Button("Node" + node2);
                button2.setOnAction(event -> System.out.println("Weight for Nodes " + node1 + " and " + node2 + ": " + weight ));
                nodes.put(node2, button2); // add the button to the nodes map
                root.add(button2, nodes.size() - 1, 0); // add the button to the root pane
            }
        }

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Working");
        stage.show();
    }
}
