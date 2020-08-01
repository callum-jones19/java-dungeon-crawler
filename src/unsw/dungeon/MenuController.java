package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuController {
 
    @FXML
    private VBox menuBox;

    @FXML
    private Label resume;
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

 

    private void highlightElement(Label target) {
        target.setTextFill(Color.HOTPINK);
        target.setStyle("-fx-font-weight: bold");
    }

    private void unHighlightElement(Label target) {
        target.setTextFill(Color.BLACK);
        target.setStyle("-fx-font-weight: regular");
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
        unHighlightElement(resume);
    }

    @FXML
    public void clickChange() {
        // TODO
        System.out.println("Need to implement change screen.");
        // maybe store these screens in gameScreenManager and have the states
        //manage it
    }

    @FXML
    public void hoverChange() {
        highlightElement(controls);
    }
    
    @FXML
    public void unHoverChange() {
        unHighlightElement(controls);
    }

    @FXML
    public void clickLobby() {
        screen.returnToLobbyScreen();;
    }

    @FXML
    public void hoverLobby() {
        highlightElement(lobby);
    }

    @FXML
    public void unHoverLobby() {
        unHighlightElement(lobby);
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
        unHighlightElement(quit);
    }


}