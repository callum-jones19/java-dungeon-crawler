package unsw.dungeon;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 * 
 * GameState is the interface for any class that will be executable as a game
 * 'scene'. This is more a framework that will be expanded in Milestone 3 to
 * allow for pause screens (which will have a different run, handleInput and
 * render function.) This will be plugged into DungeonController - as in,
 * DungeonController will store a GameState, to which it will pass in inputs and
 * manage the overall game loop. (i.e. inside this loop, we wil call
 * currentState.run(), currentState.render, etc.).
 * 
 * This framework means the game state can exist without relying on a
 * third-party front-end. i.e. The backend can exist as an actual game should -
 * with a proper game loop - without relying on a file with ANY dependance of a
 * third party library. It also makes the code extensible (we can make other
 * states, such as a pause screen, which we can dynamically switch between for
 * things like pause functionality, or maybe a 'peaceful' mode where enemies
 * don't chase (idk that one's off the top of my head.))
 * 
 */
public interface GameState {
    // timeToRunFor will be removed after this milestone - this function just
    // allows for testing, as we can choose to run the game loop for only x
    // seconds.
    public boolean update(double deltaTime);
    public void initialRender(Pane parent);
    public void receiveInput(KeyCode k);
}