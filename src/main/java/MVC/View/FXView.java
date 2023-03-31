package MVC.View;

import MVC.Model.Leaderboard;
import MVC.Model.Player;
import javafx.application.Platform;
import MVC.Model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
        EventHandler<ActionEvent> eventHandler = e -> {
            Set<Point> points = new HashSet<>();
            for (int i=0; i<Integer.parseInt(text.getText()); i++) {
                points.add(new Point(i));
            }
            graph.populateNodes(points);
        };
        button.setOnAction(eventHandler);

        Button testButton = new Button("Test");
        testButton.setOnAction(e -> test());


        TextField loginText = new TextField();
        loginText.setMaxWidth(100);
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> login(loginText.getText()));

        TextField registerText = new TextField();
        registerText.setMaxWidth(100);
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> register(registerText.getText()));


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



        root.getChildren().addAll(testButton,button, text, loginButton,loginText,registerButton, registerText,(Node) graph,leaderboardTextArea);

        stage.setScene(scene);
        stage.show();
    }

    public void populateGraph(Set<Point> points) {
        graph.populateNodes(points);
    }

    public Collection<DisplayNode> getNodes() {
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

    public void highlightNode(Point point, Boolean active) {
        graph.highlight(point, active);
    }
    public void setHighlightColor(Point point, Color color) {
        graph.setHighlightColor(point, color);
    }
    public Boolean isHighlighted(Point point1) {
        return graph.isHighlighted(point1);
    }
    public void highlightConnection(Point point1, Point point2, Boolean active) {
        graph.highlightConnection(point1, point2, active);
    }
    public void setConnectionHighlightColor(Point point1, Point point2, Color color) {
        graph.setConnectionHighlightColor(point1, point2, color);
    }
    public Boolean isConnectionHighlighted(Point point1, Point point2) {
        return graph.isConnectionHighlighted(point1, point2);
    }

    public void addConnection(Point point1, Point point2, int weight) {
        graph.addConnection(point1, point2, weight);
    }

    public void test() {
        for (DisplayNode p1 : graph.getDisplayNodes()) {
            for (DisplayNode p2 : graph.getDisplayNodes()) {
                graph.addConnection(p1.getPoint(), p2.getPoint(), 0);
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

    public void register(String givenUsername) {
        Player player;
        Alert alert;
        List<Player> players;
        givenUsername =givenUsername.replaceAll("\\s+","");
        System.out.println(givenUsername);
        player = Leaderboard.loadPlayer(givenUsername);
        players = Leaderboard.loadPlayers();

        if (players.contains(player) || givenUsername.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input not valid");
            alert.setContentText("This user already exists. Please enter a unique username.");
            alert.showAndWait();
        }else{
            Leaderboard.addPlayer(givenUsername);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Registration Successful");
            alert.setContentText("You have now registered the account: " + givenUsername);
            alert.show();

        }
    }
}