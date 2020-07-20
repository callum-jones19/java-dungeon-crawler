package unsw.dungeon;

/**
 * Interface for any entities that can be picked up by the player.
 */
public interface Item {
    /**
     * Is there another use left on this item.
     * @return True if useable again, false if not.
     */
    boolean canUseAgain();
    
    /**
     * Should this item only exist as a single object in an inventory.
     * @return True if unique, false if not.
     */
    boolean isUnique();

    /**
     * Compare this item to another item.
     * 
     * @param i Item being compared to this one
     * @return True if the item types are the same.
     */
    boolean checkItemType(Item i);

    /**
     * Function to run when an item is picked up.
     * @param e Entity that picks up the item.
     */
    void pickup(Entity e);
}