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
    // Once this state has been initialised, only should be changed BY THE STATES.
    private GameScreen currentScreenState;
    
    private GameScreen pausedState;
    private GameScreen deathState;
    private GameScreen controlsState;
    private GameScreen completedState;
    private GameScreen loadedDungeonState;

    private String currentDungeonFile = null;


    public GameScreenManager(Stage stage) {
        this.primaryStage = stage;
        try {
            this.pausedState = new MenuScreen(primaryStage, this);
            this.deathState = new DeathScreen(primaryStage, this);
            this.controlsState = new ControlsScreen(primaryStage, this);
            this.completedState = new CompletionScreen(primaryStage, this);
            loadNewDungeonState("lobby.json");
            currentScreenState = loadedDungeonState;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load new instance of the game
     */
    public void loadGame() {
        currentScreenState.loadScreen();
    }

    /////////////////////////////////////////////////////////////////////////////
    // These two functions load into the state holders, not the actual state,
    // therefore they don't break the actual state pattern (by changing state
    // from within the context.) We still aren't changing strategies from the
    // context class(this), which would have made it a strategy pattern.
    //
    // This code is here so that we don't have issues of DRY inside states,
    // where upon telling the game to load a new level, we would need to repeat
    // the below code each time. The same goes for returning to the lobby - without
    // the below function, we would need to ensure that anything changing the state
    // to the lobby has the same code and uses the correct JSON file
    //
    /////////////////////////////////////////////////////////////////////////////
    
    // Load a dungeon state from JSON into the dungeonState.
    public void loadNewDungeonState(String fileName) throws IOException {
        DungeonControllerLoader loader = new DungeonControllerLoader(fileName);
        DungeonScreen tmp = new DungeonScreen(primaryStage, this, loader);
        loadedDungeonState = tmp;
        currentDungeonFile = fileName;
    }
    
    // Load the lobby level 
    public void loadLobbyState() {
        try {
            // Load into the dungeon by default
            setScreenState(getLoadedDungeonState());
            loadNewDungeonState("lobby.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    /////////////////////////////////////////////////////////////////////////////


    public void setScreenState (GameScreen gs) {
        this.currentScreenState = gs;
        currentScreenState.loadScreen();
    }


    public GameScreen getPauseState() {
        return this.pausedState;
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

    public String getCurrentDungeonFile() {
        return currentDungeonFile;
    }

    public void refreshKeys() {
        DungeonScreen dScreen = (DungeonScreen) loadedDungeonState;
        dScreen.refreshKeys();
    }

    public GameScreen getLoadedDungeonState() {
        return this.loadedDungeonState;
    }
}