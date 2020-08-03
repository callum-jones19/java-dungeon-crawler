package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TutorialController {

    @FXML
    private Label back;

    private TutorialScreen screen;

    public TutorialController (TutorialScreen screen) {
        this.screen = screen;
    }

    @FXML
    public void initialize() {
        
    }

    private void highlightElement(Label target) {
        target.setTextFill(Color.HOTPINK);
        target.setStyle("-fx-font-weight: bold");
    }

    private void unHighlightElement(Label target) {
        target.setTextFill(Color.BLACK);
        target.setStyle("-fx-font-weight: regular");
    }

    @FXML
    public void backClick() {
        screen.openMenu();
    }
    
    @FXML
    public void backHover() {
        highlightElement(back);
    }

    @FXML
    public void backUnHover() {
        unHighlightElement(back);
    }

}
