package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen implements GameScreen {
    private Stage stage;
    private String title;
    private DungeonController controller;
    private Scene scene;

    private GameScreenManager gm;

    public DungeonScreen(Stage stage, GameScreenManager gm, DungeonControllerLoader l) throws IOException { 
        this.gm = gm;
        
        // Set a reference to the primary window to draw to.
        this.stage = stage;
        
        // Initialise the dungeon controller for the particular dungeon file
        // entered.
        controller = new DungeonController(l, this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);

        // Set the scene title.
        title = "Dungeon";

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
        // Need to write this twice to overcome JavaFX Bug
        stage.setMaximized(false);
        stage.setMaximized(true);

        // Need to enforce the gameloop to run, as it will not run *initialize*
        // if comign back from a pause.
        controller.startGameLoop();
    }

    public void openPauseScreen() {
        controller.stopGameLoop();
        gm.setActiveScreen(gm.getPausedState());
    }


}