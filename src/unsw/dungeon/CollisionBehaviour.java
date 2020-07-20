package unsw.dungeon;

/**
 * This is the base interface for all possible collision strategies that
 * may be imlemented.
 * 
 * 
 */
public interface CollisionBehaviour {
    /**
     * Returns whether or not this tile is enterable by any other entity. 
     * NB: By calling isEnterable in the moving entity class, not in onCollide,
     * we allow more flexibility for additional features - thus collision
     * acts more as a marker - it says you should/should not move here if
     * your entity is trying to adhere to normal collisions. However with this 
     * implentation, we could easily add behaviour like a ghost enemy - something
     * that can move through walls (but maybe still wants to trigger the onCollide.)
     * 
     * @return True if the tile this behaviour is attached to should be enterable.
     * False if it is not.
     */
    boolean isEnterable();


    /**
     * This function defines the behaviour this collision strategy should
     * employ when another entity collides with it.
     * 
     * @param e The entity that interacted with this collider.
     */
    void onCollide(Entity e);
}