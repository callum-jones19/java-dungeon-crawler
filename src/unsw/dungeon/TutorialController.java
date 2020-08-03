package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TutorialController implements IHighlighter {

    @FXML
    private Label back;

    private TutorialScreen screen;

    public TutorialController (TutorialScreen screen) {
        this.screen = screen;
    }

    @FXML
    public void initialize() {
        
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
        unhighlightElement(back);
    }

}
