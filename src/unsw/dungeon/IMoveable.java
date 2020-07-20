package unsw.dungeon;

/**
 * Interface for any entity that should be able to be pushed/moved by the system.
 */
public interface IMoveable {
    //TODO bound check
    void move(int x, int y);
}