package MVC.View;

import javafx.scene.paint.Color;

public enum ConnectionColour {
    SELECTED (Color.GREEN),
    AVAILABLE (Color.SKYBLUE),
    UNAVAILABLE (Color.RED),
    CURRENT (Color.ORANGE);

    final Color color;
    ConnectionColour(Color color) {
        this.color=color;
    }
}
