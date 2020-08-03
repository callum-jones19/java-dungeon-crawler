package unsw.dungeon;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CompletionController {

    @FXML 
    private Label lobbyBtn;
    @FXML 
    private Label loadLevelBtn;
    @FXML 
    private Label quitGameBtn;

    private CompletionScreen screen;

    public CompletionController(CompletionScreen screen) {
        this.screen = screen;
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
    public void hoverLobby() {
        highlightElement(lobbyBtn);
    }

    @FXML
    public void hoverLevel() {
        highlightElement(loadLevelBtn);
    }

    @FXML 
    public void hoverQuit() {
        highlightElement(quitGameBtn);
    }

    @FXML 
    public void unHoverLobby() {
        unHighlightElement(lobbyBtn);
    }

    @FXML
    public void unHoverLevel() {
        unHighlightElement(loadLevelBtn);
    }

    @FXML 
    public void unHoverQuit() {
        unHighlightElement(quitGameBtn);
    }

    @FXML 
    public void returnToLobby() {
        // TODO
    }

    @FXML
    public void loadLevel() {
        // TODO
    }

    @FXML
    public void quitGame() {
        screen.closeApplication();
    }
    
}