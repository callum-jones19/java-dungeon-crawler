package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.JFileChooser;


import java.io.File;

public class CompletionController implements IHighlighter {

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
        unhighlightElement(lobbyBtn);
    }

    @FXML
    public void unHoverLevel() {
        unhighlightElement(loadLevelBtn);
    }

    @FXML 
    public void unHoverQuit() {
        unhighlightElement(quitGameBtn);
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
        } else {
            return;
        }

        screen.loadNewLevel(filePath);

    }

    @FXML
    public void quitGame() {
        screen.closeApplication();
    }
    
}