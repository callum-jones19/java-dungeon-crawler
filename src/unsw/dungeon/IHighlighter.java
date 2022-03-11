package unsw.dungeon;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public interface IHighlighter {

    default void highlightElement(Label target) {
        target.setTextFill(Color.HOTPINK);
        target.setStyle("-fx-font-weight: bold");
    }

    default void unhighlightElement(Label target) {
        target.setTextFill(Color.BLACK);
        target.setStyle("-fx-font-weight: regular");
    }
    
}