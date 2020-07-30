package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;

/**
 * Class to control which 
 */
public class GameScreenManager {
    
    // Reference to JavaFX primary stage.
    Stage primaryStage;

    // States
    private GameScreen currentScreen;
    
    private GameScreen pausedState;
    private GameScreen lobbyState;
    private GameScreen dungeonScreen;



    public GameScreenManager(Stage stage) {
        this.primaryStage = stage;
        currentScreen = null;
    }

    /**
     * Lets the user load directly into a dungeon state - this should be the starting
     * screen.
     */
    public void loadNewDungeon(String filename) {
        try {
            DungeonControllerLoader loader = new DungeonControllerLoader(filename);
            setActiveScreen(new DungeonScreen(primaryStage, this, loader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveScreen (GameScreen gs) {
        currentScreen = gs;
        currentScreen.loadScreen();
    }

}