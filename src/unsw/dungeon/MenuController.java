package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuController implements IHighlighter {
 
    @FXML
    private VBox menuBox;

    @FXML
    private Label resume;
    @FXML 
    private Label restart;
    @FXML
    private Label controls;
    @FXML
    private Label lobby;
    @FXML
    private Label quit;

    private MenuScreen screen;

    public MenuController(MenuScreen screen) {
        this.screen = screen;
    }

    @FXML
    public void initialize() {
        
    }

    // Use this to escape out of the menu with ESC
    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            screen.returnToDungeonScreen();
        }
    }

    @FXML
    public void clickResume() {
        screen.returnToDungeonScreen();
    }

    @FXML
    public void hoverResume() {
        highlightElement(resume);
    }

    @FXML
    public void unHoverResume() {
        unhighlightElement(resume);
    }

    @FXML
    public void clickRestart() {
        screen.restartLevel();
    }

    @FXML
    public void hoverRestart() {
        highlightElement(restart);
    }

    @FXML
    public void unHoverRestart() {
        unhighlightElement(restart);
    }

    @FXML
    public void clickChange() {
        // TODO
        screen.getControlsScreen();
    }

    @FXML
    public void hoverChange() {
        highlightElement(controls);
    }
    
    @FXML
    public void unHoverChange() {
        unhighlightElement(controls);
    }

    @FXML
    public void clickLobby() {
        screen.returnToLobbyScreen();
    }

    @FXML
    public void hoverLobby() {
        highlightElement(lobby);
    }

    @FXML
    public void unHoverLobby() {
        unhighlightElement(lobby);
    }

    @FXML
    public void clickQuit() {
        screen.closeApplication();
    }

    @FXML
    public void hoverQuit() {
        highlightElement(quit);
    }

    @FXML
    public void unHoverQuit() {
        unhighlightElement(quit);
    }


}