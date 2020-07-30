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
    private GameScreen currentScreenState;
    
    private GameScreen pausedState;
    private GameScreen dungeonState;



    public GameScreenManager(Stage stage) {
        this.primaryStage = stage;
        currentScreenState = null;
    }

    /**
     * Lets the user load directly into a dungeon state - this should be the starting
     * screen.
     */
    public void loadNewDungeon(String filename) {
        try {
            DungeonControllerLoader loader = new DungeonControllerLoader(filename);
            dungeonState = new DungeonScreen(primaryStage, this, loader);
            pausedState = new MenuScreen(primaryStage, this);
            
            // Load into the dungeon by default
            setActiveScreen(getDungeonState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveScreen (GameScreen gs) {
        this.currentScreenState = gs;
        currentScreenState.loadScreen();
    }

    public GameScreen getPausedState () {
        return pausedState;
    }

    public GameScreen getDungeonState() {
        return dungeonState;
    }

}