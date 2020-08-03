package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class DeathController {

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
        unHighlightElement(playAgainBtn);
    }

    @FXML 
    public void hoverQuit() {
        highlightElement(quitGameBtn);
    }

    @FXML 
    public void unhoverQuit() {
        unHighlightElement(quitGameBtn);
    }

    @FXML 
    public void playAgain() {
        deathScreen.resetDungeon();
    }

    @FXML 
    public void clickQuit() {
        deathScreen.closeApplication();
    }

    private void highlightElement(Label target) {
        target.setTextFill(Color.HOTPINK);
        target.setStyle("-fx-font-weight: bold");
    }

    private void unHighlightElement(Label target) {
        target.setTextFill(Color.BLACK);
        target.setStyle("-fx-font-weight: regular");
    }
    
}