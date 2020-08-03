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
    @FXML
    private Label texturePack;
    @FXML
    private Label howTo;

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

    @FXML
    public void clickTexture() {
        screen.openTextureScreen();
    }
    
    @FXML
    public void hoverTexture() {
        highlightElement(texturePack);
    }

    @FXML
    public void unHoverTexture() {
        unHighlightElement(texturePack);
    }
    
    @FXML
    public void clickHow() {
        screen.openHowToScreen();
    }

    @FXML
    public void hoverHow() {
        highlightElement(howTo);
    }

    @FXML
    public void unHoverHow() {
        unHighlightElement(howTo);
    }

}