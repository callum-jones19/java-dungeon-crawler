package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class DeathController implements IHighlighter {

    @FXML 
    private VBox optionBox;

    @FXML 
    private Label playAgainBtn;

    @FXML 
    private Label quitGameBtn;

    private DeathScreen deathScreen;

    public DeathController(DeathScreen deathScreen) {
        this.deathScreen = deathScreen;
    }

    @FXML
    public void initialize() {
        // TODO 
    }

    @FXML 
    public void hoverPlay() {
        highlightElement(playAgainBtn);
    }

    @FXML 
    public void unhoverPlay() {
        unhighlightElement(playAgainBtn);
    }

    @FXML 
    public void hoverQuit() {
        highlightElement(quitGameBtn);
    }

    @FXML 
    public void unhoverQuit() {
        unhighlightElement(quitGameBtn);
    }

    @FXML 
    public void playAgain() {
        deathScreen.resetDungeon();
    }

    @FXML 
    public void clickQuit() {
        deathScreen.closeApplication();
    }
    
}