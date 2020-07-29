package unsw.dungeon;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Class to control which 
 */
public class GameController {
    
    @FXML
    Pane mainScreen;

    // States
    // TODO change
    private GameState currentState;
    private DungeonState dungeonState;

    // Gameloop
    private Timeline gameLoop;
    private EventHandler<ActionEvent> ev;

    public GameController() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        // Initialise the main game loop.
        ev = new EventHandler<ActionEvent>() {
            long prevTime = System.nanoTime();
            
            @Override
            public void handle(ActionEvent event) {
                long newTime = System.nanoTime();
                double deltaTime = (double)(newTime - prevTime)/1000000000;
                prevTime = newTime;
                
                System.out.println(deltaTime);
                dungeonState.update(deltaTime);
            }

        };

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), ev);
        gameLoop.getKeyFrames().addAll(kf);

        // Now load in the starting dungeon and create the game state.
        for (ZLayer z : ZLayer.values()) {
            System.out.println(z);
        }


    }

    public Parent getRootNode() {
        return this.mainScreen;
    }

    @FXML
    public void initialize() {
        gameLoop.play();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE:
                // TODO change state.
        }
    }
}