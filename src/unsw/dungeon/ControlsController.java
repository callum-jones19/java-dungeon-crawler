package unsw.dungeon;

import java.util.HashMap;
import java.util.Scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ControlsController implements IHighlighter {

    @FXML
    private GridPane movementGrid;
    @FXML
    private GridPane rotationGrid;
    @FXML
    private Button saveBtn;

    @FXML
    private Label moveUpLabel;
    @FXML
    private Label moveDownLabel;
    @FXML
    private Label moveRightLabel;
    @FXML
    private Label moveLeftLabel;
    @FXML 
    private Label attackLabel;

    private ControlsScreen screen;

    private BooleanProperty moveUp = new SimpleBooleanProperty();
    private BooleanProperty moveDown = new SimpleBooleanProperty();
    private BooleanProperty moveLeft = new SimpleBooleanProperty();
    private BooleanProperty moveRight = new SimpleBooleanProperty();
    private BooleanProperty attack = new SimpleBooleanProperty();

    private HashMap<String, String> keybindings;

    public ControlsController(ControlsScreen screen) {
        this.screen = screen;
        this.keybindings = getCurrentKeys();
    }

    @FXML
    public void initialize() {
        moveUp.bind(moveUpLabel.hoverProperty());
        moveUpLabel.setText("Move Up: " + keybindings.get("UP"));
        moveDown.bind(moveDownLabel.hoverProperty());
        moveDownLabel.setText("Move Down: " + keybindings.get("DOWN"));
        moveLeft.bind(moveLeftLabel.hoverProperty());
        moveLeftLabel.setText("Move Left: " + keybindings.get("LEFT"));
        moveRight.bind(moveRightLabel.hoverProperty());
        moveRightLabel.setText("Move Right: " + keybindings.get("RIGHT"));

        attack.bind(attackLabel.hoverProperty());
        attackLabel.setText("Attack: " + keybindings.getOrDefault("ATTACK", "SPACE"));

        Image playerImage = new Image((new File("images/human.png")).toURI().toString());
        ImageView playerView = new ImageView(playerImage);
        movementGrid.add(playerView, 1, 1);

    }

    @FXML 
    public void returnToDungeon() {
        try {
            FileWriter outputter = new FileWriter("keybindings.txt");
            outputter.write(keybindings.toString());
            outputter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen.refreshKeys();
        screen.returnToDungeonScreen();
    }

    @FXML
    public void handleKeyPressMovement(KeyEvent event) {

        if (moveUp.get()) {
            keybindings.put("UP", event.getCode().toString());
            moveUpLabel.setText("Move Up: " + event.getCode().toString());
        } else if (moveDown.get()) {
            keybindings.put("DOWN", event.getCode().toString());
            moveDownLabel.setText("Move Down: " + event.getCode().toString());
        } else if (moveLeft.get()) {
            keybindings.put("LEFT", event.getCode().toString());
            moveLeftLabel.setText("Move Left: " + event.getCode().toString());
        } else if (moveRight.get()) {
            keybindings.put("RIGHT", event.getCode().toString());
            moveRightLabel.setText("Move Right: " + event.getCode().toString());
        } else if (attack.get()) {
            keybindings.put("ATTACK", event.getCode().toString());
            attackLabel.setText("Attack: " + event.getCode().toString());
        }

        System.out.println(keybindings);

    }

    @FXML 
    public void hoverUp() {
        highlightElement(moveUpLabel);
    }

    @FXML 
    public void hoverDown() {
        highlightElement(moveDownLabel);
    }

    @FXML 
    public void hoverLeft() {
        highlightElement(moveLeftLabel);
    }

    @FXML 
    public void hoverRight() {
        highlightElement(moveRightLabel);
    }

    @FXML 
    public void hoverAttack() {
        highlightElement(attackLabel);
    }

    @FXML 
    public void unHoverUp() {
        unhighlightElement(moveUpLabel);
    }

    @FXML 
    public void unHoverDown() {
        unhighlightElement(moveDownLabel);
    }

    @FXML 
    public void unHoverLeft() {
        unhighlightElement(moveLeftLabel);
    }

    @FXML 
    public void unHoverRight() {
        unhighlightElement(moveRightLabel);
    }

    @FXML 
    public void unHoverAttack() {
        unhighlightElement(attackLabel);
    }

    public HashMap<String, String> getCurrentKeys() {
        String keyMap = "";
        HashMap<String, String> currKeys = new HashMap<String, String>();
        try {
            File keys = new File("keybindings.txt");
            Scanner fileReader = new Scanner(keys);
            while (fileReader.hasNextLine()) {
                keyMap += fileReader.nextLine();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        keyMap = keyMap.substring(1, keyMap.length() - 1);
        String[] pairs = keyMap.split(",");
        for (String pair: pairs) {
            pair = pair.strip();
            currKeys.put(pair.split("=")[0], pair.split("=")[1]);
        }
        return currKeys;
    }

    
}