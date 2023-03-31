module Main {
    requires javafx.controls;

    opens Main to javafx.fxml;
    exports Main;
}