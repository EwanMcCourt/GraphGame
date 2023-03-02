package MVC.View;

import MVC.Model.Leaderboard;
import MVC.Model.Player;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FXView implements View{
    private final Stage stage;
    private GraphDisplay graph;
    private TextArea leaderboardTextArea;
    private ArrayList topPlayers;

    public FXView(Stage stage) {
        this.stage = stage;
    }
    public void initialise() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 700, 700, Color.INDIGO);

        SimpleDoubleProperty graphWidth = new SimpleDoubleProperty();
        SimpleDoubleProperty graphHeight = new SimpleDoubleProperty();
        graphWidth.bind(scene.widthProperty());
        graphHeight.bind(scene.heightProperty());

        graph = new DisplayGraph(graphWidth, graphHeight);
//        graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        TextField text = new TextField();
        text.setMaxWidth(100);
        Button button = new Button("Populate");
        button.setOnAction(e -> graph.populateNodes(Integer.parseInt(text.getText())));

        Button testButton = new Button("Test");
        testButton.setOnAction(e -> test());


        TextField loginText = new TextField();
        loginText.setMaxWidth(100);
        Button loginButton = new Button("login");
        loginButton.setOnAction(e -> login(loginText.getText()));


        leaderboardTextArea = new TextArea();
        leaderboardTextArea.setEditable(false);
        leaderboardTextArea.setMouseTransparent(true);
        leaderboardTextArea.setFocusTraversable(false);
        leaderboardTextArea.appendText("LEADERBOARD:");
        topPlayers = Leaderboard.getTopTenPlayers();
        for (int i =0; i<topPlayers.size(); i++){
            leaderboardTextArea.appendText("\n"+topPlayers.get(i));

        }
        leaderboardTextArea.setPrefSize(5,500);//change to dynamically size
        leaderboardTextArea.setMaxWidth(100);
        leaderboardTextArea.setMaxHeight(500);



        root.getChildren().addAll(testButton,button, text, loginButton,loginText,(Node) graph,leaderboardTextArea);

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

    public void test() {
        for (int i=0; i<graph.getNodeNum();i++) {
            for (int j = 1+i; j <graph.getNodeNum(); j++) {
                graph.addConnection(i, j, 0);
            }
        }

    }


    public void login(String givenUsername) {

        Player player;
        Alert alert;

        player = Leaderboard.loadPlayer(givenUsername);
        if (player == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input not valid");
            alert.setContentText("This user does not exist. If you want to create a new user, please register.");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login Successful");
            alert.setContentText("You are now logged in as " + givenUsername);
            alert.show();

        }
    }
}