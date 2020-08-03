package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.JFileChooser;

import java.awt.Component;
import java.io.File;

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
        screen.returnToLobbyScreen();
    }

    @FXML
    public void loadLevel() {
        JFileChooser fileBrowser = new JFileChooser();
        int response = fileBrowser.showOpenDialog(null);

        String filePath = "";
        if (response == JFileChooser.APPROVE_OPTION) {
            filePath = fileBrowser.getSelectedFile().getAbsolutePath();
        }

        screen.loadNewLevel(filePath);

    }

    @FXML
    public void quitGame() {
        screen.closeApplication();
    }
    
}