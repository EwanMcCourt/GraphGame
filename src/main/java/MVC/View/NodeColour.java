package MVC.View;

import javafx.scene.paint.Color;

public enum NodeColour {
    SELECTED (Color.GREEN),
    AVAILABLE (Color.SKYBLUE),
    UNAVAILABLE (Color.RED),
    START (Color.PURPLE),
    CURRENT (Color.ORANGE),
    GOAL (Color.BLUE);

    final Color color;
    NodeColour(Color color) {
        this.color=color;
    }
}
