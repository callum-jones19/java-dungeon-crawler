package unsw.dungeon;

/**
 * Interface allowing a strategy pattern implentation of enemy pathfinding.
 */
public interface SearchStyle {
    public Coordinates pathSearch(int targetX, int targetY, int currentX, int currentY);
}