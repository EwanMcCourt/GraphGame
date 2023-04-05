package Main;

import MVC.Controller.Controller;
import MVC.Model.*;
import MVC.View.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        View view = new FXView(stage);
        Model model = new FileGraph("points.txt");
        new Controller(view, model);
    }
}
