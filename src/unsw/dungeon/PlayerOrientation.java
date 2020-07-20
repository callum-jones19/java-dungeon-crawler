package unsw.dungeon;

/**
 * Interface allowing a strategy pattern implentation of the orientation of the
 * player and how their attacks should accordingly be oriented
 */
public interface PlayerOrientation {
    public void attack(Weapon weapon);
}