package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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
        // TODO
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        screen.returnToDungeon();
    }
}