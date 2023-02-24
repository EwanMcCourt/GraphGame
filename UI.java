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
    List<Integer>  nodes = new ArrayList<>();
    List<Integer> to_send = new ArrayList<>();

    public void run(){
        launch();
    }
    public void set_nodes(){
        data.add(Arrays.asList(0, 1, 7));
        data.add(Arrays.asList(0, 11, 5));
        data.add(Arrays.asList(0, 25, 4));
    }
    public void onClick(int node){
        if (!(to_send.contains(node)) && (to_send.size() < 2)){
            to_send.add(node);
        }

        else if (to_send.contains(node)){
            to_send.remove(to_send.indexOf(node));
        }
        else if (to_send.size() >= 2){
            System.out.println("Only 2 nodes, remove one");
        }
        System.out.println(to_send);
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


            if (!nodes.contains(node1)) {
                Button button1 = new Button("Node" + node1);
                button1.setOnAction(event ->
                        onClick(node1));
                nodes.add(node1);
                root.add(button1, nodes.size() - 1, 0); // add the button to the root pane
            }
            if (!nodes.contains(node2)) {
                Button button2 = new Button("Node" + node2);
                button2.setOnAction(event -> onClick(node2)
                );
                nodes.add(node2);
                root.add(button2, nodes.size() - 1, 0); // add the button to the root pane
            }
        }

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Working");
        stage.show();
    }
}
