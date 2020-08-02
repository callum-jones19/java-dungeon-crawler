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
    private GameScreen deathState;
    private GameScreen controlsState;
    private GameScreen completedState;

    private String currentDungeon = null;

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
            if (this.currentDungeon == null) {
                this.currentDungeon = filename;
            }
            DungeonControllerLoader loader = new DungeonControllerLoader(filename);
            dungeonState = new DungeonScreen(primaryStage, this, loader);
            pausedState = new MenuScreen(primaryStage, this);
            deathState = new DeathScreen(primaryStage, this);
            controlsState = new ControlsScreen(primaryStage, this);
            completedState = new CompletionScreen(primaryStage, this);
            
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

    public GameScreen getDeathState() {
        return deathState;
    }

    public GameScreen getControlsState() {
        return controlsState;
    }

    public GameScreen getCompletedState() {
        return completedState;
    }

    public String getCurrentDungeon() {
        return currentDungeon;
    }

    public void refreshKeys() {
        DungeonScreen dScreen = (DungeonScreen) dungeonState;
        dScreen.refreshKeys();
    }

}