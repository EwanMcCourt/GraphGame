import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class UI extends Application implements View {
    List<List<Integer>> data = new ArrayList<>();
    HashMap<Integer, Button> nodes = new HashMap<>();
    List<Integer> to_send = new ArrayList<>();

    public void run(){
        launch();
    }
    public void set_nodes(){
        data.add(Arrays.asList(0, 1));
        data.add(Arrays.asList(0, 11));
        data.add(Arrays.asList(0, 25));
        data.add(Arrays.asList(0, 28));
        data.add(Arrays.asList(0, 35));
        data.add(Arrays.asList(0, 36));
        data.add(Arrays.asList(0, 47));
        data.add(Arrays.asList(1, 5));
        data.add(Arrays.asList(1, 8));
        data.add(Arrays.asList(1, 11));
        data.add(Arrays.asList(1, 18));
        data.add(Arrays.asList(2, 15));
        data.add(Arrays.asList(2, 19));
        data.add(Arrays.asList(2, 47));
        data.add(Arrays.asList(3, 5));
        data.add(Arrays.asList(3, 38));
        data.add(Arrays.asList(3, 48));
        data.add(Arrays.asList(4, 6));
        data.add(Arrays.asList(4, 10));
        data.add(Arrays.asList(4, 13));
        data.add(Arrays.asList(4, 15));
        data.add(Arrays.asList(4, 28));
        data.add(Arrays.asList(4, 39));
        data.add(Arrays.asList(5, 17));
        data.add(Arrays.asList(5, 32));
        data.add(Arrays.asList(5, 40));
        data.add(Arrays.asList(5, 44));
        data.add(Arrays.asList(5, 46));
        data.add(Arrays.asList(5, 47));
        data.add(Arrays.asList(6, 11));
        data.add(Arrays.asList(6, 16));
        data.add(Arrays.asList(6, 20));
        data.add(Arrays.asList(6, 36));
        data.add(Arrays.asList(6, 48));
        data.add(Arrays.asList(7, 9));
        data.add(Arrays.asList(7, 18));
        data.add(Arrays.asList(7, 27));
        data.add(Arrays.asList(7, 30));
        data.add(Arrays.asList(7, 36));
        data.add(Arrays.asList(8, 36));
        data.add(Arrays.asList(9, 26));
        data.add(Arrays.asList(9, 40));
        data.add(Arrays.asList(10, 11));
        data.add(Arrays.asList(10, 17));
        data.add(Arrays.asList(10, 19));
        data.add(Arrays.asList(10, 26));
        data.add(Arrays.asList(10, 35));
        data.add(Arrays.asList(10, 41));
        data.add(Arrays.asList(10, 45));
        data.add(Arrays.asList(10, 47));
        data.add(Arrays.asList(11, 13));
        data.add(Arrays.asList(11, 14));
        data.add(Arrays.asList(11, 17));
        data.add(Arrays.asList(11, 20));
        data.add(Arrays.asList(11, 28));
        data.add(Arrays.asList(11, 30));
        data.add(Arrays.asList(11, 49));
        data.add(Arrays.asList(12, 20));
        data.add(Arrays.asList(12, 35));
        data.add(Arrays.asList(12, 48));
        data.add(Arrays.asList(13, 20));
        data.add(Arrays.asList(13, 22));
        data.add(Arrays.asList(13, 29));
        data.add(Arrays.asList(13, 30));
        data.add(Arrays.asList(13, 33));
        data.add(Arrays.asList(13, 42));
        data.add(Arrays.asList(13, 48));
        data.add(Arrays.asList(14, 23));
        data.add(Arrays.asList(14, 43));
        data.add(Arrays.asList(14, 48));
        data.add(Arrays.asList(15, 46));
        data.add(Arrays.asList(16, 24));
        data.add(Arrays.asList(17, 19));
        data.add(Arrays.asList(17, 22));
        data.add(Arrays.asList(18, 21));
        data.add(Arrays.asList(18, 32));
        data.add(Arrays.asList(18, 42));
        data.add(Arrays.asList(19, 26));
        data.add(Arrays.asList(19, 43));
        data.add(Arrays.asList(20, 40));
        data.add(Arrays.asList(21, 23));
        data.add(Arrays.asList(21, 30));
        data.add(Arrays.asList(21, 31));
        data.add(Arrays.asList(21, 41));
        data.add(Arrays.asList(21, 45));
        data.add(Arrays.asList(21, 48));
        data.add(Arrays.asList(22, 23));
        data.add(Arrays.asList(22, 28));
        data.add(Arrays.asList(22, 34));
        data.add(Arrays.asList(24, 25));
        data.add(Arrays.asList(24, 28));
        data.add(Arrays.asList(24, 33));
        data.add(Arrays.asList(25, 33));
        data.add(Arrays.asList(25, 39));
        data.add(Arrays.asList(26, 37));
        data.add(Arrays.asList(26, 40));
        data.add(Arrays.asList(26, 44));
        data.add(Arrays.asList(27, 29));
        data.add(Arrays.asList(27, 35));
        data.add(Arrays.asList(28, 29));
        data.add(Arrays.asList(28, 31));
        data.add(Arrays.asList(28, 35));
        data.add(Arrays.asList(28, 37));
        data.add(Arrays.asList(28, 49));
        data.add(Arrays.asList(29, 36));
        data.add(Arrays.asList(29, 42));
        data.add(Arrays.asList(29, 43));
        data.add(Arrays.asList(30, 31));
        data.add(Arrays.asList(30, 34));
        data.add(Arrays.asList(30, 42));
        data.add(Arrays.asList(30, 44));
        data.add(Arrays.asList(30, 48));
        data.add(Arrays.asList(31, 34));
        data.add(Arrays.asList(32, 42));
        data.add(Arrays.asList(33, 34));
        data.add(Arrays.asList(34, 44));
        data.add(Arrays.asList(36, 44));
        data.add(Arrays.asList(37, 41));
        data.add(Arrays.asList(37, 43));
        data.add(Arrays.asList(37, 48));
        data.add(Arrays.asList(38, 44));
        data.add(Arrays.asList(40, 43));
        data.add(Arrays.asList(43, 46));
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
    public void create_line(int node1, int node2, Pane root){
        Button button1 = nodes.get(node1);
        Button button2 = nodes.get(node2);
        Line line = new Line();
        line.startXProperty().bind(button1.layoutXProperty().add(button1.widthProperty().divide(2)));
        line.startYProperty().bind(button1.layoutYProperty().add(button1.heightProperty().divide(2)));
        line.endXProperty().bind(button2.layoutXProperty().add(button2.widthProperty().divide(2)));
        line.endYProperty().bind(button2.layoutYProperty().add(button2.heightProperty().divide(2)));
        root.getChildren().addAll(line);
        line.toBack();
    }
    public void set_location(int node, double w, double h,Pane root){
        Button button = nodes.get(node);
        button.layoutXProperty().bind(root.widthProperty().multiply(w));
        button.layoutYProperty().bind(root.heightProperty().multiply(h));
    }


    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        set_nodes();
        System.out.println(data.size());
        // Create a button for each node in the node lists
        for (int i = 0; i < data.size(); i++) {
            int node1 = data.get(i).get(0);
            int node2 = data.get(i).get(1);


            if (!nodes.containsKey(node1)) {
                Button button1 = new Button(Integer.toString(node1));
                button1.setOnAction(event ->
                        onClick(node1));
                nodes.put(node1, button1);
                root.getChildren().addAll(button1); // add the button to the root pane
            }
            if (!nodes.containsKey(node2)) {
                Button button2 = new Button(Integer.toString(node2));
                button2.setOnAction(event -> onClick(node2)
                );
                nodes.put(node2, button2);
                root.getChildren().addAll(button2); // add the button to the root pane
            }
        }
        set_location(0,0.5,0.5,root);
        set_location(1,0.5,0.45,root);
        set_location(11,0.45,0.5,root);
        set_location(25,0.52,0.55,root);
        set_location(28,0.55,0.5,root);
        set_location(35,0.45,0.45, root);
        set_location(36,0.55,0.45,root);
        set_location(47,0.47,0.55,root);



        //B0.prefHeightProperty().bind(root.heightProperty().multiply(0.02));
        //B0.prefWidthProperty().bind(root.widthProperty().multiply(0.05));
        //Button B1 = nodes.get(1);
        //B1.layoutXProperty().bind(root.widthProperty().multiply(0.5));
        //B1.layoutYProperty().bind(root.heightProperty().multiply(0.45));
        create_line(0,1,root);
        create_line(0,11,root);
        create_line(0,25,root);
        create_line(0,28,root);
        create_line(0,35,root);
        create_line(0,36,root);
        create_line(0,47,root);


        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.setTitle("Working");
        stage.show();
    }
}
