package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TexturePackScreen implements GameScreen {
    private Stage stage;
    private String title;
    private TexturePackController controller;
    private Scene scene;

    private GameScreenManager gm;

    public TexturePackScreen (Stage stage, GameScreenManager gm) throws IOException {
        // Set a reference to the primary window to draw to.
        this.stage = stage;
        
        this.gm = gm;

        // Set the controller for the menu
        controller = new TexturePackController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TexturePackView.fxml"));
        loader.setController(controller);

        // Set the scene title
        this.title = "[TEXTURES] Dungeon";

        // Create a scene with the controller class as a root.
        Parent root = loader.load();
        scene = new Scene(root, DungeonApplication.APP_HEIGHT, DungeonApplication.APP_WIDTH);
    }

    @Override
    public void loadScreen() {
        stage.setScene(scene);
        stage.setTitle(title);
        scene.getRoot().requestFocus();
        stage.show();
        // Need to write this twice to overcome JavaFX Bug, love that
        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    public void openMenuScreen() {
        gm.setScreenState(gm.getPauseState());
    }
}