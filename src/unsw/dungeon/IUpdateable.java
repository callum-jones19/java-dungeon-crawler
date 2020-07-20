package unsw.dungeon;

/**
 * Interface that allows for an object to run the update function from the main
 * gameloop.
 */
public interface IUpdateable {
    /**
     * Function is called by the main game loop every frame, with deltaTime
     * being passed in.
     * 
     * @param deltaTime Time between the current and last frame.
     */
    public void update(double deltaTime);
}