package unsw.dungeon;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

public class TexturePackController {

    @FXML
    MenuItem defaultOption;
    @FXML
    MenuItem christmasOption;
    @FXML
    Label backButton;

    private String currentPack;

    private TexturePackScreen screen;

    public TexturePackController (TexturePackScreen screen) {
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

    private void writeCurrentPack() {
        try {
            String file = DungeonApplication.CONFIG;
            FileWriter writer = new FileWriter(file);
            writer.write("currentPack=" + currentPack);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCurrentPack() {
        String file = DungeonApplication.CONFIG;
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String setting[] = line.split("=");
            if (setting[0].equals("currentPack")) {
                currentPack = setting[1];
            }
        }
        reader.close();
    }

    @FXML
    public void initialize() {
        readCurrentPack();
    }

    @FXML
    public void onClickDefault() {
        currentPack = "default";
        writeCurrentPack();
    }

    @FXML
    public void onClickChristmas() {
        currentPack = "christmas";
        writeCurrentPack();
    }

    @FXML
    public void clickBack() {
        screen.openMenuScreen();
    }

    @FXML
    public void hoverBack() {
        highlightElement(backButton);
    }

    @FXML
    public void unHoverBack() {
        unHighlightElement(backButton);
    }

}
